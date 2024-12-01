package retar.advent.code.historianhysteria

import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun HistorianHysteriaScreen(
    component: HistorianHysteriaComponent,
    modifier: Modifier,
) {

    val viewState by component.viewState.subscribeAsState()

    HistorianHysteriaContent(
        viewState = viewState,
        modifier = modifier,
    )
}

@Composable
private fun HistorianHysteriaContent(
    viewState: HistorianHysteriaViewState,
    modifier: Modifier = Modifier,
) {
    SelectionContainer {
        Text(
            text = viewState.text,
            modifier = modifier,
        )
    }
}