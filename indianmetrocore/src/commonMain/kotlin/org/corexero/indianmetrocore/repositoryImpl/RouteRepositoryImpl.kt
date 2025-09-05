package org.corexero.indianmetrocore.repositoryImpl

import com.codeancy.metroui.common.utils.UiText
import com.codeancy.metroui.domain.models.LocationUi
import com.codeancy.metroui.domain.models.RecentRouteResult
import com.codeancy.metroui.domain.models.RouteResultUi
import com.codeancy.metroui.domain.models.StationUi
import com.codeancy.metroui.domain.repository.RouteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.decodeFromHexString
import kotlinx.serialization.protobuf.ProtoBuf
import org.corexero.indianmetro.database.IndianMetroDatabase
import org.corexero.indianmetrocore.graphs.RouteCalculator
import org.corexero.indianmetrocore.protocolBufs.LineMetadata
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class RouteRepositoryImpl(
    private val indianMetroDatabase: IndianMetroDatabase,
    private val routeCalculator: RouteCalculator,
) : RouteRepository {

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getRoute(
        sourceId: Long,
        destinationId: Long
    ): RouteResultUi {

        val stations = indianMetroDatabase.indianMetroDatabaseQueries
            .getStationsByIds(listOf(sourceId, destinationId))
            .executeAsList()

        val sourceStation = stations.find { it.id == sourceId }
        val destinationStation = stations.find { it.id == destinationId }

        // invalid source or destination
        if (sourceStation == null || destinationStation == null) {
            throw IllegalArgumentException("Invalid source or destination station Id")
        }
        val route = routeCalculator.findRoutes(
            start = sourceStation.code,
            end = destinationStation.code,
            k = 1
        ).first()

        val interchangesUi: List<RouteResultUi.Interchange> = route.legs.map { leg ->
            // trips that have both endpoints in the same StopTimes (your first query)
            val trips = indianMetroDatabase.indianMetroDatabaseQueries
                .getTripsBetweenStations(
                    stationCode = listOf(leg.from),
                    stationCode_ = leg.to
                )
                .executeAsList()

            // line + calendar info (your second query)
            val currentTimeSeconds = currentTimeInISTSeconds()

            val latestTripInfo = indianMetroDatabase.indianMetroDatabaseQueries
                .getTripsWithLineAndCalendar(
                    stop_times_id = trips.map { it.stopTimesId },
                    running_days_array = todayWeekdayInIST().toLikePattern() // unknown -> pass something; you can replace later
                )
                .executeAsList()
                .filter {
                    it.startTime >= currentTimeSeconds
                }.minByOrNull { it.startTime }!!

            val validTrip = trips.first { it.stopTimesId == latestTripInfo.stopTimesId }

            // pick one (first) to read line info; if none, defaults kick in
            val lineName = latestTripInfo.lineName
            val lineColor = "#ff0000" // color not in queries; keep empty string as requested

            // load the concrete stop records (your third query)
            val stopsRaw = indianMetroDatabase.indianMetroDatabaseQueries
                .getStopTimesWithStationInfoById(id = validTrip.stopTimesId)
                .executeAsList()
                .sortedBy { it.arrivalTimeOffset }
                .filter {
                    it.stopSeq in validTrip.startStationStopSeq..validTrip.endStationStopSeq
                }

            val lineMetadata = latestTripInfo.lineMetadata?.let {
                ProtoBuf.decodeFromByteArray<LineMetadata>(it)
            }

            // figure out the range for this leg on that stopTimes id
            val startStopSeq = trips.firstOrNull()?.startStationStopSeq ?: 0L
            val endStopSeq = trips.firstOrNull()?.endStationStopSeq ?: 0L
            val stopTimesId = trips.firstOrNull()?.stopTimesId

            val segmentStops = stopsRaw
                .filter { it.stopTimesId == stopTimesId }
                .filter { it.stopSeq in startStopSeq..endStopSeq }
                .sortedBy { it.stopSeq }

            // the in-between list (exclude endpoints if present)
            val inBetweenStations: List<StationUi> =
                if (segmentStops.size <= 2) emptyList()
                else segmentStops.drop(1).dropLast(1).map { s ->
                    stationUiFromDb(
                        id = s.stopTimesId, // no station id in this row -> keep 0/stopTimesId
                        code = s.stationCode,
                        name = s.stationName,
                        lineColor = lineColor,
                        lineName = lineName,
                        isInterchange = false,
                        isFirst = false,
                        isEnd = false,
                        lat = s.latitude,
                        lng = s.longitude
                    )
                }

            // build source/destination StationUi for this leg
            val startRow = segmentStops.firstOrNull()
            val endRow = segmentStops.lastOrNull()

            val fromUi = stationUiFromDb(
                id = startRow?.stopTimesId ?: 0L,
                code = startRow?.stationCode ?: leg.from,
                name = startRow?.stationName ?: leg.from,
                lineColor = lineColor,
                lineName = lineName,
                isInterchange = false,
                isFirst = true,
                isEnd = false,
                lat = startRow?.latitude,
                lng = startRow?.longitude
            )

            val toUi = stationUiFromDb(
                id = endRow?.stopTimesId ?: 0L,
                code = endRow?.stationCode ?: leg.to,
                name = endRow?.stationName ?: leg.to,
                lineColor = lineColor,
                lineName = lineName,
                isInterchange = true,   // end of a leg → potential interchange
                isFirst = false,
                isEnd = true,
                lat = endRow?.latitude,
                lng = endRow?.longitude
            )

            RouteResultUi.Interchange(
                sourceStation = fromUi,
                destinationStation = toUi,
                inBetweenStations = inBetweenStations,
                lineColor = lineColor,
                lineName = lineName
            )
        }
        // 4) top-level UI objects for the final response
        val sourceUi = StationUi(
            id = sourceStation.id,
            name = UiText.DynamicString(sourceStation.name),
            icon = StationUi.StationIcon.Out,
            description = null,
            platform = null,
            time = 0,
            colorHex = "#ffffff",
            isInterchange = false,
            isFirstStation = true,
            isEndStation = false,
            lineName = "",
            platformNo = null,
            towards = null,
            code = sourceStation.code,
            locationUi = LocationUi(
                lat = sourceStation.lat,
                long = sourceStation.lng
            )
        )

        val destUi = StationUi(
            id = destinationStation.id,
            name = UiText.DynamicString(destinationStation.name),
            icon = StationUi.StationIcon.In,
            description = null,
            platform = null,
            time = 0,
            colorHex = "",
            isInterchange = false,
            isFirstStation = false,
            isEndStation = true,
            lineName = "",
            platformNo = null,
            towards = null,
            code = destinationStation.code,
            locationUi = LocationUi(
                lat = destinationStation.lat,
                long = destinationStation.lng
            )
        )

        val totalStations = (route.legs.sumOf { it.stops } + 1).coerceAtLeast(1)

        return RouteResultUi(
            sourceStation = sourceUi,
            destinationStation = destUi,
            fare = 0, // not available → 0
            interchanges = route.interchanges,
            stations = totalStations,
            interchange = interchangesUi
        )
    }

    private fun stationUiFromDb(
        id: Long,
        code: String,
        name: String,
        lineColor: String,
        lineName: String,
        isInterchange: Boolean,
        isFirst: Boolean,
        isEnd: Boolean,
        lat: Double? = null,
        lng: Double? = null
    ): StationUi = StationUi(
        id = id,
        name = UiText.DynamicString(name),
        icon = when {
            isFirst -> StationUi.StationIcon.Out
            isEnd -> StationUi.StationIcon.In
            else -> StationUi.StationIcon.Train
        },
        description = null,
        platform = null,
        time = 0,
        colorHex = lineColor,
        isInterchange = isInterchange,
        isFirstStation = isFirst,
        isEndStation = isEnd,
        lineName = lineName,
        platformNo = null,
        towards = null,
        code = code,
        locationUi = LocationUi(
            lat = lat ?: 0.0,
            long = lng ?: 0.0
        )
    )

    override suspend fun updatePlatForms(routeResultUi: RouteResultUi): RouteResultUi {
        // platforms data not available; return as-is
        return routeResultUi
    }

    override suspend fun getRecentSearches(): Flow<List<RecentRouteResult>> {
        return flow { }
    }

    override suspend fun getRecentSearch(
        sourceId: Long,
        destinationId: Long
    ): RecentRouteResult? {
        return null
    }

    private enum class WeekDay(val index: Int) {
        Sun(0),
        Mon(1),
        Tue(2),
        Wed(3),
        Thu(4),
        Fri(5),
        Sat(6);

        fun toLikePattern(): String {
            val chars = CharArray(7) { '_' }
            chars[index] = '1'
            return chars.joinToString(",")
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun todayWeekdayInIST(): WeekDay {
        val nowIst = Clock.System.now().toLocalDateTime(TimeZone.of("Asia/Kolkata"))
        return when (nowIst.dayOfWeek) {
            DayOfWeek.MONDAY -> WeekDay.Mon
            DayOfWeek.TUESDAY -> WeekDay.Tue
            DayOfWeek.WEDNESDAY -> WeekDay.Wed
            DayOfWeek.THURSDAY -> WeekDay.Thu
            DayOfWeek.FRIDAY -> WeekDay.Fri
            DayOfWeek.SATURDAY -> WeekDay.Sat
            DayOfWeek.SUNDAY -> WeekDay.Sun
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun currentTimeInISTSeconds(): Int {
        val istNow = Clock.System.now().toLocalDateTime(TimeZone.of("Asia/Kolkata"))
        return istNow.hour * 3600 + istNow.minute * 60 + istNow.second
    }

}
