package saptools.mgavilan.nihongo.fragments.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.main_menu_item.view.*
import saptools.mgavilan.nihongo.R
import saptools.mgavilan.nihongo.data.SchoolYear

class MainMenuRecViewAdapter( _items: List<SchoolYear>, _callback: (Int) -> Unit ): RecyclerView.Adapter<MainMenuRecViewAdapter.ViewHolder>() {

    var items: List<SchoolYear>? = null
    var viewHolder: ViewHolder? = null
    var callback: ((Int) -> Unit)? = null

    init {
        items= _items
        callback = _callback
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.main_menu_item, parent, false)

        viewHolder = ViewHolder(view)

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return items!!.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.title?.text = item?.title
        holder.view?.setOnClickListener {
            callback!!(position)
        }
    }

    class ViewHolder( _view: View ): RecyclerView.ViewHolder(_view) {
        var view = _view
        var title: TextView? =null

        init {
            title = view.title
        }

    }
}
