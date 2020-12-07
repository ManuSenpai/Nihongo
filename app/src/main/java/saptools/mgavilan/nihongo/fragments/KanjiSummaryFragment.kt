package saptools.mgavilan.nihongo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import saptools.mgavilan.nihongo.MainActivity
import saptools.mgavilan.nihongo.R
import saptools.mgavilan.nihongo.utils.Utils

class KanjiSummaryFragment : Fragment() {

    var rootView: View? = null

    var kanji: TextView ? = null
    var onyomiLL: LinearLayout ? = null
    var kunyomiLL: LinearLayout ? = null
    var meaning: TextView ? = null
    var examples: LinearLayout ? = null
    var forwardBtn: ImageView ? = null
    var backBtn: ImageView ? = null
    var exitBtn: ImageView ? = null
    var currentValue: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.kanji_summary_card, container, false)
        currentValue = Utils.getSharedValue(activity!!, "kanji", "kanji")!!.toInt()

        setUI()

        if ( currentValue == 0 ) {
            forwardBtn?.visibility = View.GONE
            if ( MainActivity.course!!.schoolYears[MainActivity.currentYear]!!.units[MainActivity.currentUnit].kanjis.size > 1 ) {
                backBtn?.visibility = View.VISIBLE
            }
        } else {
            if ( currentValue == MainActivity.course!!.schoolYears[MainActivity.currentYear]!!.units[MainActivity.currentUnit].kanjis.size - 1 ) {
                backBtn?.visibility = View.GONE
                forwardBtn?.visibility = View.VISIBLE
            } else {
                backBtn?.visibility = View.VISIBLE
                forwardBtn?.visibility = View.VISIBLE
            }
        }

        return rootView
    }

    private fun setUI() {
        kanji = rootView?.findViewById(R.id.kanji)
        onyomiLL = rootView?.findViewById(R.id.onyomiLL)
        kunyomiLL = rootView?.findViewById(R.id.kunnyomiLL)
        meaning = rootView?.findViewById(R.id.meaning_text)
        examples = rootView?.findViewById(R.id.examplesLL)
        forwardBtn = rootView?.findViewById(R.id.forward_btn)
        backBtn = rootView?.findViewById(R.id.back_btn)
        exitBtn = rootView?.findViewById(R.id.exit_btn)

        exitBtn?.setOnClickListener {
            Utils.fragmentCalling(activity!!, fragmentManager!!, HomeFragment()!!)
        }
    }

}