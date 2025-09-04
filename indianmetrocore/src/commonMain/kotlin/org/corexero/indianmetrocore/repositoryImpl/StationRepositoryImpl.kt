package org.corexero.indianmetrocore.repositoryImpl

import com.codeancy.metroui.common.utils.UiText
import com.codeancy.metroui.domain.models.LocationUi
import com.codeancy.metroui.domain.models.NearestMetroStationUi
import com.codeancy.metroui.domain.models.StationUi
import com.codeancy.metroui.domain.repository.StationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.corexero.indianmetro.database.IndianMetroDatabase
import org.corexero.indianmetrocore.sqldelight.GetAllStations


class StationRepositoryImpl(
    private val indianMetroDatabase: IndianMetroDatabase,
    private val cityId: Long
) : StationRepository {

    override suspend fun getAllStations(): List<StationUi> {
        return withContext(Dispatchers.IO) {
            indianMetroDatabase.indianMetroDatabaseQueries.getAllStations(cityId).executeAsList()
                .map { it.toStationUi() }
        }
    }

    private fun GetAllStations.toStationUi(): StationUi {
        return StationUi(
            id = id,
            code = code,
            name = UiText.DynamicString(name),
            icon = StationUi.StationIcon.Train,
            description = null,
            platform = null,
            time = 0,
            colorHex = "#000000",
            isInterchange = false,
            isFirstStation = false,
            isEndStation = false,
            lineName = "",
            platformNo = null,
            towards = null,
            locationUi = LocationUi(
                lat = lat,
                long = lng
            ),
        )
    }

    override suspend fun getNearestMetroStations(locationUi: LocationUi): List<NearestMetroStationUi> {
        TODO("Not yet implemented")
    }

    override suspend fun getFirstAndLastMetroTime(
        source: StationUi,
        destination: StationUi
    ): Pair<String, String>? {
        TODO("Not yet implemented")
    }

}