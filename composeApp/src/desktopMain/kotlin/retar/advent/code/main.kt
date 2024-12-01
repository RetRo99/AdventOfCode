package retar.advent.code

import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import retar.advent.code.navigation.DefaultRootComponent
import javax.swing.SwingUtilities

@OptIn(ExperimentalDecomposeApi::class)
fun main() {
    val lifecycle = LifecycleRegistry()

    val rootComponent = runOnUiThread {
        DefaultRootComponent(DefaultComponentContext(lifecycle))
    }
    application {
        val windowState = rememberWindowState()
        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Advent of Code",
        ) {
            LifecycleController(
                lifecycleRegistry = lifecycle,
                windowState = windowState,
                windowInfo = LocalWindowInfo.current,
            )
            App(rootComponent = rootComponent)
        }
    }
}

internal fun <T> runOnUiThread(block: () -> T): T {
    if (SwingUtilities.isEventDispatchThread()) {
        return block()
    }

    var error: Throwable? = null
    var result: T? = null

    SwingUtilities.invokeAndWait {
        try {
            result = block()
        } catch (e: Throwable) {
            error = e
        }
    }

    error?.also { throw it }

    @Suppress("UNCHECKED_CAST")
    return result as T
}