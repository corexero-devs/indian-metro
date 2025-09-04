package org.corexero.indianmetrocore.di

import org.corexero.indianmetrocore.repositoryImpl.AppConfigurationProviderImpl
import org.corexero.sutradhar.appConfig.AppConfigurationProvider
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        singleOf(::AppConfigurationProviderImpl)
            .bind<AppConfigurationProvider>()
    }