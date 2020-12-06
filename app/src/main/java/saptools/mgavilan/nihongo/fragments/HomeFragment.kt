package saptools.mgavilan.nihongo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import saptools.mgavilan.nihongo.MainActivity
import saptools.mgavilan.nihongo.R
import saptools.mgavilan.nihongo.fragments.adapters.MainMenuRecViewAdapter
import saptools.mgavilan.nihongo.fragments.adapters.UnitRecViewAdapter

class HomeFragment : Fragment() {

    var rootView: View? = null
    var mainView: RecyclerView? = null
    var unitView: RecyclerView? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var unitLayoutManager: RecyclerView.LayoutManager? = null
    var mainAdapter: MainMenuRecViewAdapter? = null
    var unitAdapter: UnitRecViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false)

        mainView = rootView?.findViewById(R.id.recView)
        unitView = rootView?.findViewById(R.id.unitRecView)
        layoutManager = LinearLayoutManager(activity!!)
        unitLayoutManager = LinearLayoutManager(activity!!)
        mainAdapter = MainMenuRecViewAdapter( MainActivity.course!!.schoolYears ){ it ->
            mainView?.visibility = View.GONE
            showUnits(it)
        }
        mainView?.layoutManager = layoutManager
        mainView?.adapter = mainAdapter

        return rootView
    }

    private fun showUnits( position: Int ) {
        unitView?.removeAllViewsInLayout()
        unitAdapter = UnitRecViewAdapter(_items = MainActivity.course!!.schoolYears[position].units, context = activity!!)
        unitView?.layoutManager = unitLayoutManager
        unitView?.adapter = unitAdapter
        unitView?.visibility = View.VISIBLE
    }

    private fun hideUnits() {
        mainView?.visibility = View.VISIBLE
        unitView?.visibility = View.GONE
    }
}