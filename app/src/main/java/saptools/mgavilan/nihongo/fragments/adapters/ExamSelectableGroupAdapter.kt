package saptools.mgavilan.nihongo.fragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.exam_selectable_group.view.*
import saptools.mgavilan.nihongo.R
import saptools.mgavilan.nihongo.data.SchoolYear

class ExamSelectableGroupAdapter(_items: List<SchoolYear>, context: Context ): RecyclerView.Adapter<ExamSelectableGroupAdapter.ViewHolder>() {

    var schoolYears: List<SchoolYear>? = null
    var viewHolder: ViewHolder? = null
    var mContext: Context? = null

    init {
        schoolYears= _items
        mContext = context
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.exam_selectable_group, parent, false)

        viewHolder = ViewHolder(view)

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return schoolYears!!.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = schoolYears?.get(position)
        holder.title?.text = item?.title
        holder.dropIcon?.visibility = View.VISIBLE

        // Creating list to contain the units for each Schoolyear
        val unitList = LinearLayout(mContext!!)
        val listLayoutParams = LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT)
        unitList.orientation = LinearLayout.VERTICAL
        listLayoutParams.setMargins(100, 40,0,0)
        unitList.layoutParams = listLayoutParams
        holder.view.selectable_group.addView(unitList)
        unitList.visibility = View.GONE

        // Creating Unit selectables for each schoolyear
        for ( un in schoolYears!![position].units.indices ) {
            val newUnit = LayoutInflater.from(mContext).inflate(R.layout.exam_selectable_group, null, false)
            newUnit.title.text = schoolYears!![position].units[un].title
            newUnit.check.isChecked = false
            newUnit.check.setOnCheckedChangeListener { _, b ->
                schoolYears!![position].units[un].isSelected = b
            }
            newUnit.setOnClickListener {
                newUnit.check.isChecked = !newUnit.check.isChecked
            }
            unitList.addView(newUnit)
        }

        // We set the view in order to display the list of units when clicked
        holder.view.setOnClickListener {
            if ( unitList.visibility == View.VISIBLE ) {
                unitList.visibility = View.GONE
                holder.dropIcon?.setBackgroundResource(R.drawable.ic_drop_down)
            } else {
                unitList.visibility = View.VISIBLE
                holder.dropIcon?.setBackgroundResource(R.drawable.ic_drop_up)
            }
        }

        // When clicking on the area checkbox, all its children must be checked / unchecked
        holder.checkbox?.setOnCheckedChangeListener { _, b ->
            for ( i in 0 until unitList.childCount ) {
                unitList[i].check.isChecked = b
                item?.units!![i].isSelected = b
            }
        }

    }

    class ViewHolder( _view: View ): RecyclerView.ViewHolder(_view) {
        var view = _view
        var title: TextView? =null
        var checkbox: CheckBox? = null
        var dropIcon: ImageView? = null

        init {
            title = view.title
            checkbox = view.check
            dropIcon = view.drop_icon
        }

    }
}
