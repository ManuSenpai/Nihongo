package saptools.mgavilan.nihongo.data

data class KanjiItem(
    val kanji: String = "",
    val strokes: Int,
    val onyomi: List<String>,
    val kunyomi: List<String>,
    val meaning: List<String>,
    val examples: List<KanjiExample>
//    val examples: List<String>
) {

    fun getMeaning(): String {
        var result = ""

        for ( i in 0 until meaning.size ) {
            result += meaning[i]
            if ( i < meaning.size - 1 ) result += "; "
        }

        return result
    }
}