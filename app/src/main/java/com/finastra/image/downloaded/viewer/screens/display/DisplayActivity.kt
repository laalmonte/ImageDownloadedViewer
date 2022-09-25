package com.finastra.image.downloaded.viewer.screens.display

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.finastra.image.downloaded.viewer.R
import com.finastra.image.downloaded.viewer.data.TrackDetails
import java.util.*

class DisplayActivity : AppCompatActivity() {
    private lateinit var toolbarApp: Toolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var tvTitle: TextView
    private lateinit var tvGenre: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvDescription: TextView
    private lateinit var ivArtwork: ImageView
    private lateinit var trackDetails: TrackDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setupToolbar()

        tvTitle           = findViewById(R.id.tv_title)
        tvGenre           = findViewById(R.id.tv_genre)
        tvPrice           = findViewById(R.id.tv_price)
        tvDescription     = findViewById(R.id.tv_description)
        ivArtwork         = findViewById(R.id.iv_artwork)

        trackDetails      = intent.getParcelableExtra<TrackDetails>("track")!!
        setupDetails()
    }

    private fun setupToolbar(){
        toolbarTitle      = findViewById(R.id.toolbarTitle)
        toolbarTitle.text = getString(R.string.details)
        toolbarApp        = findViewById(R.id.toolbar_app)
        toolbarApp.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupDetails() {
        trackDetails.trackPrice?.let {
            val currency: String?  = Currency.getInstance(trackDetails!!.currency.toString()).symbol
            val priceString        = "$currency${it}"
            tvPrice.text           = priceString
        }
        trackDetails.trackName?.let { tvTitle.text = it }
        trackDetails.genre?.let { tvGenre.text = it }
        trackDetails.longDescription?.let { tvDescription.text = it }

        trackDetails.artWorkUrl?.let {
            Glide.with(this)
                .load(it)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .placeholder(R.drawable.img_no_image)
                .override(500,500).into(ivArtwork)
        }

    }
}