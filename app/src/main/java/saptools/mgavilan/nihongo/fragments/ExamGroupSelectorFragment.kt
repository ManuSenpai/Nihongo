package saptools.mgavilan.nihongo.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_exam1.*
import kotlinx.android.synthetic.main.fragment_exam_select_units.view.*
import kotlinx.android.synthetic.main.kanji_summary_card.*
import saptools.mgavilan.nihongo.MainActivity
import saptools.mgavilan.nihongo.R
import saptools.mgavilan.nihongo.data.KanjiItem
import saptools.mgavilan.nihongo.data.Question
import saptools.mgavilan.nihongo.data.YearUnit
import saptools.mgavilan.nihongo.fragments.adapters.ExamSelectableGroupAdapter
import java.time.Year
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.collections.ArrayList

const val MAX_NUM_OF_ANSWERS: Int = 4

class ExamGroupSelectorFragment : Fragment() {

    var rootView: View? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_exam_select_units, container, false)

        val unitList = rootView!!.unit_display_LL
        layoutManager = LinearLayoutManager(activity!!)
        unitList.layoutManager = layoutManager
        unitList.adapter = ExamSelectableGroupAdapter(MainActivity.course!!.schoolYears, activity!!)

        rootView!!.study_unit_btn.setOnClickListener {
            val selectedUnits = ArrayList<YearUnit>()
            MainActivity.course!!.schoolYears.forEach { year ->
                selectedUnits.addAll( year.units.filter { un -> un.isSelected })
            }

            val questionList = generateQuestions(selectedUnits)

            MainActivity.fragment = Exam1Fragment( questionList )
            MainActivity.fragmentCalling(requireActivity(), fragmentManager!!)
        }

        return rootView

    }

    /**
     * Generates a list of Kanji Items and then picks randomly n kanjis to create an array list of questions.
     */
    @RequiresApi(Build.VERSION_CODES.N)
    fun generateQuestions(
        selectedUnits: ArrayList<YearUnit>,
        nQuestions: Int = 10
    ): ArrayList<Question> {
        val availableKanjis = ArrayList<KanjiItem>()
        val selectedQuestions = ArrayList<KanjiItem>()
        val questionList = ArrayList<Question>()

        // Getting all available kanjis to generate the questions.
        selectedUnits.forEach { unit -> availableKanjis.addAll( unit.kanjis ) }

        // We shuffle the questions
        availableKanjis.shuffle()

        // Creating the questions
        for ( i in 0 until nQuestions ) {
            selectedQuestions.add(availableKanjis[0])
            availableKanjis.removeAt(0)
        }

        for ( i in 0 until selectedQuestions.size ) {
            questionList.add( generateSingleQuestion( availableKanjis, selectedQuestions[i]) )
        }

        return questionList
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun generateSingleQuestion (availableQuestions: ArrayList<KanjiItem>, kanjiItem: KanjiItem): Question {

        val answers = ArrayList<String>()
        // Getting the random answers from other kanjis
        availableQuestions.shuffle()
        for( i in 0 until MAX_NUM_OF_ANSWERS - 1) {
            answers.add(availableQuestions[i].getMeaning())
        }

        // Setting the position of the correct answer
        val correctPosition = ThreadLocalRandom.current().nextInt(0, MAX_NUM_OF_ANSWERS)
        answers.add(correctPosition, kanjiItem.getMeaning())

        return Question(
            kanjiItem.kanji,
            answers,
            correctPosition,
            -1
        )
    }
}