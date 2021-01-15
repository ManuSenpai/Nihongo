package saptools.mgavilan.nihongo.fragments

import android.graphics.Typeface
import android.icu.text.DecimalFormat
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import saptools.mgavilan.nihongo.MainActivity
import saptools.mgavilan.nihongo.R
import saptools.mgavilan.nihongo.adapter.Exam1ResultAdapter
import saptools.mgavilan.nihongo.data.KanjiItem
import saptools.mgavilan.nihongo.data.Question
import java.math.RoundingMode
import kotlin.math.round

/**
 * A simple [Fragment] subclass.
 * Use the [Exam1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Exam1ResultsFragment(questionList: ArrayList<Question>) : Fragment() {

    private var questions = ArrayList<Question>()
    var rootView: View? = null
    var mListView: ListView ?= null
    var correctAnswers: Int = 0
    var wrongAnswers: Int = 0

    init {
        this.questions = questionList
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_exam_result_screen, container, false)

        correctAnswers = questions.filter { q -> q.correctAnswer == q.selectedAnswer }.size
        wrongAnswers = questions.size - correctAnswers

        setUI()
        return rootView
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setUI(){
        rootView!!.findViewById<TextView>(R.id.tvCorrectAnswers).text = correctAnswers.toString()
        rootView!!.findViewById<TextView>(R.id.tvWrongAnswers).text = wrongAnswers.toString()
        rootView!!.findViewById<TextView>(R.id.tvFinalMark).text =
            round((correctAnswers.toDouble() / questions.size ) * 10 ).toString()
            //roundToSingleDecimal( (correctAnswers.toDouble() / questions.size ) * 10 )

        mListView = rootView!!.findViewById(R.id.resultList)
        mListView!!.adapter = Exam1ResultAdapter(
            requireActivity(),
            questions
        )

        // If some wrong questions were found
        if ( correctAnswers != questions.size ) {
            rootView!!.findViewById<Button>(R.id.btnRepeatMistakes).visibility = View.VISIBLE
            rootView!!.findViewById<Button>(R.id.btnRepeatMistakes).setOnClickListener {
                // Generate a list of wrong questions
                val wrongQuestionList = questions.filter { q -> q.selectedAnswer != q.correctAnswer }
                wrongQuestionList.forEach { q -> q.selectedAnswer = -1 }

                MainActivity.fragment = Exam1Fragment( ArrayList(wrongQuestionList) )
                MainActivity.fragmentCalling(requireActivity(), fragmentManager!!)
            }
        }

        rootView!!.findViewById<Button>(R.id.btBackToHome).setOnClickListener {
            MainActivity.fragment = HomeFragment( )
            MainActivity.fragmentCalling(requireActivity(), fragmentManager!!)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun roundToSingleDecimal( value: Double ): String {
        val df = DecimalFormat("#.#")
        return df.format( value).toString()
    }

}