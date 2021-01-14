package saptools.mgavilan.nihongo.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import saptools.mgavilan.nihongo.R
import saptools.mgavilan.nihongo.data.KanjiItem
import saptools.mgavilan.nihongo.data.Question

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Exam1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Exam1Fragment(questionList: ArrayList<Question>) : Fragment() {

    private var questions = ArrayList<Question>()
    var rootView: View? = null
    var questionLL: LinearLayout ?= null
    var answerLL: LinearLayout ?= null
    var currentQuestion: Int = 0

    init {
        this.questions = questionList
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_exam1, container, false)

        questionLL = rootView!!.findViewById(R.id.question)
        answerLL = rootView!!.findViewById(R.id.answers)
        generateQuestion(questions[currentQuestion])
        return rootView
    }

    /**
     * Fills the upper part of the screen with the question and the lower part with the answers
     */
    private fun generateQuestion( question: Question ) {
        questionLL!!.removeAllViewsInLayout()
        answerLL!!.removeAllViewsInLayout()

        // Generating Question TextView
        val questionTV = TextView(requireActivity())
        questionTV.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        questionTV.textSize = 80f
        questionTV.typeface = Typeface.DEFAULT_BOLD
        questionTV.text = question.question
        questionTV.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
        questionTV.gravity = Gravity.CENTER or Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL

        questionLL!!.addView(questionTV)

        for ( i in 0 until question.answers!!.size) {
            val cardView = CardView(requireActivity())
            cardView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
            cardView.setPadding(16, 16, 16, 16)
            cardView.setOnClickListener {
                question.selectedAnswer = i
                if ( currentQuestion < questions.size - 1 ) {
                    currentQuestion++
                    generateQuestion( questions[currentQuestion] )
                }
            }

            val answerTV = TextView(requireActivity())
            answerTV.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f
            )
            answerTV.textSize = 20f
            answerTV.text = question.answers[i]
            answerTV.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            answerTV.gravity = Gravity.CENTER or Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL

            cardView.addView(answerTV)
            answerLL!!.addView(cardView)
        }
    }

}