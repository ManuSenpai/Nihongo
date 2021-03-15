package saptools.mgavilan.nihongo.data

data class Course ( val title: String, val schoolYears: List<SchoolYear> ){
    /**
     * Generates a list containing ALL Kanjis from all units.
     */
    fun generateListOfQuestions(): ArrayList<KanjiItem> {
        val result = ArrayList<KanjiItem>()
        schoolYears.forEach { year -> year.units.forEach { unit -> result.addAll( unit.kanjis ) } }
        return result
    }
}