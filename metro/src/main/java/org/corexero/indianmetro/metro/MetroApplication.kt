package org.corexero.indianmetro.metro

import android.app.Application
import org.corexero.indianmetrocore.di.indianMetroModule
import org.corexero.indianmetrocore.graphs.model.City
import org.corexero.sutradhar.di.sutraDharModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class MetroApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MetroApplication)
            androidLogger(Level.DEBUG)
            modules(
                module {
                    single {
                        City.entries.first {
                            it.name.lowercase() == BuildConfig.CITY.lowercase()
                        }
                    }
                },
                indianMetroModule,
                sutraDharModule
            )
        }

    }

}