// Usage: node split-city.js <source.db> <cityId> <outDir>
// Keeps schema identical; no extra indexes created.
const fs = require('fs');
const path = require('path');
const Database = require('better-sqlite3');

if (process.argv.length < 5) {
  console.error('Usage: node split-city.js <source.db> <cityId> <outDir>');
  process.exit(1);
}

const SRC = path.resolve(process.argv[2]);
const CITY = Number(process.argv[3]);
const OUT_DIR = path.resolve(process.argv[4]);

if (!fs.existsSync(SRC)) { console.error('Source DB not found:', SRC); process.exit(1); }
if (!Number.isInteger(CITY)) { console.error('cityId must be an integer'); process.exit(1); }
if (!fs.existsSync(OUT_DIR)) fs.mkdirSync(OUT_DIR, { recursive: true });

const DDL = `
PRAGMA foreign_keys=OFF;
CREATE TABLE Line (
  id INTEGER NOT NULL PRIMARY KEY,
  name TEXT NOT NULL,
  city INTEGER NOT NULL,
  type INTEGER NOT NULL,
  metadata BLOB
);
CREATE TABLE PlatformSequence (
  id INTEGER PRIMARY KEY NOT NULL,
  platform_sequence BLOB
);
CREATE TABLE Station (
  id INTEGER PRIMARY KEY NOT NULL,
  code TEXT NOT NULL,
  lat REAL NOT NULL,
  lng REAL NOT NULL,
  type_id INTEGER NOT NULL,
  city INTEGER NOT NULL,
  metadata BLOB
);
CREATE TABLE StationName (
  station_id INTEGER NOT NULL,
  name TEXT NOT NULL,
  lang INTEGER NOT NULL,
  name_type INTEGER NOT NULL,
  soundex TEXT,
  soundex_index TEXT,
  PRIMARY KEY (station_id, name, lang)
);
CREATE TABLE StopTimes (
  id INTEGER NOT NULL,
  stop_seq INTEGER NOT NULL,
  stn_id INTEGER NOT NULL,
  arr_time_offset INTEGER NOT NULL,
  dep_time_offset INTEGER NOT NULL,
  distance INTEGER NOT NULL,
  is_stopping INTEGER NOT NULL,
  metadata BLOB,
  PRIMARY KEY (id, stop_seq)
);
CREATE TABLE Trip (
  id TEXT NOT NULL,
  calendar_id INTEGER NOT NULL,
  name TEXT NOT NULL,
  line_id INTEGER NOT NULL,
  stop_times_id INTEGER NOT NULL,
  trip_type INTEGER NOT NULL,
  start_time INTEGER NOT NULL,
  metadata BLOB,
  PRIMARY KEY (id, calendar_id)
);
CREATE TABLE TripCalendar (
  id INTEGER PRIMARY KEY NOT NULL,
  running_days_array TEXT NOT NULL,
  start_date INTEGER NOT NULL,
  end_date INTEGER
);
CREATE TABLE android_metadata (locale TEXT);
CREATE TABLE room_master_table (id INTEGER PRIMARY KEY, identity_hash TEXT);
`;

(function main() {
  const src = new Database(SRC, { fileMustExist: true });
  const outPath = path.join(OUT_DIR, `schedule_internal_db_city_${CITY}.db`);
  if (fs.existsSync(outPath)) fs.unlinkSync(outPath);

  // Create empty target with identical schema
  const dest = new Database(outPath);
  dest.exec(DDL);
  dest.close();

  // Copy data via ATTACH + INSERT...SELECT (fast, single process)
  src.exec(`ATTACH DATABASE '${outPath.replaceAll("'", "''")}' AS dest;`);

  try {
    src.exec('BEGIN;');

    // 1) Lines of this city
    src.exec(`
      INSERT INTO dest.Line
      SELECT id, name, city, type, metadata
      FROM Line
      WHERE city = ${CITY};
    `);

    // 2) Trips belonging to those lines
    src.exec(`
      INSERT INTO dest.Trip
      SELECT t.id, t.calendar_id, t.name, t.line_id, t.stop_times_id, t.trip_type, t.start_time, t.metadata
      FROM Trip t
      WHERE t.line_id IN (SELECT id FROM dest.Line);
    `);

    // 3) Calendars used by those trips
    src.exec(`
      INSERT INTO dest.TripCalendar
      SELECT DISTINCT c.*
      FROM TripCalendar c
      WHERE c.id IN (SELECT DISTINCT calendar_id FROM dest.Trip);
    `);

    // 4) StopTimes sequences referenced by those trips
    src.exec(`
      INSERT INTO dest.StopTimes
      SELECT s.*
      FROM StopTimes s
      WHERE s.id IN (SELECT DISTINCT stop_times_id FROM dest.Trip);
    `);

    // 5) Stations referenced by those StopTimes (STRICT: only referenced ones)
    src.exec(`
      INSERT INTO dest.Station
      SELECT DISTINCT st.*
      FROM Station st
      WHERE st.id IN (SELECT DISTINCT stn_id FROM dest.StopTimes)
        AND st.city = ${CITY};
    `);

    // 6) Station names for those stations
    src.exec(`
      INSERT INTO dest.StationName
      SELECT sn.*
      FROM StationName sn
      WHERE sn.station_id IN (SELECT id FROM dest.Station);
    `);

    // 7) Optional: PlatformSequence. It has no city—leave empty to keep "only provided city" data.
    // If your app needs it, uncomment:
    // src.exec(`INSERT INTO dest.PlatformSequence SELECT * FROM PlatformSequence;`);

    // 8) android_metadata & room_master_table (copy single rows if present)
    const locale = src.prepare(`SELECT locale FROM android_metadata LIMIT 1`).get()?.locale ?? 'en_US';
    const hash = src.prepare(`SELECT identity_hash FROM room_master_table LIMIT 1`).get()?.identity_hash;

    src.exec(`INSERT INTO dest.android_metadata(locale) VALUES ('${locale.replaceAll("'", "''")}');`);
    if (hash) {
      src.exec(`
        INSERT INTO dest.room_master_table(id, identity_hash)
        VALUES (42, '${hash.replaceAll("'", "''")}');
      `);
    }

    src.exec('COMMIT;');
    console.log(`✔ Created ${outPath}`);
  } catch (e) {
    src.exec('ROLLBACK;');
    console.error('Failed:', e.message);
    process.exit(1);
  } finally {
    src.exec('DETACH DATABASE dest;');
    src.close();
  }
})();
