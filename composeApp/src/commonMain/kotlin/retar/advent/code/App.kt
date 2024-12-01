package retar.advent.code

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.PredictiveBackParams
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.materialPredictiveBackAnimatable
import org.jetbrains.compose.ui.tooling.preview.Preview
import retar.advent.code.historianhysteria.HistorianHysteriaScreen
import retar.advent.code.navigation.RootComponent

@Composable
@Preview
fun App(rootComponent: RootComponent) {
    MaterialTheme {
        Box(Modifier.fillMaxSize()) {
            RootContent(
                rootComponent
            )
        }
    }
}

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    ChildStack(
        stack = component.childStack,
        modifier = modifier,
        animation = stackAnimation(
            animator = fade() + scale(),
            predictiveBackParams = {
                PredictiveBackParams(
                    backHandler = component.backHandler,
                    onBack = component::onBackClicked,
                    animatable = ::materialPredictiveBackAnimatable,
                )
            },
        ),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.HistorianHysteria -> HistorianHysteriaScreen(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}