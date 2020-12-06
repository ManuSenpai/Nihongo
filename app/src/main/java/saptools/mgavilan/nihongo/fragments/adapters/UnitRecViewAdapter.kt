package saptools.mgavilan.nihongo.fragments.adapters

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.unit_item.view.*
import saptools.mgavilan.nihongo.R
import saptools.mgavilan.nihongo.R.color.nihonOrange
import saptools.mgavilan.nihongo.data.YearUnit

class UnitRecViewAdapter(_items: List<YearUnit>, context: Context): RecyclerView.Adapter<UnitRecViewAdapter.ViewHolder>() {

    var items: List<YearUnit>? = null
    var viewHolder: ViewHolder? = null
    var mContext: Context? = null

    init {
        items= _items
        mContext = context
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.unit_item, parent, false)

        viewHolder = ViewHolder(view)

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return items!!.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.title?.text = item?.title
        holder.bestMark?.text = item?.bestMark.toString()
        val nStars = item?.bestMark!! / 2
        for ( i in 0 until nStars ) {
            val iv = ImageView( mContext )
            iv.setImageDrawable(mContext?.getDrawable(R.drawable.ic_full_star))
            iv.setColorFilter(ContextCompat.getColor(mContext!!, nihonOrange))
            val lp = LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT )
            iv.layoutParams = lp
            holder.starLL?.addView(iv)
        }
    }

    class ViewHolder( _view: View ): RecyclerView.ViewHolder(_view) {
        var view = _view
        var title: TextView? =null
        var bestMark: TextView? = null
        var starLL: LinearLayout? = null

        init {
            title = view.title
            bestMark = view.best_mark
            starLL = view.star_layout
        }

    }
}
