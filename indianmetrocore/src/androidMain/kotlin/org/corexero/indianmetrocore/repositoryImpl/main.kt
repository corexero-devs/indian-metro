package org.corexero.indianmetrocore.repositoryImpl

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromHexString
import kotlinx.serialization.protobuf.ProtoBuf
import org.corexero.indianmetrocore.protocolBufs.PlatformSequence

@OptIn(ExperimentalSerializationApi::class)
fun main() {
//    val topology = DelhiCityTransitTopology()
//    val adjanceyListManager = AdjanceyListManager(topology)
//    val routeCalculator = RouteCalculator(adjanceyListManager, topology)
//    routeCalculator.findRoutes(
//        start = "M-SKNDR",
//        end = "M-BTN",
//        k = 5
//    ).forEach {
//        println(it)
//    }

    println(ProtoBuf.decodeFromHexString<PlatformSequence>("0a01321205081b1201341205080d120134120508061201341205081f1201341205082112013412050807120134120508131201341205080212013412050811120134"))
}