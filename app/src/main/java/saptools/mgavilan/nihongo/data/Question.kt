package saptools.mgavilan.nihongo.data


data class Question(
    val question: String ?= null,
    val answers: ArrayList<String> ?= null,
    val correctAnswer: Int = -1,
    var selectedAnswer: Int = -1,
    var mode: Int = 0, // -1 = Alternate; 0 = Definitions; 1 = Kunyomi; 2 = Onyomi; 3 = Complex
    val subTexts: ArrayList<String> ?= null
){
    fun getModes() : ArrayList<String> {
        return arrayListOf("Alternado", "Definiciones", "Kunyomi", "Onyomi", "Compuestos")
    }
}
