package saptools.mgavilan.nihongo.data

data class Question(
    val question: String ?= null,
    val answers: ArrayList<String> ?= null,
    val correctAnswer: Int = -1,
    val selectedAnswer: Int = -1
)
