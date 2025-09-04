package org.corexero.indianmetro.hyderabad

import android.app.Application
import com.codeancy.metroui.di.metroUiModule
import com.corexero.nativelib.NativeLib
import org.corexero.indianmetrocore.di.indianMetroModule
import org.corexero.sutradhar.di.sutraDharModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HyderabadApplication : Application() {

    override fun onCreate() {
        NativeLib.getDBEncryptKey()
        super.onCreate()
        startKoin {
            androidContext(this@HyderabadApplication)
            modules(
                hyderabadModule,
                metroUiModule,
                indianMetroModule,
                sutraDharModule
            )
        }

    }

}