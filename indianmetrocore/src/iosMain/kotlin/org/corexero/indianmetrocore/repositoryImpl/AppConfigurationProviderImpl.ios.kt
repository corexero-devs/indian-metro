package org.corexero.indianmetrocore.repositoryImpl

import org.corexero.sutradhar.appConfig.AppConfiguration
import org.corexero.sutradhar.appConfig.AppConfigurationProvider
import platform.Foundation.NSBundle
import platform.UIKit.UIDevice

actual class AppConfigurationProviderImpl() :
    AppConfigurationProvider {
    actual override fun getAppConfiguration(): AppConfiguration {
        return AppConfiguration(
            productId = getProductId(),
            platformUserAgent = getPlatFormUserAgent(),
            reviewApiKey = REVIEW_API_KEY,
            notificationApiKey = NOTIFICATION_API_KEY,
            notificationIcon = 0, // iOS does not use notification icons,
            packageName = NSBundle.mainBundle.bundleIdentifier ?: "UnknownPackage"
        )
    }

    // PlatformName + AppVersion + (DeviceModel) + (OSVersion) + (AppName)
    private fun getPlatFormUserAgent(): String {
        val appVersion =
            NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString") as? String
                ?: "UnknownVersion"
        val deviceModel = "iPhone"
        val osVersion = UIDevice.currentDevice.systemVersion
        val appName =
            NSBundle.mainBundle.infoDictionary?.get("CFBundleName") as? String ?: "UnknownApp"
        return "iOS $appVersion ($deviceModel) (iOS $osVersion) ($appName)"
    }

    companion object {
        private const val REVIEW_API_KEY = "#Corexero@28@virat@abhi@rahul"
        private const val NOTIFICATION_API_KEY =
            "LpynjL15U6RZjfSfTxXm4ygHOv7XPna8J5ApVRAXABESeJ9fJh0JEsLGRgfU0ynLZUdqXhXsJE9hgZqkbMTYn8ThaDk9ihUVWWWIIdg2m8uHfh0FHir8zewVE9lOCEA3"
    }

}