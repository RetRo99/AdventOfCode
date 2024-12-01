package retar.advent.code.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.webhistory.WebHistoryController
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kotlinx.serialization.Serializable
import retar.advent.code.historianhysteria.DefaultHistorianHysteriaComponent
import retar.advent.code.historianhysteria.HistorianHysteriaComponent

interface RootComponent : BackHandlerOwner {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class HistorianHysteria(val component: HistorianHysteriaComponent) : Child
    }

    fun onBackClicked()

}

@OptIn(ExperimentalDecomposeApi::class)
class DefaultRootComponent(
    componentContext: ComponentContext,
    private val deepLink: DeepLink = DeepLink.None,
    private val webHistoryController: WebHistoryController? = null,
) : RootComponent, ComponentContext by componentContext, BackHandlerOwner {

    private val navigation = StackNavigation<ScreenConfig>()

    override val childStack = childStack(
        source = navigation,
        serializer = ScreenConfig.serializer(),
        initialStack = {
            getInitialStack(
                webHistoryPaths = webHistoryController?.historyPaths,
                deepLink = deepLink,
            )
        },
        handleBackButton = true,
        childFactory = ::childFactory,
    )

    override fun onBackClicked() {
        navigation.pop()
    }

    init {
        webHistoryController?.attach(
            navigator = navigation,
            stack = childStack,
            serializer = ScreenConfig.serializer(),
            getPath = ::getPathForConfig,
            getConfiguration = ::getConfigForPath,
        )
    }

    private fun getPathForConfig(config: ScreenConfig): String =
        when (config) {
            is ScreenConfig.HistorianHysteria -> "/day1"
        }

    private fun getConfigForPath(path: String): ScreenConfig =
        when (path.removePrefix("/")) {
            "day1" -> ScreenConfig.HistorianHysteria
            else -> ScreenConfig.HistorianHysteria
        }

    private fun getInitialStack(
        webHistoryPaths: List<String>?,
        deepLink: DeepLink,
    ): List<ScreenConfig> =
        webHistoryPaths
            ?.takeUnless(List<*>::isEmpty)
            ?.map(::getConfigForPath)
            ?: getInitialStack(deepLink)

    private fun getInitialStack(deepLink: DeepLink): List<ScreenConfig> =
        when (deepLink) {
            is DeepLink.None -> listOf(ScreenConfig.HistorianHysteria)
            is DeepLink.Web -> listOf(getConfigForPath(deepLink.path))
        }

    private fun childFactory(
        screenConfig: ScreenConfig,
        componentContext: ComponentContext
    ): RootComponent.Child = when (screenConfig) {
        ScreenConfig.HistorianHysteria -> RootComponent.Child.HistorianHysteria(
            DefaultHistorianHysteriaComponent(
                componentContext = componentContext,
                deepLink = (deepLink as? DeepLink.Web)?.path ?: "nothing"
            )
        )
    }
}

@Serializable
sealed interface ScreenConfig {
    @Serializable
    data object HistorianHysteria : ScreenConfig
}

sealed interface DeepLink {
    data object None : DeepLink
    class Web(val path: String) : DeepLink
}