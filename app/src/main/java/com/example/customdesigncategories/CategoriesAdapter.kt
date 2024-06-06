package com.example.customdesigncategories

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoriesAdapter(
    private val categories: List<Category>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    private var selectedPosition = -1

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.category_name)
        private val imageView: ImageView = itemView.findViewById(R.id.category_image)

        fun bind(category: Category, position: Int) {
            textView.text = category.name
            imageView.setImageResource(category.imageResId)
            itemView.setBackgroundColor(if (position == selectedPosition) Color.LTGRAY else Color.WHITE)
            itemView.setOnClickListener {
                selectedPosition = position
                notifyDataSetChanged()
                onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position], position)
    }

    override fun getItemCount() = categories.size
}
