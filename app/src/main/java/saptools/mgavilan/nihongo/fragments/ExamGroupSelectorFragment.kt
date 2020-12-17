package saptools.mgavilan.nihongo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_exam_select_units.view.*
import saptools.mgavilan.nihongo.MainActivity
import saptools.mgavilan.nihongo.R
import saptools.mgavilan.nihongo.fragments.adapters.ExamSelectableGroupAdapter

class ExamGroupSelectorFragment : Fragment() {

    var rootView: View? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_exam_select_units, container, false)

        val unitList = rootView!!.unit_display_LL
        layoutManager = LinearLayoutManager(activity!!)
        unitList.layoutManager = layoutManager
        unitList.adapter = ExamSelectableGroupAdapter( MainActivity.course!!.schoolYears, activity!!)

        return rootView

    }
}