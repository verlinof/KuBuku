package com.example.kubuku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kubuku.R
import com.example.kubuku.models.Genre

class GenreAdapter(private val genreList: ArrayList<Genre>)
    :RecyclerView.Adapter<GenreAdapter.GenreAdapterViewHolder> (){

    private lateinit var mListener: onItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)

        return GenreAdapterViewHolder(itemView, mListener)
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    override fun onBindViewHolder(holder: GenreAdapterViewHolder, position: Int) {
        val currentItem = genreList[position]

        Glide.with(holder.genreImage)
            .load(currentItem.genreImage)
            .centerCrop()
            .into(holder.genreImage)
        if(currentItem.genreTitle.length > 8) {
            holder.genreTitle.text = currentItem.genreTitle.slice(0..7) + "..."
        }else {
            holder.genreTitle.text = currentItem.genreTitle
        }
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    class GenreAdapterViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val genreImage: ImageView = itemView.findViewById(R.id.ivGenreImage)
        val genreTitle: TextView = itemView.findViewById(R.id.tvGenreTitle)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}

