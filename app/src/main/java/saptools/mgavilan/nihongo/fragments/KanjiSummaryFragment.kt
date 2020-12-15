package saptools.mgavilan.nihongo.fragments

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.kanji_example_item.view.*
import kotlinx.android.synthetic.main.kanji_summary_card.*
import saptools.mgavilan.nihongo.MainActivity
import saptools.mgavilan.nihongo.R
import saptools.mgavilan.nihongo.fragments.adapters.KanjiCardAdapter
import saptools.mgavilan.nihongo.utils.Utils

class KanjiSummaryFragment : Fragment() {

    var rootView: View? = null

    var viewPager: ViewPager? = null
    var currentValue: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        rootView = inflater.inflate(R.layout.kanji_summary_card, container, false)
        rootView = inflater.inflate(R.layout.kanji_summary_container, container, false)
        currentValue = Utils.getSharedValue(activity!!, "kanji", "kanji")!!.toInt()

        setUI()

        return rootView
    }

    private fun setUI() {
        viewPager = rootView?.findViewById(R.id.viewpager)
        viewPager?.adapter = KanjiCardAdapter(activity!!)
        viewPager?.setCurrentItem(currentValue, true)
    }

}