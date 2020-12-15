package saptools.mgavilan.nihongo.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import saptools.mgavilan.nihongo.MainActivity
import saptools.mgavilan.nihongo.R
import saptools.mgavilan.nihongo.fragments.adapters.MainMenuRecViewAdapter
import saptools.mgavilan.nihongo.fragments.adapters.UnitRecViewAdapter
import saptools.mgavilan.nihongo.utils.Utils

class HomeFragment : Fragment() {

    var rootView: View? = null
    var mainView: RecyclerView? = null
    var unitView: RecyclerView? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var unitLayoutManager: RecyclerView.LayoutManager? = null
    var mainAdapter: MainMenuRecViewAdapter? = null
    var unitAdapter: UnitRecViewAdapter? = null
    var backButton: ImageView? = null
    var areatitle:TextView? = null
    var unitLayout: LinearLayout? = null
    var kanjiList: LinearLayout? = null
    var studyBtn: Button? = null

    var currentYear: Int = -1
    var currentUnit: Int = -1

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
        backButton = rootView?.findViewById<ImageButton>(R.id.back_btn)
        areatitle = rootView?.findViewById(R.id.areatitle)
        unitLayout = rootView?.findViewById(R.id.options_LL)
        kanjiList = unitLayout?.findViewById(R.id.kanji_display_LL)
        areatitle?.text = "Selecciona curso"
        backButton?.visibility = View.GONE
        mainAdapter = MainMenuRecViewAdapter( MainActivity.course!!.schoolYears ){
            mainView?.visibility = View.GONE
            showUnits(it)
        }
        mainView?.layoutManager = layoutManager
        mainView?.adapter = mainAdapter
        studyBtn = rootView?.findViewById(R.id.study_unit_btn)
        studyBtn?.setOnClickListener {
            MainActivity.currentYear = currentYear
            MainActivity.currentUnit = currentUnit
            Utils.storeSharedValue(activity!!, "kanji", "kanji", "0")
            Utils.fragmentCalling(activity!!, fragmentManager!!, KanjiSummaryFragment())
        }

        if ( MainActivity.currentYear != -1 && MainActivity.currentUnit != -1 ) {
            currentYear = MainActivity.currentYear
            currentUnit = MainActivity.currentUnit
            mainView?.visibility = View.GONE
            goToUnitScreen( currentUnit )
        }

        return rootView
    }

    private fun showUnits( position: Int ) {
        currentYear = position
        backButton?.visibility = View.VISIBLE
        areatitle?.text = MainActivity.course!!.schoolYears[currentYear].title
        backButton?.setOnClickListener{
            backToMenuFromList()
        }
        unitView?.removeAllViewsInLayout()
        unitAdapter = UnitRecViewAdapter(_items = MainActivity.course!!.schoolYears[currentYear].units, context = activity!!) {
            it -> goToUnitScreen(it)
        }
        unitView?.layoutManager = unitLayoutManager
        unitView?.adapter = unitAdapter
        unitView?.visibility = View.VISIBLE
    }

    private fun backToMenuFromList() {
        backButton?.visibility = View.GONE
        mainView?.visibility = View.VISIBLE
        unitView?.visibility = View.GONE
        areatitle?.text = "Selecciona curso"
    }

    private fun backToListFromUnit () {
        unitLayout?.visibility = View.GONE
        showUnits(currentYear)
    }

    fun goToUnitScreen( position: Int ) {
        currentUnit = position
        unitLayout?.visibility = View.VISIBLE
        unitView?.visibility = View.GONE
        kanjiList?.removeAllViewsInLayout()

        backButton?.visibility = View.VISIBLE
        areatitle?.text = MainActivity.course!!.schoolYears[currentYear].units[currentUnit].title

        var currentKanjiLayout: LinearLayout? = null
        for ( i in MainActivity.course!!.schoolYears[currentYear].units[currentUnit].kanjis.indices) {
            val textView = TextView(activity!!)
            textView.text = MainActivity.course!!.schoolYears[currentYear].units[currentUnit].kanjis[i].kanji
            textView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            textView.textSize = 40F
            textView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            if ( i % 5 == 0 ) {
                var horizontalLayout = LinearLayout(activity!!)
                horizontalLayout.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
                )
                currentKanjiLayout = horizontalLayout
                kanjiList?.addView(currentKanjiLayout)
            }
            textView.setOnClickListener {
                MainActivity.currentYear = currentYear
                MainActivity.currentUnit = currentUnit
                MainActivity.fragment = KanjiSummaryFragment()
                Utils.storeSharedValue(activity!!, "kanji", "kanji", i.toString() )
                Utils.fragmentCalling(activity!!, fragmentManager!!, KanjiSummaryFragment())
            }
            currentKanjiLayout?.addView(textView)
        }

        backButton?.setOnClickListener{
            backToListFromUnit()
        }
    }

}