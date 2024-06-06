package com.example.customdesigncategories

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(private val categories: List<Category>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerText: TextView = itemView.findViewById(R.id.header_text)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.item_image)
        val textView: TextView = itemView.findViewById(R.id.item_name)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isHeader(position)) TYPE_HEADER else TYPE_ITEM
    }

    private fun isHeader(position: Int): Boolean {
        var count = 0
        for (category in categories) {
            if (position == count) {
                return true
            }
            count += category.items.size + 1
        }
        return false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_header, parent, false)
            HeaderViewHolder(itemView)
        } else {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list, parent, false)
            ItemViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.headerText.text = getHeaderForPosition(position)
        } else if (holder is ItemViewHolder) {
            val item = getItemForPosition(position)
            holder.textView.text = item.name
            holder.imageView.setImageResource(item.imageResId)
        }
    }

    override fun getItemCount(): Int {
        var count = 0
        for (category in categories) {
            count += category.items.size + 1
        }
        return count
    }

    private fun getHeaderForPosition(position: Int): String {
        var count = 0
        for (category in categories) {
            if (position == count) {
                return category.name
            }
            count += category.items.size + 1
        }
        return ""
    }

    private fun getItemForPosition(position: Int): Item {
        var count = 0
        for (category in categories) {
            if (position > count && position <= count + category.items.size) {
                return category.items[position - count - 1]
            }
            count += category.items.size + 1
        }
        throw IndexOutOfBoundsException("Invalid position")
    }

    fun setupGridLayout(recyclerView: RecyclerView, spanCount: Int) {
        val layoutManager = GridLayoutManager(recyclerView.context, spanCount)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (isHeader(position)) spanCount else 1
            }
        }
        recyclerView.layoutManager = layoutManager
    }
}
