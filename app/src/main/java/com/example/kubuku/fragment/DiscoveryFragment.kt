package com.example.kubuku.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kubuku.adapter.BookAdapter
import com.example.kubuku.adapter.GenreAdapter
import com.example.kubuku.databinding.FragmentDiscoveryBinding
import com.example.kubuku.models.Book
import com.example.kubuku.models.Genre
import com.example.kubuku.page.DetailBookActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class DiscoveryFragment : Fragment() {
    private lateinit var binding: FragmentDiscoveryBinding
    private lateinit var firestore: FirebaseFirestore
    private var newBookList: ArrayList<Book> = ArrayList<Book>()
    private var genreList: ArrayList<Genre> = ArrayList<Genre> ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiscoveryBinding.inflate(inflater)
        firestore = FirebaseFirestore.getInstance()

        //Function Callback
        fetchNewBookData()
        fetchGenreData()

        // Inflate the layout for this fragment
        return binding.root
    }

    //FUNCTION
    private fun fetchNewBookData() {
        firestore.collection("books")
            .limit(5)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var book = document.toObject(Book::class.java)
                    book.id = document.id
                    newBookList.add(book)
                }
                //RecyclerView Adapter
                with(binding) {
                    rvNewBook.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    rvNewBook.setHasFixedSize(true)

                    val adapter = BookAdapter(newBookList)
                    rvNewBook.adapter = adapter
                    adapter.setOnItemClickListener(object : BookAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(requireContext(), DetailBookActivity::class.java)
                            intent.putExtra("EXT_ID", newBookList[position].id)
                            startActivity(intent)
                        }
                    })
                }
            }
    }

    private fun fetchGenreData() {
        firestore.collection("genre")
            .orderBy("genreTitle", Query.Direction.ASCENDING)
            .limit(8)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var genre = document.toObject(Genre::class.java)
                    genre.id = document.id
                    genreList.add(genre)
                }
                //RecyclerView Adapter
                with(binding) {
                    rvGenre.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    rvGenre.setHasFixedSize(true)

                    val adapter = GenreAdapter(genreList)
                    rvGenre.adapter = adapter
                    adapter.setOnItemClickListener(object : GenreAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {

                        }
                    })
                }
            }
    }

}