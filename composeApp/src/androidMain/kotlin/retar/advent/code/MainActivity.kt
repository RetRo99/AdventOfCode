package retar.advent.code

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.defaultComponentContext
import retar.advent.code.navigation.DefaultRootComponent

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalDecomposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootComponent = DefaultRootComponent(defaultComponentContext())

        setContent {
            App(rootComponent)
        }
    }
}
