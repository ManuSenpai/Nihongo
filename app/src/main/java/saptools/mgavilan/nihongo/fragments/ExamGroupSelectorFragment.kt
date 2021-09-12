package saptools.mgavilan.nihongo.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_exam_select_units.view.*
import saptools.mgavilan.nihongo.MainActivity
import saptools.mgavilan.nihongo.R
import saptools.mgavilan.nihongo.data.KanjiExample
import saptools.mgavilan.nihongo.data.KanjiItem
import saptools.mgavilan.nihongo.data.Question
import saptools.mgavilan.nihongo.data.YearUnit
import saptools.mgavilan.nihongo.fragments.adapters.ExamSelectableGroupAdapter
import java.util.concurrent.ThreadLocalRandom
import kotlin.collections.ArrayList

const val MAX_NUM_OF_ANSWERS: Int = 4

@Suppress("UNCHECKED_CAST")
class ExamGroupSelectorFragment : Fragment() {

    var rootView: View? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        var examMode: Int = 0
        var nQuestions: Int = 12
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
                selectedUnits.addAll(year.units.filter { un -> un.isSelected })
            }

            // We reset the values
            selectedUnits.forEach { it.isSelected = false }

            var questionList = ArrayList<Question>()
            questionList = if (examMode != 3) {
                generateQuestions(selectedUnits, nQuestions)
            } else {
                generateComplexQuestionary(selectedUnits, nQuestions)
            }

            MainActivity.fragment = Exam1Fragment(questionList,
                if (examMode == 3) 50f else null,
                if ( examMode == 3) 18f else null )
            MainActivity.fragmentCalling(requireActivity(), fragmentManager!!)
        }

        rootView?.ibSettings?.setOnClickListener {
            displayOptionsPopup()
        }

        return rootView

    }

    /**
     * Generates a list of Kanji Items and then picks randomly n kanjis to create an array list of questions.
     */
    @RequiresApi(Build.VERSION_CODES.N)
    fun generateQuestions(
        selectedUnits: ArrayList<YearUnit>,
        _nQuestions: Int = 10
    ): ArrayList<Question> {
        val availableKanjis = ArrayList<KanjiItem>()
        val selectedQuestions = ArrayList<KanjiItem>()
        val questionList = ArrayList<Question>()

        // Getting all available kanjis to generate the questions.
        selectedUnits.forEach { unit -> availableKanjis.addAll(unit.kanjis) }

        // We shuffle the questions
        availableKanjis.shuffle()
        if (_nQuestions > availableKanjis.size) {
            nQuestions = availableKanjis.size
        }

        // Creating the questions
        for (i in 0 until nQuestions) {
            selectedQuestions.add(availableKanjis[i])
        }

        for (i in 0 until selectedQuestions.size) {
            questionList.add(
                generateSingleQuestion(
                    availableKanjis.clone() as ArrayList<KanjiItem>,
                    selectedQuestions, i, when (examMode) {
                        -1 -> ThreadLocalRandom.current().nextInt(0, 3)
                        else -> {
                            if (examMode == 1 && selectedQuestions[i].getKunyomi() == "—") 2
                            else if (examMode == 2 && selectedQuestions[i].getOnyomi() == "—") 1
                            else examMode
                        }
                    }
                )
            )
        }

        return questionList
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun generateComplexQuestionary(
        selectedUnits: ArrayList<YearUnit>,
        _nQuestions: Int = 10
    ): ArrayList<Question> {
        val selectedQuestions = ArrayList<KanjiExample>()
        val complexWords = ArrayList<KanjiExample>()
        val questionList = ArrayList<Question>()

        // Filtering all complex kanjis
        selectedUnits.forEach { unit ->
            unit.kanjis.forEach { kanji ->
                kanji.examples.forEach { exp ->
                    if (exp.complex) {
                        complexWords.add(exp)
                    }
                }
            }
        }

        // We shuffle the questions
        complexWords.shuffle()
        if (_nQuestions > complexWords.size) {
            nQuestions = complexWords.size
        }

        // Creating the questions
        for (i in 0 until nQuestions) {
            selectedQuestions.add(complexWords[i])
        }

        for (i in 0 until selectedQuestions.size) {
            questionList.add(
                generateSingleComplexQuestion(
                    complexWords.clone() as ArrayList<KanjiExample>,
                    selectedQuestions, i

                )
            )
        }

        return questionList
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun generateSingleQuestion(
        availableQuestions: ArrayList<KanjiItem>,
        selectedQuestions: ArrayList<KanjiItem>,
        pos: Int,
        mode: Int = 0
    ): Question {

        val answers = ArrayList<String>()
        // Getting the random answers from other kanjis
        availableQuestions.removeAt(pos)
        availableQuestions.shuffle()
        for (i in 0 until MAX_NUM_OF_ANSWERS - 1) {
            when (mode) {
                0 -> answers.add(availableQuestions[i].getMeaning())
                1 -> answers.add(availableQuestions[i].getKunyomi())
                2 -> answers.add(availableQuestions[i].getOnyomi())
            }
        }

        // Setting the position of the correct answer
        val correctPosition = ThreadLocalRandom.current().nextInt(0, MAX_NUM_OF_ANSWERS)
        when (mode) {
            0 -> answers.add(correctPosition, selectedQuestions[pos].getMeaning())
            1 -> answers.add(correctPosition, selectedQuestions[pos].getKunyomi())
            2 -> answers.add(correctPosition, selectedQuestions[pos].getOnyomi())
        }
        return Question(
            selectedQuestions[pos].kanji,
            answers,
            correctPosition,
            -1
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun generateSingleComplexQuestion(
        availableQuestions: ArrayList<KanjiExample>,
        selectedQuestions: ArrayList<KanjiExample>,
        pos: Int
    ): Question {

        val answers = ArrayList<String>()
        // Getting the random answers from other kanjis
        availableQuestions.removeAt(pos)
        availableQuestions.shuffle()

        for (i in 0 until MAX_NUM_OF_ANSWERS - 1) {
            answers.add(availableQuestions[i].transcription)
        }

        // Setting the position of the correct answer
        val correctPosition = ThreadLocalRandom.current().nextInt(0, MAX_NUM_OF_ANSWERS)
        answers.add(correctPosition, selectedQuestions[pos].transcription)

        val subTexts = ArrayList<String>()
        subTexts.add(selectedQuestions[pos].meaning)

        return Question(
            selectedQuestions[pos].example,
            answers,
            correctPosition,
            -1,
            3,
            subTexts
        )
    }

    fun displayOptionsPopup() {
        val mBuilder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        val nullParent: ViewGroup? = null
        val mView: View =
            layoutInflater.inflate(
                R.layout.popup_exam_settings,
                nullParent
            )
        mBuilder.setView(mView)
        val dialog = mBuilder.create()

        dialog.show()

        val acceptButton = dialog.findViewById<Button>(R.id.btnAccept)
        val examSpinner = dialog.findViewById<Spinner>(R.id.spExamMode)
        val adapter = ArrayAdapter<String>(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            Question().getModes()
        )
        adapter.setNotifyOnChange(true)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        examSpinner?.adapter = adapter
        examSpinner?.setSelection(examMode + 1)

        examSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                examMode = position - 1
            }
        }
        val questionET = dialog.findViewById<EditText>(R.id.etNumberQuestions)
        questionET?.setText(nQuestions.toString())

        acceptButton!!.setOnClickListener {
            nQuestions = if (questionET?.text.toString().toDouble() <= 0) {
                1
            } else {
                questionET?.text.toString().toInt()
            }
            dialog.cancel()
        }
    }
}