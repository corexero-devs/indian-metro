package org.corexero.indianmetro.hyderabad

import org.corexero.indianmetrocore.graphs.model.City
import org.koin.dsl.module

val hyderabadModule = module {
    single { City.Delhi }
}