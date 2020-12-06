package saptools.mgavilan.nihongo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import saptools.mgavilan.nihongo.data.Course
import saptools.mgavilan.nihongo.data.SchoolYear
import saptools.mgavilan.nihongo.fragments.FavoriteFragment
import saptools.mgavilan.nihongo.fragments.HomeFragment
import saptools.mgavilan.nihongo.fragments.SettingsFragment
import saptools.mgavilan.nihongo.fragments.adapters.ViewPagerAdapter
import saptools.mgavilan.nihongo.utils.Utils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpTabs()
        fillSchoolYears()

    }

    companion object {
        var course: Course? = null
    }

    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment(), getString(R.string.home))
        adapter.addFragment(FavoriteFragment(), getString(R.string.favourite))
        adapter.addFragment(SettingsFragment(), getString(R.string.settings))
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_home)
        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_star)
        tabs.getTabAt(2)!!.setIcon(R.drawable.ic_settings)
    }

    private fun fillSchoolYears() {
        val jsonFileString = Utils.getJsonDataFromAsset(applicationContext, "schoolyears.json")

        val gson = Gson()

        course = gson.fromJson(jsonFileString, Course::class.java)
    }
}