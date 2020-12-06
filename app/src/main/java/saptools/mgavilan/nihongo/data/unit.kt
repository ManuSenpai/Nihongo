package saptools.mgavilan.nihongo.data

data class YearUnit (
    val title: String = "",
    val kanjis: List<KanjiItem>,
    var isCompleted: Boolean = false,
    var bestMark: Int = 0
)