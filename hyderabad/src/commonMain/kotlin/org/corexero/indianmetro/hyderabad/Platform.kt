package org.corexero.indianmetro.hyderabad

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform