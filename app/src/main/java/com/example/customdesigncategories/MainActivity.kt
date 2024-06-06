package com.example.customdesigncategories

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

// hello from Github Testing

    // Experimental Branch  c

    // Experimental Branch  c
    // Experimental Branch  c

    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var itemsRecyclerView: RecyclerView

    private val categories = listOf(
        Category("Bags", List(15) { Item("Bag ${it + 1}", R.drawable.bag) }, R.drawable.rocco),
        Category("T-Shirts", List(15) { Item("T-Shirt ${it + 1}", R.drawable.tshirt) }, R.drawable.rocco),
        Category("Pants", List(15) { Item("Pant ${it + 1}", R.drawable.pant) }, R.drawable.rocco),
        Category("Cosmetics", List(15) { Item("Cosmetic ${it + 1}", R.drawable.cc) }, R.drawable.rocco)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView)
        itemsRecyclerView = findViewById(R.id.itemsRecyclerView)

        setupCategoriesRecyclerView()
        setupItemsRecyclerView()

        syncScroll()
    }

    private fun setupCategoriesRecyclerView() {
        categoriesRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CategoriesAdapter(categories) { position ->
            scrollToCategory(position)
        }
        categoriesRecyclerView.adapter = adapter
    }

    private fun setupItemsRecyclerView() {
        itemsRecyclerView.layoutManager = GridLayoutManager(this, 3)
        val adapter = ItemsAdapter(categories)
        adapter.setupGridLayout(itemsRecyclerView, 3)
        itemsRecyclerView.adapter = adapter
    }

    private fun scrollToCategory(position: Int) {
        val layoutManager = itemsRecyclerView.layoutManager as LinearLayoutManager
        var itemPosition = 0

        for (i in 0 until position) {
            itemPosition += categories[i].items.size + 1 // Add 1 for the header
        }

        layoutManager.scrollToPositionWithOffset(itemPosition, 0)
    }

    private fun syncScroll() {
        itemsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItemPosition != RecyclerView.NO_POSITION) {
                    val firstVisibleView = layoutManager.findViewByPosition(firstVisibleItemPosition)
                    val top = firstVisibleView?.top ?: 0

                    if (top == 0) {
                        var cumulativeCount = 0
                        for (i in categories.indices) {
                            if (firstVisibleItemPosition < cumulativeCount + categories[i].items.size + 1) {
                                categoriesRecyclerView.scrollToPosition(i)
                                break
                            }
                            cumulativeCount += categories[i].items.size + 1
                        }
                    }
                }
            }
        })
    }
}
