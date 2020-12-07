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

    var kanji: TextView? = null
    var onyomiLL: LinearLayout? = null
    var kunyomiLL: LinearLayout? = null
    var meaning: TextView? = null
    var examples: LinearLayout? = null
    var forwardBtn: ImageView? = null
    var backBtn: ImageView? = null
    var exitBtn: ImageView? = null
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
        setKanji(currentValue)

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

        backBtn?.setOnClickListener {
            setKanji( currentValue - 1 )
            currentValue--
        }

        forwardBtn?.setOnClickListener {
            setKanji( currentValue + 1 )
            currentValue++
        }

        exitBtn?.setOnClickListener {
            Utils.fragmentCalling(activity!!, fragmentManager!!, HomeFragment()!!)
        }
    }

    private fun setKanji(value: Int) {

        val thisUnit = MainActivity.course!!.schoolYears[MainActivity.currentYear]!!.units[MainActivity.currentUnit]

        if (value == 0) {
            backBtn?.visibility = View.GONE
            if (thisUnit.kanjis.size > 1) {
                forwardBtn?.visibility = View.VISIBLE
            }
        } else {
            if (value == thisUnit.kanjis.size - 1) {
                backBtn?.visibility = View.VISIBLE
                forwardBtn?.visibility = View.GONE
            } else {
                backBtn?.visibility = View.VISIBLE
                forwardBtn?.visibility = View.VISIBLE
            }
        }

        kanji?.text = thisUnit.kanjis[value].kanji

        onyomiLL?.removeAllViewsInLayout()
        for ( on in thisUnit.kanjis[value].onyomi.indices) {
            val textView: TextView = TextView(activity!!)
            textView.text = thisUnit.kanjis[value].onyomi[on]
            onyomiLL?.addView(textView)
        }

        kunyomiLL?.removeAllViewsInLayout()
        for ( on in thisUnit.kanjis[value].kunyomi.indices) {
            val textView: TextView = TextView(activity!!)
            textView.text = thisUnit.kanjis[value].kunyomi[on]
            kunyomiLL?.addView(textView)
        }

        var text = ""
        for ( on in thisUnit.kanjis[value].meaning.indices) {
            val textView: TextView = TextView(activity!!)
            text += thisUnit.kanjis[value].meaning[on]
            if ( on < thisUnit.kanjis[value].meaning.size -1 ) text += "; "
        }
        meaning?.text = text

    }

}