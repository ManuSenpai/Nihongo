package saptools.mgavilan.nihongo.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import saptools.mgavilan.nihongo.R
import java.io.IOException

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
                transaction.replace(R.id.viewPager, fragment!!)
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
    }
}