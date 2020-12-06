package saptools.mgavilan.nihongo.data

data class KanjiItem(
    val kanji: String = "",
    val strokes: Int,
    val onyomi: List<String>,
    val kunyomi: List<String>,
    val meaning: List<String>
//    val examples: List<String>
)