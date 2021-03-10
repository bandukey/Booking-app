package com.example.finalproject.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.API.ServiceBuilder
import com.example.finalproject.R
import com.example.finalproject.entity.futsal

class FutsalAdapter(
    private val lstnewsfeed: ArrayList<futsal>,
    private val context: Context
) : RecyclerView.Adapter<FutsalAdapter.NewsfeedViewHolder>() {

    class NewsfeedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgnewsfeed: ImageView = view.findViewById(R.id.newsfeed_image)
        val tv_caption: TextView = view.findViewById(R.id.newsfeed_caption)
        val tv_blogname: TextView = view.findViewById(R.id.blog_title)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsfeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.newsfeedcustomitem, parent, false)
        return NewsfeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsfeedViewHolder, position: Int) {
        val newsfeed = lstnewsfeed[position]
        holder.tv_blogname.text = newsfeed.title
        holder.tv_caption.text = newsfeed.caption


        val imagePath = ServiceBuilder.loadImagePath() + newsfeed.photo
        if (!newsfeed.photo.equals("no-photo.jpg")) {
            Glide.with(context)
                .load(imagePath)
                .fitCenter()
                .into(holder.imgnewsfeed)
        }

    }

    override fun getItemCount(): Int {
        return lstnewsfeed.size
    }
}