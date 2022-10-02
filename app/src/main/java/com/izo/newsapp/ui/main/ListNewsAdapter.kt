package com.izo.newsapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.izo.newsapp.data.remote.Articles
import com.izo.newsapp.databinding.ItemRowNewsBinding

class ListNewsAdapter(private val listNews: ArrayList<Articles>) :
    RecyclerView.Adapter<ListNewsAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val news = listNews[position]
        holder.binding.tvItemTitle.text = news.title
        holder.binding.tvItemPublishedDate.text = news.publishedAt
        Glide.with(holder.itemView.context)
            .load(news.urlToImage)
            .into(holder.binding.imgPoster)

        // jika item berita di klik
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listNews[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listNews.size

    class ListViewHolder(var binding: ItemRowNewsBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: Articles)
    }
}