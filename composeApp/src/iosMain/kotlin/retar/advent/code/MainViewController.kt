package retar.advent.code

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle
import retar.advent.code.navigation.DefaultRootComponent

@OptIn(ExperimentalDecomposeApi::class)
fun MainViewController() = ComposeUIViewController {
    val rootComponent = remember {
        DefaultRootComponent(DefaultComponentContext(ApplicationLifecycle()))
    }
    App(rootComponent = rootComponent)
}