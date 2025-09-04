package org.corexero.indianmetro.hyderabad

import org.koin.core.qualifier.named
import org.koin.dsl.module

val hyderabadModule = module {
    single(named("cityId")) { 1L }
}