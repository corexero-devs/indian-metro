package org.corexero.indianmetrocore.graphs.topology

import org.corexero.indianmetrocore.graphs.model.GraphEdge
import org.corexero.indianmetrocore.graphs.model.LabelledGraphEdge
import org.corexero.indianmetrocore.graphs.model.SourceAndDestination

interface CityTransitTopology {

    val cityName: String

    val edges: List<GraphEdge>

    fun aliasesOf(stationCode: String): List<String>

    fun edgeLineLabels(): Map<SourceAndDestination, List<LabelledGraphEdge>>

    fun stationLines(): Map<String, List<String>>

    fun aliasGroups(): Map<String, List<String>>

}