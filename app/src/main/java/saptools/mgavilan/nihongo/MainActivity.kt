package saptools.mgavilan.nihongo

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson
import saptools.mgavilan.nihongo.data.Course
import saptools.mgavilan.nihongo.fragments.HomeFragment
import saptools.mgavilan.nihongo.utils.Utils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragment = HomeFragment()
        fragmentCalling(this, supportFragmentManager)

        fillSchoolYears()

    }

    companion object {
        var course: Course? = null
        var currentYear: Int = 0
        var currentUnit: Int = 0
        var fragment: Fragment = HomeFragment()

        fun fragmentCalling(myContext: Context, fragmentManager: androidx.fragment.app.FragmentManager) {
            val fragmentTransaction: androidx.fragment.app.FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    private fun fillSchoolYears() {
        val jsonFileString = Utils.getJsonDataFromAsset(applicationContext, "schoolyears.json")

        val gson = Gson()

        course = gson.fromJson(jsonFileString, Course::class.java)
    }

    override fun onBackPressed() {

    }
}