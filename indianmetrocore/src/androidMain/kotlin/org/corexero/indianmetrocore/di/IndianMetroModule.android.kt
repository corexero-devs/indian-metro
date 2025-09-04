package org.corexero.indianmetrocore.di

import com.corexero.nativelib.NativeLib
import org.corexero.indianmetrocore.repositoryImpl.AppConfigurationProviderImpl
import org.corexero.sutradhar.appConfig.AppConfigurationProvider
import org.corexero.sutradhar.encryption.EncryptionProvider
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single {
            object : EncryptionProvider {
                override fun getEncryptionKey(): String {
                    return NativeLib.getDBEncryptKey()
                }
            }
        }.bind<EncryptionProvider>()
        singleOf(::AppConfigurationProviderImpl)
            .bind<AppConfigurationProvider>()
    }