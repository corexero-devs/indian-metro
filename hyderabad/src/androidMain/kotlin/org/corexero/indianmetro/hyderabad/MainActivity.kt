package org.corexero.indianmetro.hyderabad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import com.codeancy.metroui.app.App
import com.codeancy.metroui.common.utils.LocalMetroConfiguration
import com.codeancy.metroui.common.utils.MetroConfiguration
import indianmetro.metroui.generated.resources.Res
import indianmetro.metroui.generated.resources.map

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            CompositionLocalProvider(
                LocalMetroConfiguration provides MetroConfiguration(
                    appTitle = "Delhi Metro Yatri",
                    allowLocationButtonText = "Allow Location Access",
                    mapDrawableResource = Res.drawable.map,
                    appName = packageName,
                    appVersion = ""
                )
            ) {
                App()
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}