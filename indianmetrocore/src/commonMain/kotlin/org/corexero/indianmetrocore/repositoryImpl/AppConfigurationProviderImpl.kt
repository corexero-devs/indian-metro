package org.corexero.indianmetrocore.repositoryImpl

import org.corexero.sutradhar.appConfig.AppConfiguration
import org.corexero.sutradhar.appConfig.AppConfigurationProvider

fun getProductId(): String = "indian_metro"

expect class AppConfigurationProviderImpl : AppConfigurationProvider {

    override fun getAppConfiguration(): AppConfiguration

}