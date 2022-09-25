package com.finastra.image.downloaded.viewer.screens.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.finastra.image.downloaded.viewer.R
import com.finastra.image.downloaded.viewer.adapter.TrackListAdapter
import com.finastra.image.downloaded.viewer.data.Track
import com.finastra.image.downloaded.viewer.data.TrackDetails
import com.finastra.image.downloaded.viewer.screens.display.DisplayActivity

class MainActivity : AppCompatActivity() {
    private lateinit var toolbarApp: Toolbar
    private lateinit var rvTrack: RecyclerView
    private lateinit var swipeMainBody: SwipeRefreshLayout
    private lateinit var constraintBody: ConstraintLayout
    private lateinit var layoutNoData: LinearLayoutCompat
    private lateinit var recyclerAdapter: TrackListAdapter
    private lateinit var viewModel :MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbarApp                 = findViewById(R.id.toolbar_app)
        toolbarApp.navigationIcon  = null

        rvTrack                    = findViewById(R.id.rv_track)
        swipeMainBody              = findViewById(R.id.swipe_main_body)
        layoutNoData               = findViewById(R.id.layout_no_data)
        constraintBody             = findViewById(R.id.constraint_body)
        viewModel                  = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.getTracks()
        setupRecycler()
        setupSwipe()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getLiveDataObserver().observe(this) { allTrack ->
            if (allTrack != null) {
                showData()
                recyclerAdapter.setTrackList(allTrack)
            } else {
                showNoData()
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showNoData(){
        layoutNoData.visibility   = View.VISIBLE
        constraintBody.visibility = View.GONE
    }

    private fun showData(){
        layoutNoData.visibility   = View.GONE
        constraintBody.visibility = View.VISIBLE
    }

    private fun setupSwipe(){
        swipeMainBody.setOnRefreshListener {
            viewModel.trackLiveData.postValue(emptyList())
            viewModel.getTracks()
            Handler(Looper.getMainLooper()).postDelayed({
                swipeMainBody.isRefreshing = false
            }, 2000)
        }
    }

    private fun setupRecycler(){
        rvTrack.layoutManager        = GridLayoutManager(this, 4, RecyclerView.VERTICAL, false)
        recyclerAdapter              = TrackListAdapter(this)
        rvTrack.adapter              = recyclerAdapter
        recyclerAdapter.onItemClick  = { goToDisplay(it) }
    }

    private fun goToDisplay(track: Track){
        val intent       = Intent(this, DisplayActivity::class.java)
        val trackDetails = TrackDetails(track.trackName,
            track.trackPrice.toString(), track.currency, track.primaryGenreName,
            track.longDescription, track.artworkUrl100)

        intent.putExtra("track", trackDetails)
        startActivity(intent)
    }
}