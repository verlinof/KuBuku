package com.example.kubuku.page

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kubuku.R
import com.example.kubuku.adapter.BookAdapter
import com.example.kubuku.databinding.ActivityDetailBookBinding
import com.example.kubuku.models.Book
import com.example.kubuku.models.Cart
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class DetailBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBookBinding
    private val firestore = FirebaseFirestore.getInstance()
    private var book: Book? = null
    private lateinit var auth: FirebaseAuth
    //RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private var bookList: ArrayList<Book> = ArrayList<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        bookAdapter = BookAdapter(bookList, R.layout.item_book)

        //Function Callback
        fetchData()
        updateUi()

        with(binding) {
            btnBack.setOnClickListener {
                finish()
            }
            btnAddToCart.setOnClickListener {
                addToCart()
            }
        }
    }

    private fun addToCart() {
//        val cart = Cart(
//            idUser = auth.currentUser!!.uid,
//            idProducts =
//        )

        firestore.collection("carts")
            .document()
    }

    //FUNCTION
    private fun fetchData() {
        //Get ID from Home
        val bundle: Bundle? = intent.extras
        val id = bundle?.getString("EXT_ID")

        firestore.collection("books")
            .document(id!!)
            .get()
            .addOnSuccessListener { document ->
                if(document.exists()) {
                    book = document.toObject(Book::class.java)
                    book!!.id = document.id

                    //Recomendation Book
                    fetchAuthorBooks()

                    with(binding) {
                        // Rating Section
                        val bookRating = book!!.rating.toInt()
                        val starImageViews = listOf(ivStar1, ivStar2, ivStar3, ivStar4, ivStar5)

                        for (i in 0 until bookRating) {
                            starImageViews[i].setImageResource(R.drawable.ic_star_yellow)
                        }

                        //Main Section
                        Glide.with(ivBookImage)
                            .load(book!!.image)
                            .centerInside()
                            .into(ivBookImage)
                        tvBookTitle.text = book!!.title
                        tvAuthor.text = book!!.author
                        tvBookRating.text = book!!.rating.toString()
                        tvPriceData.text = "Rp." + book!!.priceDays.toString() + "/hari"
                        tvDescription.text = book!!.description

                        //Description Section
                        tvDescBookAuthorData.text = book!!.author
                        tvDescBookTitleData.text = book!!.title
                        tvDescTotalPageData.text = "400 halaman"
                    }
                }
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchAuthorBooks() {
        val author = book?.author

        binding.tvOtherBooksTitle.text = "Buku ${author} Lainnya"

        firestore.collection("books")
            .whereEqualTo("author", author)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var book = document.toObject(Book::class.java)
                    book.id = document.id
                    bookList.add(book)
                }

                bookAdapter.notifyDataSetChanged()
            }
    }

    private fun updateUi() {
        //Other Books Adapter
        with(binding) {
            rvOtherBooks.layoutManager = LinearLayoutManager(this@DetailBookActivity, LinearLayoutManager.HORIZONTAL, false)
            rvOtherBooks.setHasFixedSize(true)

            rvOtherBooks.adapter = bookAdapter
            bookAdapter.setOnItemClickListener(object : BookAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@DetailBookActivity, DetailBookActivity::class.java)
                    intent.putExtra("EXT_ID", bookList[position].id)
                    startActivity(intent)
                }
            })
        }

        //Bundlings Adapter

    }
}