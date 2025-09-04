package org.corexero.indianmetrocore.repositoryImpl

import com.codeancy.metroui.domain.models.RecentRouteResult
import com.codeancy.metroui.domain.models.RouteResultUi
import com.codeancy.metroui.domain.repository.RouteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RouteRepositoryImpl : RouteRepository {

    override suspend fun getRoute(
        sourceId: Long,
        destinationId: Long
    ): RouteResultUi {
        TODO("Not yet implemented")
    }

    override suspend fun updatePlatForms(routeResultUi: RouteResultUi): RouteResultUi {
        TODO("Not yet implemented")
    }

    override suspend fun getRecentSearches(): Flow<List<RecentRouteResult>> {
        return flow {  }
    }

    override suspend fun getRecentSearch(
        sourceId: Long,
        destinationId: Long
    ): RecentRouteResult? {
        TODO("Not yet implemented")
    }

}