package org.corexero.indianmetrocore.di

import app.cash.sqldelight.async.coroutines.synchronous
import com.codeancy.metroui.domain.repository.RouteRepository
import com.codeancy.metroui.domain.repository.StationRepository
import org.corexero.indianmetro.database.IndianMetroDatabase
import org.corexero.indianmetrocore.graphs.AdjacencyListManager
import org.corexero.indianmetrocore.graphs.RouteCalculator
import org.corexero.indianmetrocore.graphs.model.City
import org.corexero.indianmetrocore.graphs.topology.CityTransitTopology
import org.corexero.indianmetrocore.graphs.topology.impl.DelhiCityTransitTopology
import org.corexero.indianmetrocore.graphs.topology.impl.OtherCityTransitTopology
import org.corexero.indianmetrocore.repositoryImpl.RouteRepositoryImpl
import org.corexero.indianmetrocore.repositoryImpl.StationRepositoryImpl
import org.corexero.sutradhar.datastore.DataStoreFactory
import org.corexero.sutradhar.sqldelight.SqlDriverFactory
import org.corexero.sutradhar.utils.Path
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

private const val DB_NAME = "metro"
private const val DB_EXTENSION = "sqlite"

private const val DATA_STORE_NAME = "metro.preferences_pb"

private val path = Path(
    name = DB_NAME,
    extension = DB_EXTENSION,
)

val indianMetroModule = module {
    singleOf(::RouteRepositoryImpl)
        .bind<RouteRepository>()
    singleOf(::StationRepositoryImpl).bind<StationRepository>()
    single {
        val factory = get<SqlDriverFactory>()
        IndianMetroDatabase(
            factory.createDriver(
                dbName = path,
                schema = IndianMetroDatabase.Schema.synchronous(),
                assetNameToCopyDbFrom = path
            )
        )
    }.bind<IndianMetroDatabase>()
    single {
        get<DataStoreFactory>().createDataStore(DATA_STORE_NAME)
    }
    includes(platformModule)
    singleOf(::RouteCalculator)
    single {
        val city = get<City>()
        when (city.topologyId) {
            null -> DelhiCityTransitTopology()
            else -> OtherCityTransitTopology(city.topologyId)
        }
    }.bind<CityTransitTopology>()
    singleOf(::AdjacencyListManager)
    singleOf(::RouteCalculator)
}

expect val platformModule: Module