package com.izo.newsapp.ui.detail

import android.os.Bundle
import android.view.WindowManager
import android.webkit.WebView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.izo.newsapp.R
import com.izo.newsapp.ViewModelFactory
import com.izo.newsapp.data.local.NewsEntity
import com.izo.newsapp.data.remote.Articles
import com.izo.newsapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    val detailViewModel: DetailViewModel by viewModels {
        factory
    }
    private var isBookmark = false
    private lateinit var webview: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // menerima data dari news fragment
        val data = intent.getParcelableExtra<Articles>(DATA)

        val dataBookmark = intent.getParcelableExtra<NewsEntity>(BOOKMARK)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
        )
        webview = binding.webView


        if (data != null) {
            setUpLayoutNews(data)
        } else {
            setUpLayoutBookmark(dataBookmark)
        }

    }

    private fun setUpLayoutBookmark(dataBookmark: NewsEntity?) {


        setWebView(dataBookmark?.url)
        checkBookmark(dataBookmark?.url)

        val bookmarkNews = dataBookmark?.let {
            NewsEntity(
                null,
                it.sourceName,
                it.author,
                it.title,
                it.description,
                it.url,
                it.urlToImage,
                it.publishedAt,
                it.content
            )
        }

        binding.fabBookmark.setOnClickListener {
            if (bookmarkNews != null) {
                if (isBookmark) {
                    detailViewModel.deleteData(bookmarkNews.url)
                } else {
                    detailViewModel.insertData(bookmarkNews)
                }
            }
        }

    }

    private fun setWebView(url: String?) {

        url?.let { webview.loadUrl(it) }
        val webSettings = webview.settings
        // mengaktifkan javascript
        webSettings.javaScriptEnabled = true
        // untuk load komponen dari web spt bootstrap
        webSettings.domStorageEnabled = true

    }

    private fun setUpLayoutNews(data: Articles?) {


        checkBookmark(data?.url)
        setWebView(data?.url)

        val bookmarkNews = data?.let {
            NewsEntity(
                null,
                it.source.name,
                it.author,
                it.title,
                it.description,
                it.url,
                it.urlToImage,
                it.publishedAt,
                it.content
            )
        }

        binding.fabBookmark.setOnClickListener {
            if (bookmarkNews != null) {
                if (isBookmark) {
                    detailViewModel.deleteData(bookmarkNews.url)
                } else {
                    detailViewModel.insertData(bookmarkNews)
                }
            }
        }


    }

    private fun checkBookmark(url: String?) {
        if (url != null) {
            detailViewModel.isBookmark(url).observe(this) { result ->
                if (result) {
                    isBookmark = true
                    binding.fabBookmark.setImageDrawable(
                        ContextCompat.getDrawable(
                            this, R.drawable.ic_bookmark
                        )
                    )
                } else {
                    isBookmark = false
                    binding.fabBookmark.setImageDrawable(
                        ContextCompat.getDrawable(
                            this, R.drawable.ic_not_bookmark
                        )
                    )
                }
            }
        }
    }

    override fun onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack()
        } else {
            super.onBackPressed()
        }

    }


    companion object {
        const val DATA = "data"
        const val BOOKMARK = "bookmark"
    }
}