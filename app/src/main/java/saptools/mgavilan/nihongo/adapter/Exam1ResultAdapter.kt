package saptools.mgavilan.nihongo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.listitem_result_1.view.*
import saptools.mgavilan.nihongo.R
import saptools.mgavilan.nihongo.data.Question

class Exam1ResultAdapter(
    context: Context,
    arrayList: ArrayList<Question>
) : BaseAdapter() {

    var mArrayList = ArrayList<Question>()
    var mContext: Context

    init {
        mContext = context
        mArrayList = arrayList
    }

    override fun getCount(): Int {
        return mArrayList.size
    }

    override fun getItem(p0: Int): Any {
        return mArrayList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(pos: Int, v: View?, vG: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val row = layoutInflater.inflate(R.layout.listitem_result_1, vG, false)

        row.tvKanji.text = mArrayList[pos].question
        row.tvUserAnswer.text = mArrayList[pos].answers!![ mArrayList[pos].selectedAnswer ]
        // If the user made a mistake
        if ( mArrayList[pos].selectedAnswer != mArrayList[pos].correctAnswer ) {
            // Correct answer is displayed
            row.tvUserAnswer.setTextColor( ContextCompat.getColor(mContext, R.color.design_default_color_error) )
            row.tvCorrectAnswer.visibility = View.VISIBLE
            row.tvCorrectAnswer.text = mArrayList[pos].answers!![ mArrayList[pos].correctAnswer ]
            row.ivIcon.setImageResource( R.drawable.ic_cancel )
            row.ivIcon.setColorFilter( ContextCompat.getColor(mContext, R.color.design_default_color_error))
        }

        return row
    }


}