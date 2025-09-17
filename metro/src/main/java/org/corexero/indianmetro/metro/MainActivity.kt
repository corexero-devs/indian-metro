package org.corexero.indianmetro.metro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.res.stringResource
import com.codeancy.metroui.app.App
import com.codeancy.metroui.common.utils.LocalMetroConfiguration
import com.codeancy.metroui.common.utils.MapDrawableResource
import com.codeancy.metroui.common.utils.MetroConfiguration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            CompositionLocalProvider(
                LocalMetroConfiguration provides MetroConfiguration(
                    appTitle = stringResource(R.string.app_name),
                    allowLocationButtonText = stringResource(R.string.allow_location_button_text),
                    mapDrawableResource = MapDrawableResource(
                        R.drawable.map
                    ),
                    appName = packageName,
                    appVersion = BuildConfig.VERSION_NAME,
                )
            ) {
                App()
            }
        }
    }
}