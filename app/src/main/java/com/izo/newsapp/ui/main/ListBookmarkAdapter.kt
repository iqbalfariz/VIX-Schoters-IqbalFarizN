package com.izo.newsapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.izo.newsapp.data.local.NewsEntity
import com.izo.newsapp.databinding.ItemRowNewsBinding

class ListBookmarkAdapter(private val listBookmark: ArrayList<NewsEntity>) :
    RecyclerView.Adapter<ListBookmarkAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: ListBookmarkAdapter.OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: ListBookmarkAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val bookmark = listBookmark[position]
        holder.binding.tvItemTitle.text = bookmark.title
        holder.binding.tvItemPublishedDate.text = bookmark.publishedAt
        Glide.with(holder.itemView.context)
            .load(bookmark.urlToImage)
            .into(holder.binding.imgPoster)

        // jika item berita di klik
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listBookmark[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listBookmark.size

    inner class ListViewHolder(var binding: ItemRowNewsBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(dataBookmark: NewsEntity)
    }
}