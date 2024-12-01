package retar.advent.code.historianhysteria

data class HistorianHysteriaViewState(
    val input: String,
    val result: Result? = null,
    val isError: Boolean = false,
)

data class Result(
    val distance: String,
    val similarityScore: String,
)