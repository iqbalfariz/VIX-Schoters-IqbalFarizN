package com.izo.newsapp.ui.profil

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.izo.newsapp.R
import com.izo.newsapp.databinding.ActivityProfilBinding

class ProfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLayout()
    }

    private fun setupLayout() {

        // mengambil gambar profil
        Glide.with(this)
            .load(R.drawable.profil_iqbal)
            .circleCrop()
            .into(binding.imageView)

    }
}