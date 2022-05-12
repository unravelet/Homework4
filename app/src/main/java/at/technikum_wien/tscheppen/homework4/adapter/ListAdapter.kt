package at.technikum_wien.tscheppen.homework4.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import at.technikum_wien.tscheppen.homework4.data.NewsItem
import at.technikum_wien.tscheppen.homework4.R
import com.bumptech.glide.Glide
import java.lang.IllegalArgumentException

class ListAdapter(private val context: Context, items: List<NewsItem> = listOf()) :
    RecyclerView.Adapter<ListAdapter.ItemViewHolder>() {
    companion object {
        const val VIEW_ITEM_TOP = 0
        const val VIEW_ITEM_NORMAL = 1
    }
    var items = items
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var itemClickListener : ((NewsItem)->Unit)? = null

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemTextView = itemView.findViewById<TextView>(R.id.tv_item)
        private val itemTextViewAuthor = itemView.findViewById<TextView>(R.id.tv_item_author)
        private val itemTextViewDate = itemView.findViewById<TextView>(R.id.tv_item_date)
        private val itemViewImage = itemView.findViewById<ImageView>(R.id.imageView)

        init {
            itemView.setOnClickListener { itemClickListener?.invoke(items[absoluteAdapterPosition]) }
        }
        fun bind(index: Int) {
            itemTextView.text = items[index].title
            //add author name and date to recyclerview
            itemTextViewAuthor.text = items[index].author
            itemTextViewDate.text = items[index].publicationDate.toString()

            //external library to load images
            Glide.with(context).load(items[index].imageUrl).into(itemViewImage)

        }
    }

    //usually involves inflating a layout from xml and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val context = parent.context

        val layoutIdForListItem = when(viewType) {
            VIEW_ITEM_TOP -> {
                R.layout.list_item_top
            }
            VIEW_ITEM_NORMAL -> {
                R.layout.list_item
            }
            else -> {
                throw IllegalArgumentException("Invalid view type $viewType")
            }
        }

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(layoutIdForListItem, parent, false)
        return ItemViewHolder(view)



    }

    //involves populating data into the item through holder
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(position)
    }

    //return the total count of items in the list
    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_ITEM_TOP
        } else {
            VIEW_ITEM_NORMAL
        }
    }
}