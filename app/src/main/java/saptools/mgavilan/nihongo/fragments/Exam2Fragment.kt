package saptools.mgavilan.nihongo.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.os.ProxyFileDescriptorCallback
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.fragment_exam1.*
import kotlinx.android.synthetic.main.kanji_summary_card.*
import org.w3c.dom.Text
import saptools.mgavilan.nihongo.MainActivity
import saptools.mgavilan.nihongo.R
import saptools.mgavilan.nihongo.data.KanjiItem
import saptools.mgavilan.nihongo.data.PlatinumItem
import saptools.mgavilan.nihongo.data.YearUnit
import saptools.mgavilan.nihongo.utils.Utils
import java.util.concurrent.ThreadLocalRandom

/**
 * A simple [Fragment] subclass.
 * Use the [Exam2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Exam2Fragment( unitList: ArrayList<KanjiItem>) : Fragment() {

    var rootView: View? = null
    val platinumItems: ArrayList<PlatinumItem> = ArrayList()

    lateinit var kanjiTV: TextView
    lateinit var onyomi: LinearLayout
    lateinit var kunyomi: LinearLayout
    lateinit var definition: TextView
    lateinit var questionArea: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.kanji_summary_card, container, false)

        kanjiTV = rootView!!.findViewById(R.id.kanji)
        onyomi = rootView!!.findViewById(R.id.onyomiLL)
        kunyomi = rootView!!.findViewById(R.id.kunnyomiLL)
        definition = rootView!!.findViewById(R.id.meaning_text)
        questionArea = rootView!!.findViewById(R.id.examplesLL)

        return rootView
    }

    /**
     * Generates an active layout randomly with only one visible item
     */
    private fun generateQuestion( kanjiItem: KanjiItem ) {
        val typeOfQuestion = ThreadLocalRandom.current().nextInt(0, 4)
        /* Types
            0 -> Show Kanji
            1 -> Show Onyomi
            2 -> Show Kunyomi
            3 -> Show definition
         */
        when ( typeOfQuestion ) {
            0 -> {
                kanjiTV.text = kanjiItem.kanji
                cleanLinearLayoutAndfillWithInterrogations( onyomi )
                cleanLinearLayoutAndfillWithInterrogations( kunyomi )
                definition.text = "????"
            }
            1 -> {
                kanjiTV.text = "?"
                fillLinearLayoutWithInformation( onyomi, kanjiItem.onyomi )
                cleanLinearLayoutAndfillWithInterrogations( kunyomi )
                definition.text = "????"
            }
            2 -> {
                kanjiTV.text = "?"
                cleanLinearLayoutAndfillWithInterrogations( onyomi )
                fillLinearLayoutWithInformation( kunyomi, kanjiItem.kunyomi )
                definition.text = "????"
            }
            else -> {
                kanjiTV.text = "?"
                cleanLinearLayoutAndfillWithInterrogations( onyomi )
                cleanLinearLayoutAndfillWithInterrogations( onyomi )
                definition.text = kanjiItem.getMeaning()
            }
        }

        val arrayOfTypes = ArrayList<Int>()
        for ( i in 0 until 4 ) {
            if ( i != typeOfQuestion &&
                ((typeOfQuestion == 1 && kanjiItem.getOnyomi() != "—") ||
                (typeOfQuestion == 1 && kanjiItem.getOnyomi() != "—"))) {
                arrayOfTypes.add(i)
            }
        }
        nextQuestion(0, arrayOfTypes, kanjiItem, Utils.getNRandomUnitsWithoutCurrentKanji(3, kanjiItem.kanji))
    }

    /**
     *
     */
    private fun nextQuestion( currentPosition: Int, questions: ArrayList<Int>, kanjiItem: KanjiItem, randomUnits: ArrayList<KanjiItem> ) {
        questionArea.removeAllViewsInLayout()
        val titleTextView = TextView(requireActivity())

        when ( questions[currentPosition] ) {
            0 -> { titleTextView.text = "Selecciona kanji correcto" }
            1 -> { titleTextView.text = "Selecciona onyomi correcto" }
            2 -> { titleTextView.text = "Selecciona kunyomi correcto" }
            3 -> { titleTextView.text = "Selecciona definición correcta" }
        }

    }

    /**
     * Fills a Linear Layout with interrogations
     */
    private fun cleanLinearLayoutAndfillWithInterrogations( ll: LinearLayout ) {
        ll.removeAllViewsInLayout()
        val textView: TextView = TextView(requireActivity())
        textView.text = "????"
        textView.textSize = 20f
        textView.typeface = Typeface.DEFAULT_BOLD
        ll.addView( textView )
    }

    /**
     * Fills a Linear Layout with information
     */
    private fun fillLinearLayoutWithInformation ( ll: LinearLayout, information: List<String> ) {
        ll.removeAllViewsInLayout()
        val currentTV: TextView = TextView(requireActivity())
        currentTV.textSize = 20f
        currentTV.typeface = Typeface.DEFAULT_BOLD
        var currentText = ""
        for (on in information.indices) {
            currentText += information[on]
            if ( on < information.size - 1 ) currentText += "    "
        }
        currentTV.text = currentText
        ll.addView(currentTV)
    }

    /**
     * Fills the answers area with responses of a given type
     */
    private fun fillOptionsFromCertainType( type: Int, units: ArrayList<KanjiItem>, callback: () -> Unit ) {
        units.forEach { unit ->
            val questionCard = Utils.generateCardView(
                requireActivity(),
                when(type) {
                    0 -> unit.kanji
                    1 -> unit.getOnyomi()
                    2 -> unit.getKunyomi()
                    else -> unit.getMeaning()
                }
            )
            questionCard.setOnClickListener { callback() }
            questionArea.addView( questionCard )
        }
    }

}