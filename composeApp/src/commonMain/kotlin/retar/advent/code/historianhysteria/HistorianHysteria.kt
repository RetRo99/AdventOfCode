package retar.advent.code.historianhysteria

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

interface HistorianHysteriaComponent {
    val viewState: Value<HistorianHysteriaViewState>

    fun calculateDistance(): Int

}

class DefaultHistorianHysteriaComponent(
    componentContext: ComponentContext,
    deepLink: String,
) : HistorianHysteriaComponent, ComponentContext by componentContext {

    override val viewState: MutableValue<HistorianHysteriaViewState> =
        MutableValue(HistorianHysteriaViewState(deepLink))

    override fun calculateDistance(): Int {
        TODO("Not yet implemented")
    }

}