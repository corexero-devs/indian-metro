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

    // --- helpers used by both functions ---
    private data class EdgeInfo(val label: String?, val weight: Int?)

    private fun aliasesOf(code: String): List<String> =
        cityTransitTopology.aliasesOf(code).ifEmpty { emptyList() }

    private fun aliasEqual(a: String, b: String): Boolean {
        if (a == b) return true
        val aSet = (listOf(a) + aliasesOf(a)).toSet()
        val bSet = (listOf(b) + aliasesOf(b)).toSet()
        return aSet.intersect(bSet).isNotEmpty()
    }

    private fun edgeInfo(a: String, b: String): EdgeInfo {
        val edgeLabels = cityTransitTopology.edgeLineLabels()
        val aC = listOf(a) + aliasesOf(a)
        val bC = listOf(b) + aliasesOf(b)
        for (aa in aC) for (bb in bC) {
            edgeLabels[SourceAndDestination(aa, bb)]?.firstOrNull()?.let { return EdgeInfo(it.label, it.weight) }
            edgeLabels[SourceAndDestination(bb, aa)]?.firstOrNull()?.let { return EdgeInfo(it.label, it.weight) }
        }
        return EdgeInfo(null, null)
    }

    private fun hopWeight(a: String, b: String): Int =
        edgeInfo(a, b).weight ?: 1

    private fun resolveLineBetween(a: String, b: String): String? {
        edgeInfo(a, b).label?.let { return it } // prefer explicit edge label
        val stationLines = cityTransitTopology.stationLines()
        val aLines = (stationLines[a] ?: emptyList()) + aliasesOf(a).flatMap { stationLines[it] ?: emptyList() }
        val bLines = (stationLines[b] ?: emptyList()) + aliasesOf(b).flatMap { stationLines[it] ?: emptyList() }
        return aLines.toSet().intersect(bLines.toSet()).firstOrNull()
    }

    // --- corrected convertToInterchange ---
    private fun convertToInterchange(
        paths: List<Path>,
    ): List<InterchangePath> {

        fun toInterchange(path: Path): InterchangePath {
            val nodes = path.nodes
            if (nodes.size < 2) return InterchangePath(emptyList(), path.totalWeight, 0)

            val legs = mutableListOf<InterchangeLeg>()
            var legStartIdx = 0

            // seed first hop
            var curLine: String? = resolveLineBetween(nodes[0], nodes[1])
            var accWeight = hopWeight(nodes[0], nodes[1])

            var i = 1
            while (i < nodes.lastIndex) {
                val a = nodes[i]
                val b = nodes[i + 1]
                val info = edgeInfo(a, b)
                val nextLine = info.label ?: resolveLineBetween(a, b)
                val nextWeight = info.weight ?: hopWeight(a, b)

                if (aliasEqual(a, b) && nextWeight > 0) {
                    // positive-weight same-station link => finish current ride leg, then add a WALK leg
                    if (i > legStartIdx) {
                        legs += InterchangeLeg(
                            from = nodes[legStartIdx],
                            to = a,
                            line = curLine ?: resolveLineBetween(nodes[legStartIdx], a) ?: "UNKNOWN",
                            stops = i - legStartIdx,
                            weight = accWeight
                        )
                    }
                    legs += InterchangeLeg(
                        from = a,
                        to = b,
                        line = info.label ?: "WALK",
                        stops = 0,
                        weight = nextWeight
                    )
                    // restart ride leg from b
                    legStartIdx = i + 1
                    accWeight = 0
                    curLine = null
                } else if (curLine != null && curLine == nextLine) {
                    // continue on the same line
                    accWeight += nextWeight
                } else {
                    // line change: close previous leg and start new
                    legs += InterchangeLeg(
                        from = nodes[legStartIdx],
                        to = a,
                        line = curLine ?: resolveLineBetween(nodes[legStartIdx], a) ?: "UNKNOWN",
                        stops = i - legStartIdx,
                        weight = accWeight
                    )
                    legStartIdx = i
                    curLine = nextLine
                    accWeight = nextWeight
                }

                i += 1
            }

            // close final leg to the last node (if any ride leg remains)
            if (legStartIdx < nodes.lastIndex) {
                legs += InterchangeLeg(
                    from = nodes[legStartIdx],
                    to = nodes.last(),
                    line = curLine ?: resolveLineBetween(nodes[nodes.lastIndex - 1], nodes.last()) ?: "UNKNOWN",
                    stops = nodes.lastIndex - legStartIdx,
                    weight = accWeight
                )
            }

            // count interchanges from ride legs only (ignore 0-stop WALK legs)
            val rideLegs = legs.count { it.stops > 0 }
            return InterchangePath(
                legs = legs,
                totalWeight = path.totalWeight,
                interchanges = if (rideLegs == 0) 0 else rideLegs - 1
            )
        }

        return paths.map(::toInterchange)
    }

    // --- corrected filterPaths (LMJ-like) ---
    private fun filterPaths(
        rawPaths: List<Path>,
        nearOptimalFactor: Double = 1.25,
        maxPaths: Int = 10
    ): List<Path> {
        if (rawPaths.isEmpty()) return emptyList()

        // base sort for stability
        var paths = rawPaths
            .filter { it.nodes.isNotEmpty() }
            .sortedWith(compareBy<Path> { it.totalWeight }.thenBy { it.nodes.size })
            .toMutableList()
        if (paths.isEmpty()) return emptyList()

        // cap to small working set
        if (paths.size > maxPaths) paths = paths.take(maxPaths).toMutableList()

        // simple paths only (no cycles)
        paths = paths.filter { p -> p.nodes.size == p.nodes.toSet().size }.toMutableList()
        if (paths.isEmpty()) return emptyList()

        // no blank codes
        paths = paths.filter { p -> p.nodes.none { it.isBlank() } }.toMutableList()
        if (paths.isEmpty()) return emptyList()

        // collapse consecutive alias-equal nodes ONLY when the hop has zero weight
        fun collapseZeroWeightAliases(nodes: List<String>): List<String> {
            if (nodes.size < 2) return nodes
            val out = ArrayList<String>(nodes.size)
            out += nodes.first()
            for (i in 1 until nodes.size) {
                val prev = out.last()
                val cur = nodes[i]
                if (aliasEqual(prev, cur) && hopWeight(prev, cur) == 0) {
                    // skip cur (same station, zero-cost)
                    continue
                }
                out += cur
            }
            return out
        }
        paths = paths.map { p -> p.copy(nodes = collapseZeroWeightAliases(p.nodes)) }
            .filter { it.nodes.size >= 2 }
            .toMutableList()
        if (paths.isEmpty()) return emptyList()

        // minimal schedule-esque sanity: require at least one hop
        paths = paths.filter { it.nodes.size >= 2 }.toMutableList()
        if (paths.isEmpty()) return emptyList()

        // keep near-optimal by weight (same tolerance as before)
        val best = paths.minOf { it.totalWeight }
        paths = paths.filter { it.totalWeight <= best * nearOptimalFactor }.toMutableList()

        return paths
    }


}
