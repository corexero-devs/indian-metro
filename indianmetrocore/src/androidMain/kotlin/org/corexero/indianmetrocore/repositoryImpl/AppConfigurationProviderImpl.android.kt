package org.corexero.indianmetrocore.repositoryImpl

import android.content.Context
import android.os.Build
import com.corexero.nativelib.NativeLib
import org.corexero.sutradhar.appConfig.AppConfiguration
import org.corexero.sutradhar.appConfig.AppConfigurationProvider

actual class AppConfigurationProviderImpl(
    private val context: Context
) :
    AppConfigurationProvider {
    actual override fun getAppConfiguration(): AppConfiguration {
        return AppConfiguration(
            productId = getProductId(),
            platformUserAgent = getPlatFormUserAgent(),
            reviewApiKey = NativeLib.getReviewApiKey(),
            notificationApiKey = NativeLib.getReviewApiKey(),
            notificationIcon = 1,
            packageName = context.applicationInfo.packageName
        )
    }

    // PlatformName + AppVersion + (DeviceModel) + (OSVersion) + (AppName)
    private fun getPlatFormUserAgent(): String {
        val appVersion = ""
        val deviceModel = Build.MODEL ?: "UnknownModel"
        val osVersion = Build.VERSION.RELEASE ?: "UnknownOS"
        val appName = context.applicationInfo.loadLabel(context.packageManager).toString()
        return "Android $appVersion ($deviceModel) (Android $osVersion) ($appName)"
    }

}