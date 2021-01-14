package saptools.mgavilan.nihongo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        return inflater.inflate(R.layout.fragment_exam1, container, false)
    }
}