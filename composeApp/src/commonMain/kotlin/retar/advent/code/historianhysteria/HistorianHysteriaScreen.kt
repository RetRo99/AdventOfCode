package retar.advent.code.historianhysteria

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun HistorianHysteriaScreen(
    component: HistorianHysteriaComponent,
    modifier: Modifier,
) {

    val viewState by component.viewState.subscribeAsState()

    HistorianHysteriaContent(
        viewState = viewState,
        onInputChanged = component::onInputChanged,
        onCalculateDistance = component::calculateDistance,
        modifier = modifier,
    )
}

@Composable
private fun HistorianHysteriaContent(
    viewState: HistorianHysteriaViewState,
    onInputChanged: (String) -> Unit,
    onCalculateDistance: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text("Paste your input here:")

        TextField(
            value = viewState.input,
            maxLines = 5,
            onValueChange = onInputChanged,
        )

        when {
            viewState.result != null -> {
                val clipboardManager = LocalClipboardManager.current
                TextButton(
                    onClick = {
                        clipboardManager.setText(
                            annotatedString = buildAnnotatedString {
                                append(text = viewState.result.distance)
                            })
                    }) {
                    Text("Distance: ${viewState.result.distance}")
                }

                TextButton(
                    onClick = {
                        clipboardManager.setText(
                            annotatedString = buildAnnotatedString {
                                append(text = viewState.result.similarityScore)
                            })
                    }) {
                    Text("Similarity score: ${viewState.result.similarityScore}")
                }
                Text("Click on the result to copy it to the clipboard")
            }

            viewState.isError -> {
                Text("Error, please try again")
            }

            else -> {
                Button(onClick = onCalculateDistance) {
                    Text("Calculate distance")
                }
            }
        }
    }

}