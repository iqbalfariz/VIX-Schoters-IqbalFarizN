package com.izo.newsapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.izo.newsapp.ViewModelFactory
import com.izo.newsapp.data.local.NewsEntity
import com.izo.newsapp.data.remote.Articles
import com.izo.newsapp.databinding.FragmentHomeBinding
import com.izo.newsapp.ui.detail.DetailActivity


class HomeFragment : Fragment() {

    private var tabName: String? = null
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        tabName = arguments?.getString(ARG_TAB)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: MainViewModel by viewModels {
            factory
        }


        if (tabName == TAB_NEWS) {
            viewModel.getNews("id").observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is com.izo.newsapp.data.Result.Loading -> {
                            showLoading(true)
                        }
                        is com.izo.newsapp.data.Result.Success -> {
                            showLoading(false)
                            setRvNews(result.data)
                        }
                        is com.izo.newsapp.data.Result.Error -> {
                            showLoading(false)
                            Toast.makeText(
                                context,
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

        } else if (tabName == TAB_BOOKMARK) {

            showLoading(false)
            viewModel.getNewsRoom().observe(viewLifecycleOwner) { dataBookmark ->
                setRvBookmark(dataBookmark)
            }

        }


    }

    private fun setRvBookmark(dataBookmark: List<NewsEntity>?) {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvNews?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding?.rvNews?.addItemDecoration(itemDecoration)

        val listNewsBookmark = ArrayList<NewsEntity>()
        if (dataBookmark != null) {
            listNewsBookmark.addAll(dataBookmark)
        }
        val adapterBookmark = ListBookmarkAdapter(listNewsBookmark)
        binding?.rvNews?.adapter = adapterBookmark

        adapterBookmark.setOnItemClickCallback(object : ListBookmarkAdapter.OnItemClickCallback {
            override fun onItemClicked(dataBookmark: NewsEntity) {
                // mengirim data ke halaman detail
                val intentToDetail = Intent(context, DetailActivity::class.java)
                intentToDetail.putExtra(DetailActivity.BOOKMARK, dataBookmark)
                startActivity(intentToDetail)
            }

        })

    }

    private fun setRvNews(data: List<Articles>) {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvNews?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding?.rvNews?.addItemDecoration(itemDecoration)

        val listNews = ArrayList<Articles>()
        listNews.addAll(data)
        val adapter = ListNewsAdapter(listNews)
        binding?.rvNews?.adapter = adapter

        adapter.setOnItemClickCallback(object : ListNewsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Articles) {
                // mengirim data ke halaman detail
                val intentToDetail = Intent(context, DetailActivity::class.java)
                intentToDetail.putExtra(DetailActivity.DATA, data)
                startActivity(intentToDetail)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    companion object {
        const val ARG_TAB = "tab_name"
        const val TAB_NEWS = "news"
        const val TAB_BOOKMARK = "bookmark"
    }
}