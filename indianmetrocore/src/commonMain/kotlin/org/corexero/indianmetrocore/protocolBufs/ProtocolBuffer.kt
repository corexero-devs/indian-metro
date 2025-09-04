package org.corexero.indianmetrocore.protocolBufs

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoNumber

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class PlatformSequence(
    @ProtoNumber(1) val id: String,
    @ProtoNumber(2) val platforms: Map<Int, String>
)

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class TripMetadata(@ProtoNumber(1) val flags: Int)

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class Attribute(
    @ProtoNumber(1) val key: String,
    @ProtoNumber(2) val value: String,
    @ProtoNumber(3) val extra: String
)

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class LineMetadata(
    @ProtoNumber(1) val primary: String,
    @ProtoNumber(2) val secondary: String,
    @ProtoNumber(3) val attributes: List<Attribute>
)

