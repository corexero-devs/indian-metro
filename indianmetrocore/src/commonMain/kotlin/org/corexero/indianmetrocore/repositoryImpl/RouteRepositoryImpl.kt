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
import kotlinx.serialization.protobuf.ProtoBuf
import org.corexero.indianmetro.database.IndianMetroDatabase
import org.corexero.indianmetrocore.graphs.RouteCalculator
import org.corexero.indianmetrocore.graphs.topology.CityTransitTopology
import org.corexero.indianmetrocore.protocolBufs.LineMetadata
import org.corexero.indianmetrocore.protocolBufs.PlatformSequence
import org.corexero.indianmetrocore.protocolBufs.TripMetadata
import org.corexero.indianmetrocore.sqldelight.GetStopTimesWithStationInfoById
import org.corexero.indianmetrocore.sqldelight.GetTripsBetweenStations
import org.corexero.indianmetrocore.sqldelight.GetTripsWithLineAndCalendar
import org.corexero.sutradhar.utils.Logger
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class RouteRepositoryImpl(
    private val indianMetroDatabase: IndianMetroDatabase,
    private val routeCalculator: RouteCalculator,
    private val cityTransitTopology: CityTransitTopology
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
            k = 10
        ).first()

        return route.legs.map { leg ->
            // trips that have both endpoints in the same StopTimes (your first query)
            val trips = indianMetroDatabase.indianMetroDatabaseQueries
                .getTripsBetweenStations(
                    stationCode = listOf(leg.from) + cityTransitTopology.aliasesOf(leg.from),
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
                .filter { it.startTime >= currentTimeSeconds }.minByOrNull { it.startTime }

            if (latestTripInfo == null) {
                throw IllegalStateException("No trips found between ${leg.from} and ${leg.to}")
            }

            val validTrip = trips.first { it.stopTimesId == latestTripInfo.stopTimesId }

            val tripMetadata = latestTripInfo.tripMetadata?.let {
                ProtoBuf.decodeFromByteArray<TripMetadata>(it)
            }

            val platForm = tripMetadata?.let {
                indianMetroDatabase
                    .indianMetroDatabaseQueries.getPlatformSequenceById(it.flags.toLong())
                    .executeAsOneOrNull()
            }

            val platformInfo = platForm?.platform_sequence?.let {
                ProtoBuf.decodeFromByteArray<PlatformSequence>(it)
            }

            val stopsRaw = indianMetroDatabase.indianMetroDatabaseQueries
                .getStopTimesWithStationInfoById(id = validTrip.stopTimesId)
                .executeAsList()
                .sortedBy { it.arrivalTimeOffset }

            mapToStationList(
                trip = validTrip,
                tripInfo = latestTripInfo,
                stations = stopsRaw,
                platformSequence = platformInfo
            )
        }.toRouteResultUi()
    }

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

    @OptIn(ExperimentalSerializationApi::class)
    fun mapToStationList(
        trip: GetTripsBetweenStations,
        tripInfo: GetTripsWithLineAndCalendar,
        stations: List<GetStopTimesWithStationInfoById>,
        platformSequence: PlatformSequence?
    ): List<StationUi> {

        val lineMetadata = tripInfo.lineMetadata?.let {
            ProtoBuf.decodeFromByteArray<LineMetadata>(it)
        }

        val stationsInThisInterchange = stations.filter {
            it.stopSeq >= trip.startStationStopSeq && it.stopSeq <= trip.endStationStopSeq
        }.sortedBy { it.stopSeq }

        val towardsStation = if (trip.startStationStopSeq < trip.endStationStopSeq) {
            stations.last()
        } else {
            stations.first()
        }
        val result = mutableListOf<StationUi>()
        var time = 0L
        stationsInThisInterchange.forEachIndexed { index, stopWithStation ->
            if (index != 0) {
                time += stationsInThisInterchange[index].arrivalTimeOffset - stationsInThisInterchange[index - 1].arrivalTimeOffset
            }
            StationUi(
                id = stopWithStation.stationId,
                name = UiText.DynamicString(stopWithStation.stationName),
                description = null,
                platform = null,
                platformNo = platformSequence?.let {
                    it.stationPlatformMap[stopWithStation.stopSeq.toInt()] ?: it.id
                },
                time = time,
                colorHex = lineMetadata?.primary ?: "#262626",
                lineName = tripInfo.lineName,
                towards = towardsStation.stationName,
                code = stopWithStation.stationCode,
                locationUi = LocationUi(
                    lat = stopWithStation.latitude,
                    long = stopWithStation.longitude
                )
            ).let { result.add(it) }
        }
        return result
    }

    private fun List<List<StationUi>>.toRouteResultUi(): RouteResultUi {
        val interChange = mutableListOf<RouteResultUi.Interchange>()
        var timeOffset = 0L
        val updatesStations: List<List<StationUi>> = mapIndexed { index, stations ->
            var updatesStations: List<StationUi>
            if (index == 0) {
                timeOffset += stations.last().time / 60
                updatesStations = stations.map { it.copy(time = it.time / 60) }
            } else {
                timeOffset += stations.last().time / 60
                updatesStations = stations.mapIndexed { index, station ->
                    station.copy(time = station.time / 60 + timeOffset)
                }
            }
            updatesStations
        }
        updatesStations.forEachIndexed { index, stations ->
            val sourceStation = stations.first()
            val destStation = stations.last()
            val inBetweenStations = stations.drop(1).dropLast(1)
            RouteResultUi.Interchange(
                sourceStation = sourceStation,
                destinationStation = destStation,
                inBetweenStations = inBetweenStations,
                lineColor = stations.first().colorHex,
                lineName = stations.first().lineName
            ).let { interChange.add(it) }
        }
        return RouteResultUi(
            sourceStation = interChange.first().sourceStation,
            destinationStation = interChange.last().destinationStation,
            fare = 0,
            interchanges = this.size - 1,
            stations = this.flatMap { it.map { it.id } }.toSet().size,
            interchange = interChange,
        )
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
