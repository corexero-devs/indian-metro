package org.corexero.indianmetrocore.graphs

import org.corexero.indianmetrocore.graphs.model.SourceAndDestination
import org.corexero.indianmetrocore.graphs.topology.CityTransitTopology
import org.corexero.indianmetrocore.graphs.utils.MinHeap


class RouteCalculator(
    private val adjacencyListManager: AdjacencyListManager,
    private val cityTransitTopology: CityTransitTopology
) {

    val adjacency: Map<String, Set<AdjacencyListManager.Edge>> by lazy {
        adjacencyListManager.getAdjacencyList()
    }

    data class Path(val nodes: List<String>, val totalWeight: Int)

    private data class PathState(
        val nodes: List<String>,
        val cost: Int
    )

    fun findRoutes(
        start: String,
        end: String,
        k: Int = 5,
        maxExpansionsPerNode: Int = 50,
        maxPathLen: Int = 200
    ): List<InterchangePath> {

        require(start in adjacency) { "Unknown start node: $start" }
        require(end in adjacency) { "Unknown end node: $end" }

        val pq =
            MinHeap(compareBy<PathState> { it.cost }.thenBy { it.nodes.size })
        pq.add(PathState(listOf(start), 0))

        val expansionsPerNode = mutableMapOf<String, Int>()
        val results = mutableListOf<Path>()
        val seenSignatures = mutableSetOf<String>() // dedupe identical node sequences

        while (pq.isNotEmpty() && results.size < k) {
            val current = pq.poll()
            val last = current.nodes.last()

            if (last == end) {
                val sig = current.nodes.joinToString("->")
                if (seenSignatures.add(sig)) {
                    results += Path(current.nodes, current.cost)
                }
                continue
            }

            val used = expansionsPerNode.getOrElse(last) { 0 }
            if (used >= maxExpansionsPerNode) continue
            expansionsPerNode[last] = used + 1

            val neighbors = adjacency[last].orEmpty()
            for (edge in neighbors) {
                val next = edge.node
                if (next in current.nodes) continue               // avoid cycles (simple paths only)
                if (current.nodes.size + 1 > maxPathLen) continue // optional safety guard

                val nextPath = current.nodes + next
                val nextCost = current.cost + edge.weight
                pq.add(PathState(nextPath, nextCost))
            }
        }

        // Sort for a stable, nice output: by cost, then by hop count
        return convertToInterchange(
            filterPaths(
                results.sortedWith(
                    compareBy<Path> { it.totalWeight }.thenBy { it.nodes.size }
                ))
        )
    }

    data class InterchangeLeg(
        val from: String,
        val to: String,
        val line: String,
        val stops: Int,     // number of station-to-station hops in this leg
        val weight: Int     // sum of weights across those hops
    )

    data class InterchangePath(
        val legs: List<InterchangeLeg>,
        val totalWeight: Int,
        val interchanges: Int // legs.size - 1
    )

    private fun convertToInterchange(
        paths: List<Path>,
    ): List<InterchangePath> {

        // Preload maps for speed
        val edgeLabels =
            cityTransitTopology.edgeLineLabels()                // Map<SourceAndDestination, List<LabelledGraphEdge>>
        val stationLines =
            cityTransitTopology.stationLines()                // Map<String, List<String>>

        fun aliases(code: String) = cityTransitTopology.aliasesOf(code).ifEmpty { emptyList() }

        fun resolveLineBetween(a: String, b: String): String? {
            // 1) Try explicit edge labels (consider aliases in both directions)
            val aCandidates = listOf(a) + aliases(a)
            val bCandidates = listOf(b) + aliases(b)

            for (aa in aCandidates) for (bb in bCandidates) {
                edgeLabels[SourceAndDestination(aa, bb)]?.firstOrNull()?.let { return it.label }
                edgeLabels[SourceAndDestination(bb, aa)]?.firstOrNull()
                    ?.let { return it.label } // undirected fallback
            }

            // 2) Fall back to station-lines intersection
            val aLines = (stationLines[a] ?: emptyList()) + aCandidates.flatMap {
                stationLines[it] ?: emptyList()
            }
            val bLines = (stationLines[b] ?: emptyList()) + bCandidates.flatMap {
                stationLines[it] ?: emptyList()
            }
            val common = aLines.toSet().intersect(bLines.toSet())
            return common.firstOrNull()
        }

        fun hopWeight(a: String, b: String): Int {
            // Use the same precedence as line resolution to fetch weight if present; else default to 1
            val aCandidates = listOf(a) + aliases(a)
            val bCandidates = listOf(b) + aliases(b)
            for (aa in aCandidates) for (bb in bCandidates) {
                edgeLabels[SourceAndDestination(aa, bb)]?.firstOrNull()?.let { return it.weight }
                edgeLabels[SourceAndDestination(bb, aa)]?.firstOrNull()?.let { return it.weight }
            }
            // If edgeLineLabels() is empty in this city, weights usually came from `edges`
            // but we don’t have that map here; assume unit weight for a hop.
            return 1
        }

        fun toInterchange(path: Path): InterchangePath {
            val nodes = path.nodes
            if (nodes.size < 2) {
                return InterchangePath(emptyList(), path.totalWeight, 0)
            }

            val legs = mutableListOf<InterchangeLeg>()

            var legStartIdx = 0
            var currentLine: String? = resolveLineBetween(nodes[0], nodes[1])
            var accWeight = hopWeight(nodes[0], nodes[1])

            for (i in 1 until nodes.lastIndex) {
                val nextLine = resolveLineBetween(nodes[i], nodes[i + 1])
                val nextWeight = hopWeight(nodes[i], nodes[i + 1])

                // If the line continues, accumulate; else close the current leg and start a new one
                if (currentLine != null && currentLine == nextLine) {
                    accWeight += nextWeight
                } else {
                    // Close leg [legStartIdx .. i] on currentLine (if any)
                    val from = nodes[legStartIdx]
                    val to = nodes[i]
                    val stops = i - legStartIdx
                    val lineName = currentLine ?: "UNKNOWN"
                    legs += InterchangeLeg(from, to, lineName, stops, accWeight)

                    // Start new leg
                    legStartIdx = i
                    currentLine = nextLine
                    accWeight = nextWeight
                }
            }

            // Close the final leg to the last node
            val from = nodes[legStartIdx]
            val to = nodes.last()
            val stops = nodes.lastIndex - legStartIdx
            val lineName =
                currentLine ?: resolveLineBetween(nodes[nodes.lastIndex - 1], nodes.last())
                ?: "UNKNOWN"
            legs += InterchangeLeg(from, to, lineName, stops, accWeight)

            return InterchangePath(
                legs = legs,
                totalWeight = path.totalWeight,
                interchanges = if (legs.isEmpty()) 0 else legs.size - 1
            )
        }

        return paths.map { toInterchange(it) }
    }


    private fun filterPaths(rawPaths: List<Path>, maxPaths: Int = 10): List<Path> {
        // 1. Drop null/empty paths
        var paths = rawPaths.filter { it.nodes.isNotEmpty() }.toMutableList()
        if (paths.isEmpty()) return emptyList()

        // 2. Limit explored paths to 10
        if (paths.size > maxPaths) {
            paths = paths.take(maxPaths).toMutableList()
        }

        // 3. Skip paths with duplicate stations (cycles)
        paths = paths.filter { path ->
            path.nodes.size == path.nodes.toSet().size
        }.toMutableList()
        if (paths.isEmpty()) return emptyList()

        // 4. Drop paths where any sublist is empty
        paths = paths.filter { path ->
            path.nodes.none { it.isBlank() }
        }.toMutableList()
        if (paths.isEmpty()) return emptyList()

        // 5. (Schedule-based pruning in lmj.f)
        // In original lmj this uses ScheduleTime objects.
        // Here we'll simulate with a cutoff on path length (like "must have at least 2 hops").
        // You can replace this with actual time-based checks.
        val scheduleCutoff = 2
        paths = paths.filter { it.nodes.size >= scheduleCutoff }.toMutableList()
        if (paths.isEmpty()) return emptyList()

        // 6. Drop paths where final segment is empty (already covered by isNotEmpty check)

        // 7. Keep only shortest / lowest-weight paths
        val minWeight = paths.minOf { it.totalWeight }
        paths = paths.filter { it.totalWeight <= minWeight * 1.25 }
            .toMutableList() // same 1.25× tolerance in lmj

        return paths
    }


}
