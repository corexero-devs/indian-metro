package org.corexero.indianmetrocore

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.decodeFromHexString
import kotlinx.serialization.json.Json
import kotlinx.serialization.protobuf.ProtoBuf
import org.corexero.indianmetrocore.protocolBufs.PlatformSequence
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Serializable
data class PlatfronmJson(
    val id: Int,
    val platform_sequence: String
)

@OptIn(ExperimentalEncodingApi::class)
fun exportedBlobStringToBytes(raw: String): ByteArray {
    val s = raw.trim().removeSurrounding("\"", "\"") // JSON exports

    // SQLite: X'ABCD'  or  x'abcd'
    if ((s.startsWith("X'", true) || s.startsWith("x'", true)) && s.endsWith("'")) {
        return s.substring(2, s.length - 1).hexToBytes()
    }
    // Postgres: \xABCD
    if (s.startsWith("\\x")) {
        return s.substring(2).hexToBytes()
    }
    // MySQL: 0xABCD
    if (s.startsWith("0x") || s.startsWith("0X")) {
        return s.substring(2).hexToBytes()
    }
    // Plain hex? (even length, only 0-9A-F)
    if (s.length % 2 == 0 && s.all { it.isDigit() || it in 'a'..'f' || it in 'A'..'F' }) {
        return s.hexToBytes()
    }
    // Try Base64
    return try {
        Base64.decode(s)
    } catch (_: IllegalArgumentException) {
        // Last resort: treat as UTF-8 text bytes (rare)
        s.encodeToByteArray()
    }
}

private fun String.hexToBytes(): ByteArray =
    chunked(2).map { it.toInt(16).toByte() }.toByteArray()

fun String.toHexString(): String =
    this.encodeToByteArray().joinToString("") { "%02x".format(it) }

@OptIn(ExperimentalSerializationApi::class)
fun main() {
    val allValues = Json.decodeFromString<List<PlatfronmJson>>(platformJson)

    allValues.forEach {
        println(it.id)
        parsePlatformSequence(exportedBlobStringToBytes(it.platform_sequence))
    }

}

@OptIn(ExperimentalSerializationApi::class)
fun parsePlatformSequence(data: ByteArray) {
    val decoded = ProtoBuf.decodeFromByteArray<PlatformSequence>(data)
    println(decoded)
}