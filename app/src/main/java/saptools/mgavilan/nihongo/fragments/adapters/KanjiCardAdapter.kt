package saptools.mgavilan.nihongo.fragments.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.kanji_example_item.view.*
import saptools.mgavilan.nihongo.MainActivity
import saptools.mgavilan.nihongo.R


class KanjiCardAdapter ( _context: Context ): PagerAdapter() {

    val mContext: Context = _context
    val collection: ViewGroup? = null

    override fun instantiateItem(collection: ViewGroup, position: Int): View {
        val layout =
            LayoutInflater.from(mContext).inflate(R.layout.kanji_summary_card, null, false) as View
        collection.addView(layout)

        val kanji = layout.findViewById<TextView>(R.id.kanji)
        val onyomiLL = layout.findViewById<LinearLayout>(R.id.onyomiLL)
        val kunyomiLL = layout.findViewById<LinearLayout>(R.id.kunnyomiLL)
        val meaning = layout.findViewById<TextView>(R.id.meaning_text)
        val examples = layout.findViewById<LinearLayout>(R.id.examplesLL)

        val thisUnit =
            MainActivity.course!!.schoolYears[MainActivity.currentYear]!!.units[MainActivity.currentUnit]

        kanji?.text = thisUnit.kanjis[position].kanji

        onyomiLL?.removeAllViewsInLayout()
        val textView: TextView = TextView(mContext!!)

        textView.textSize = 20f
        textView.typeface = Typeface.DEFAULT_BOLD
        var onText = ""
        for (on in thisUnit.kanjis[position].onyomi.indices) {
            onText += thisUnit.kanjis[position].onyomi[on]
            if ( on < thisUnit.kanjis[position].onyomi.size - 1 ) onText += "    "
        }
        textView.text = onText
        onyomiLL?.addView(textView)

        kunyomiLL?.removeAllViewsInLayout()
        val kunTextView: TextView = TextView(mContext!!)
        kunTextView.textSize = 20f
        kunTextView.typeface = Typeface.DEFAULT_BOLD
        var kunText = ""
        for (on in thisUnit.kanjis[position].kunyomi.indices) {
            kunText += thisUnit.kanjis[position].kunyomi[on]
            if ( on < thisUnit.kanjis[position].kunyomi.size - 1 ) kunText += "    "
        }
        kunTextView.text = kunText
        kunyomiLL?.addView(kunTextView)

        var text = ""
        for (on in thisUnit.kanjis[position].meaning.indices) {
            text += thisUnit.kanjis[position].meaning[on]
            if (on < thisUnit.kanjis[position].meaning.size - 1) text += "; "
        }

        examples?.removeAllViewsInLayout()
        for (on in thisUnit.kanjis[position].examples.indices) {
            val kanjiItemLL: LinearLayout =
                LayoutInflater.from(mContext).inflate(R.layout.kanji_example_item, null, false) as LinearLayout
            kanjiItemLL.example_text.text = thisUnit.kanjis[position].examples[on].example
            kanjiItemLL.transcription_text.text = thisUnit.kanjis[position].examples[on].transcription
            kanjiItemLL.meaning_text.text = thisUnit.kanjis[position].examples[on].meaning

            examples?.addView(kanjiItemLL)
        }

        meaning?.text = text

        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, `object`: Any) {
        collection.removeView(`object` as View?)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return MainActivity.course!!.schoolYears[MainActivity.currentYear]!!.units[MainActivity.currentUnit].kanjis.size
    }
}