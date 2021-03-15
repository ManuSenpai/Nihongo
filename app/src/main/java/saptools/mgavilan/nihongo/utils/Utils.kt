package saptools.mgavilan.nihongo.utils

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import saptools.mgavilan.nihongo.MainActivity
import saptools.mgavilan.nihongo.R
import saptools.mgavilan.nihongo.data.KanjiItem
import saptools.mgavilan.nihongo.data.YearUnit
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class Utils {
    companion object {
        fun getJsonDataFromAsset(context: Context, fileName: String): String? {
            val jsonString: String
            try {
                jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                return null
            }
            return jsonString
        }

        // Fragment Calling from Fragment
        fun fragmentCalling(myContext: Context, fragmentManager: FragmentManager, fragment: Fragment) {
            try {
                hideKeyboard(myContext as Activity)
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.frame_container, fragment!!)
                //                transaction.addToBackStack(null);
                //                transaction.disallowAddToBackStack()
                transaction.commit()
            } catch (e: Exception) {
            }
        }

        /**
         * Gets value from internal caché
         * @param sharedID String Internal Cache Register ID
         * @param value String value to retrieve
         * @param defValue String value to be returned in case value is not found
         */
        fun getSharedValue(mContext: Context,
                           sharedID: String,
                           value: String,
                           defValue: String = ""): String? {
            var preferences = mContext.getSharedPreferences(sharedID, Context.MODE_PRIVATE)
            return preferences.getString(value, defValue)
        }

        /**
         * @param sharedID
         */
        fun storeSharedValue(mContext: Context, sharedID: String, key: String, value: String) {
            var preferences = mContext.getSharedPreferences(sharedID, Context.MODE_PRIVATE)
            var editor = preferences.edit()
            editor.putString(key, value)
            editor.commit()
        }

        /**
         * Hides soft keyboard
         */
        fun hideKeyboard(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        /**
         * Generates N random units not containing given kanji
         */
        fun getNRandomUnitsWithoutCurrentKanji( nUnits: Int, notContainingThisKanji: String): ArrayList<KanjiItem>{
            val randomUnitsWithNoSlash =
                MainActivity.listOfQuestions.filter { it.getKunyomi() != "—"
                        && it.getOnyomi() != "—"
                        && it.kanji != notContainingThisKanji }

            Collections.shuffle(randomUnitsWithNoSlash)
            val result = ArrayList<KanjiItem>()
            for ( i in 0 until nUnits ) { result.add(randomUnitsWithNoSlash[i]) }

            return result
        }

        /**
         * Generates a CardView containing the question itself
         * @param context Context of the fragment
         * @param questionText String The text that comes in the question
         */
        fun generateCardView( context: Context, questionText: String ): CardView {

            val cardView = CardView(context)
            cardView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
            cardView.setPadding(16, 16, 16, 16)

            val answerTV = TextView(context)
            answerTV.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f
            )
            answerTV.textSize = 20f
            answerTV.text = questionText
            answerTV.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            answerTV.gravity = Gravity.CENTER or Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL

            cardView.addView(answerTV)
            return cardView
        }
    }
}