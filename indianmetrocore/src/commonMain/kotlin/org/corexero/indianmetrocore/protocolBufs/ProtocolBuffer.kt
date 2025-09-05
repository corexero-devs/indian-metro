package org.corexero.indianmetrocore.protocolBufs

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class PlatformSequence(
    @ProtoNumber(1) val id: String,
    @ProtoNumber(2) val stationPlatformMap: Map<Int, String>
)

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class TripMetadata(@ProtoNumber(1) val flags: Int)

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class Attribute(
    @ProtoNumber(1) val name: String,
    @ProtoNumber(2) val color: String,
    @ProtoNumber(3) val textColor: String
)

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class LineMetadata(
    @ProtoNumber(1) val primary: String? = null,
    @ProtoNumber(2) val secondary: String? = null,
    @ProtoNumber(3) val attributes: List<Attribute>
)

