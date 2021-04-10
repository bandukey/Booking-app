package com.example.finalproject.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.API.ServiceBuilder
import com.example.finalproject.R
import com.example.finalproject.entity.futsal

class FutsalAdapter(
    private val lstnewsfeed: ArrayList<futsal>,
    private val context: Context
) : RecyclerView.Adapter<FutsalAdapter.NewsfeedViewHolder>() {

    class NewsfeedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView3: ImageView = view.findViewById(R.id.imageView3)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvAddress: TextView = view.findViewById(R.id.tvAddress)
        val tvprice: TextView = view.findViewById(R.id.tvprice)
        val tvPhone: TextView = view.findViewById(R.id.tvPhone)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsfeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.futsallayout, parent, false)
        return NewsfeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsfeedViewHolder, position: Int) {
        val newsfeed = lstnewsfeed[position]
        holder.tvName.text = newsfeed.futsalname
        holder.tvAddress.text = newsfeed.address
        holder.tvPhone.text = newsfeed.phoneno


//        val imagePath = ServiceBuilder.loadImagePath() + newsfeed.address
//        if (!newsfeed.address.equals("no-photo.jpg")) {
//            Glide.with(context)
//                .load(imagePath)
//                .fitCenter()
//                .into(holder.imgnewsfeed)
//        }

    }

    override fun getItemCount(): Int {
        return lstnewsfeed.size
    }

}