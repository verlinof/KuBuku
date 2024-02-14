package com.example.kubuku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kubuku.R
import com.example.kubuku.models.Author

class AuthorAdapter(private val authorList: ArrayList<Author>)
    :RecyclerView.Adapter<AuthorAdapter.AuthorAdapterViewHolder> (){

    private lateinit var mListener: onItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)

        return AuthorAdapterViewHolder(itemView, mListener)
    }

    override fun getItemCount(): Int {
        return authorList.size
    }

    override fun onBindViewHolder(holder: AuthorAdapterViewHolder, position: Int) {
        val currentItem = authorList[position]

        Glide.with(holder.authorImage)
            .load(currentItem.authorImage)
            .centerCrop()
            .into(holder.authorImage)
        if(currentItem.authorName.length > 8) {
            holder.authorName.text = currentItem.authorName.slice(0..7) + "..."
        }else {
            holder.authorName.text = currentItem.authorName
        }
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    class AuthorAdapterViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val authorImage: ImageView = itemView.findViewById(R.id.ivAuthorImage)
        val authorName: TextView = itemView.findViewById(R.id.tvAuthorName)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}

