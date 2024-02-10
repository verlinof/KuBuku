package com.example.kubuku.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kubuku.databinding.FragmentHomeBinding
import com.example.kubuku.helper.HelperSharedPreferences
import com.example.kubuku.models.Book
import com.example.kubuku.adapter.BookAdapter
import com.example.kubuku.adapter.GenreAdapter
import com.example.kubuku.models.Genre
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var helperSharedPreferences: HelperSharedPreferences
    //Firebase
    private val firestore = FirebaseFirestore.getInstance()
    private var bookList: ArrayList<Book> = ArrayList<Book> ()
    private var genreList: ArrayList<Genre> = ArrayList<Genre> ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        helperSharedPreferences = HelperSharedPreferences(requireContext())

        //Function Call
        fetchBookData()
        fetchGenreData()


        with(binding) {
            //Username
            val username = helperSharedPreferences.getUsername()
            tvUsername.text = "Halo, $username"
        }

        return binding.root
    }

    //Function
    private fun fetchBookData() {
        firestore.collection("books")
            .orderBy("totalOrder", Query.Direction.DESCENDING)
            .limit(5)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val book = document.toObject(Book::class.java)
                    bookList.add(book)
                }
                //RecyclerView Adapter
                with(binding) {
                    rvFavorite.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    rvFavorite.setHasFixedSize(true)

                    val adapter = BookAdapter(bookList)
                    rvFavorite.adapter = adapter
                    adapter.setOnItemClickListener(object : BookAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {

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
                    val genre = document.toObject(Genre::class.java)
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