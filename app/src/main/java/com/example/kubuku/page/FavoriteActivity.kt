package com.example.kubuku.page

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kubuku.R
import com.example.kubuku.adapter.BookAdapter
import com.example.kubuku.databinding.ActivityFavoriteBinding
import com.example.kubuku.models.Book
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var checkedState: String
    private lateinit var firestore: FirebaseFirestore
    private var bookList: ArrayList<Book> = ArrayList<Book> ()
    private var bundleList: ArrayList<Book> = ArrayList<Book> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()
        checkedState = "books"

        fetchBookData()

        with(binding) {
            btnBack.setOnClickListener {
                finish()
            }
            btnBooks.setOnClickListener {
                val color = ColorStateList.valueOf(ContextCompat.getColor(this@FavoriteActivity, R.color.bg_secondary))
                btnBundlings.backgroundTintList = null
                btnBooks.backgroundTintList = color
                fetchBookData()
            }
            btnBundlings.setOnClickListener {
                val color = ColorStateList.valueOf(ContextCompat.getColor(this@FavoriteActivity, R.color.bg_secondary))
                btnBooks.backgroundTintList = null
                btnBundlings.backgroundTintList = color
                fetchBookData()
            }
        }

    }

    //Function
    private fun fetchBookData() {
        //Remove previous data
        bookList.clear()

        firestore.collection("books")
            .limit(5)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var book = document.toObject(Book::class.java)
                    book.id = document.id
                    bookList.add(book)
                }
                //RecyclerView Adapter
                with(binding) {
                    rvData.layoutManager = GridLayoutManager(this@FavoriteActivity, 3 , LinearLayoutManager.VERTICAL, false)
                    rvData.setHasFixedSize(true)

                    val adapter = BookAdapter(bookList, R.layout.item_book_grid)
                    rvData.adapter = adapter
                    adapter.setOnItemClickListener(object : BookAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FavoriteActivity, DetailBookActivity::class.java)
                            intent.putExtra("EXT_ID", bookList[position].id)
                            startActivity(intent)
                        }
                    })
                }
            }
    }
}