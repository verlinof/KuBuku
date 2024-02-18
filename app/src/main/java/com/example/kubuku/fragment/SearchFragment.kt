package com.example.kubuku.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kubuku.R
import com.example.kubuku.adapter.BookAdapter
import com.example.kubuku.databinding.FragmentSearchBinding
import com.example.kubuku.models.Book
import com.example.kubuku.page.DetailBookActivity
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var handler: Handler

    //Firebase
    private lateinit var firestore: FirebaseFirestore

    //Data
    private var bookList: ArrayList<Book> = ArrayList<Book> ()
    private lateinit var adapter: BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)

        handler = Handler()
        firestore = FirebaseFirestore.getInstance()
        adapter = BookAdapter(bookList, R.layout.item_book_grid)

        //Function Callback
        fetchData()

        with(binding) {
            //Auto Focus on Edittext
            etSearch.requestFocus()
            //Search Logic
            etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(query: Editable?) {
                    //Capitalize Word for Query
                    fun String.capitalizeEveryWord(): String {
                        return this.split("\\s+".toRegex()) // Split the string by whitespace
                            .joinToString(" ") { it.capitalize() } // Capitalize each word and join them back
                    }

                    handler.removeCallbacksAndMessages(null)
                    handler.postDelayed({
                        // Fetch data here
                        val capitalizeQuery = query.toString().capitalizeEveryWord()
                        search(capitalizeQuery)
                        if(query.toString().isNotEmpty()) {
                            tvSearchResult.text = "Menampilkan hasil \"${capitalizeQuery}\""
                        } else {
                            tvSearchResult.text = "Menampilkan hasil"
                        }

                    }, 500)
                }

            })

            btnBack.setOnClickListener {
                //Fragment Transaction
                val fragmentManager = requireActivity().supportFragmentManager

                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frameLayout, DiscoveryFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun search(query: String?) {
        //Clear previous Data
        bookList.clear()

        if(query != null) {
            firestore.collection("books")
                .where(Filter.or(
                    Filter.equalTo("title", query),
                    Filter.equalTo("author", query)
                ))
                .addSnapshotListener { documents, error ->
                    if(error != null) {
                        // Handle any errors
                        Toast.makeText(requireContext(), "Error Happened", Toast.LENGTH_SHORT).show()
                        return@addSnapshotListener
                    }

                    if (documents != null) {
                        for (document in documents) {
                            var book = document.toObject(Book::class.java)
                            book.id = document.id
                            bookList.add(book)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

        }

    }

    private fun fetchData() {
        //RecyclerView Adapter
        with(binding) {
            rvData.layoutManager = GridLayoutManager(requireContext(), 3 , LinearLayoutManager.VERTICAL, false)
            rvData.setHasFixedSize(true)

//            val adapter = BookAdapter(bookList, R.layout.item_book_grid)
            rvData.adapter = adapter
            adapter.setOnItemClickListener(object : BookAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(requireContext(), DetailBookActivity::class.java)
                    intent.putExtra("EXT_ID", bookList[position].id)
                    startActivity(intent)
                }
            })
        }
    }

}