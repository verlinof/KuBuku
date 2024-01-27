package com.example.kubuku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.kubuku.R

class SliderAdapter(
    private val imageList: ArrayList<Int>,
    private val titleList: ArrayList<String>,
    private val descriptionList: ArrayList<String>,
    private val viewPager: ViewPager2
): RecyclerView.Adapter<SliderAdapter.SliderViewHolder> () {

    class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ivSliderImage)
        val sliderTitle: TextView = itemView.findViewById(R.id.tvSliderTitle)
        val sliderDescription: TextView = itemView.findViewById(R.id.tvSliderDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_slider_onboarding, parent, false)

        return SliderViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.imageView.setImageResource(imageList[position])
        holder.sliderTitle.text = titleList[position]
        holder.sliderDescription.text = descriptionList[position]
    }
}