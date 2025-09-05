package org.corexero.indianmetrocore.graphs

import org.corexero.indianmetrocore.graphs.topology.CityTransitTopology
import kotlin.collections.forEach

class AdjacencyListManager(
    private val cityTransitTopology: CityTransitTopology
) {

    fun getAdjacencyList(): Map<String, Set<Edge>> {
        val adjacencyList = mutableMapOf<String, MutableSet<Edge>>()
        if (cityTransitTopology.edgeLineLabels().isNotEmpty()) {
            cityTransitTopology.edgeLineLabels()
                .forEach { (sourceAndDestination, labelledGraphEdges) ->
                    adjacencyList[sourceAndDestination.source] =
                        adjacencyList.getOrElse(sourceAndDestination.source) { mutableSetOf() }
                            .apply {
                                add(
                                    Edge(
                                        sourceAndDestination.destination,
                                        labelledGraphEdges.first().weight
                                    )
                                )
                            }
                }
            return adjacencyList
        }
        cityTransitTopology.edges.forEach { graphEdge ->
            adjacencyList[graphEdge.source] =
                adjacencyList.getOrElse(graphEdge.source) { mutableSetOf() }
                    .apply {
                        add(Edge(graphEdge.destination, graphEdge.weight))
                    }
            adjacencyList[graphEdge.destination] =
                adjacencyList.getOrElse(graphEdge.destination) { mutableSetOf() }
                    .apply {
                        add(Edge(graphEdge.source, graphEdge.weight))
                    }
        }
        return adjacencyList
    }

    data class Edge(
        val node: String,
        val weight: Int
    )

}