package org.corexero.indianmetro.metro

import android.app.Application
import com.corexero.nativelib.NativeLib
import org.corexero.indianmetrocore.di.indianMetroModule
import org.corexero.sutradhar.di.sutraDharModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MetroApplication : Application() {

    override fun onCreate() {
        NativeLib.getDBEncryptKey()
        super.onCreate()
        startKoin {
            androidContext(this@MetroApplication)
            androidLogger(Level.DEBUG)
            modules(
                metroModule,
                indianMetroModule,
                sutraDharModule
            )
        }

    }

}