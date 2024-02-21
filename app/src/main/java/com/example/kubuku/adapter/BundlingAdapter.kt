package com.example.kubuku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kubuku.R
import com.example.kubuku.models.Book
import com.example.kubuku.models.Bundling

class BundlingAdapter(private val bundlingList: ArrayList<Bundling>, layout: Int)
    :RecyclerView.Adapter<BundlingAdapter.BundlingAdapterViewHolder> (){

    private lateinit var mListener: onItemClickListener
    private val layoutType = layout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BundlingAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(layoutType, parent, false)

        return BundlingAdapterViewHolder(itemView, mListener)
    }

    override fun getItemCount(): Int {
        return bundlingList.size
    }

    override fun onBindViewHolder(holder: BundlingAdapterViewHolder, position: Int) {
        val currentItem = bundlingList[position]

        Glide.with(holder.bookImage)
            .load(currentItem.image)
            .centerCrop()
            .into(holder.bookImage)
        if(currentItem.title.length > 6) {
            holder.bookTitle.text = currentItem.title.slice(0..5) + "..."
        }else {
            holder.bookTitle.text = currentItem.title
        }
        if(currentItem.author.length > 10) {
            holder.bookAuthor.text = "by ${currentItem.author.slice(0..10)}..."
        } else{
            holder.bookAuthor.text = "by ${currentItem.author}"
        }
        holder.bookRating.text = currentItem.rating.toString()
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    class BundlingAdapterViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val bookImage: ImageView = itemView.findViewById(R.id.ivBookImage)
        val bookTitle: TextView = itemView.findViewById(R.id.tvBookTitle)
        val bookAuthor: TextView = itemView.findViewById(R.id.tvBookAuthor)
        val bookRating: TextView = itemView.findViewById(R.id.tvBookRating)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}

