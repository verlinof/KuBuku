package com.example.kubuku.page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.kubuku.R
import com.example.kubuku.databinding.ActivityDetailBookBinding
import com.example.kubuku.models.Book
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class DetailBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBookBinding
    private val firestore = FirebaseFirestore.getInstance()
    private var book: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchData()

        with(binding) {
            btnBack.setOnClickListener {
                finish()
            }
            btnAddToCart.setOnClickListener {

            }
        }
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
}