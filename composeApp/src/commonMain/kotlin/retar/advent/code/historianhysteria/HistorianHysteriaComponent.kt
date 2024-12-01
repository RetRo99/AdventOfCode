package retar.advent.code.historianhysteria

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import kotlin.math.abs

interface HistorianHysteriaComponent {
    val viewState: Value<HistorianHysteriaViewState>

    fun calculateDistance()
    fun onInputChanged(newInput: String)

}

class DefaultHistorianHysteriaComponent(
    componentContext: ComponentContext,
) : HistorianHysteriaComponent, ComponentContext by componentContext {

    override val viewState: MutableValue<HistorianHysteriaViewState> =
        MutableValue(HistorianHysteriaViewState(""))

    override fun calculateDistance() {
        viewState.update {
            it.copy(isError = false)
        }
        try {
            val items = viewState.value.input.split("\n").map {
                it.split(" ").filter(String::isNotBlank).map(String::toInt)
            }

            val firstList = items.map { it.first() }.sorted()
            val secondList = items.map { it.last() }.sorted()
            val distance = firstList.zip(secondList).sumOf { (first, second) ->
                abs(
                    first -
                            second
                )
            }
            val similarityScore = firstList.sumOf { first ->
                val count = secondList.count { second -> first == second }
                first * count
            }
            val result = Result(distance.toString(), similarityScore.toString())
            viewState.update {
                it.copy(result = result)
            }
        } catch (e: Exception) {
            viewState.update {
                it.copy(isError = true)
            }
        }
    }

    override fun onInputChanged(newInput: String) {
        viewState.update {
            it.copy(input = newInput)
        }
    }

}