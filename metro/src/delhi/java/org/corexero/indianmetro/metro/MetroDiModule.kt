package org.corexero.indianmetro.metro

import org.corexero.indianmetrocore.graphs.model.City
import org.koin.dsl.module

val metroModule = module {
    single { City.Delhi }
}