package com.finastra.image.downloaded.viewer.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.finastra.image.downloaded.viewer.R
import com.finastra.image.downloaded.viewer.data.Track


class TrackListAdapter(private val activity:Activity):RecyclerView.Adapter<TrackListAdapter.TrackHolder>() {
    private var trackList: List<Track>? = null
    var onItemClick: ((Track)->Unit)?   = null

    fun setTrackList(trackList: List<Track>?){
        this.trackList = trackList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        return TrackHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_track_image, parent, false))
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        holder.bind(trackList?.get(position)!!, activity)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(trackList!![position])
        }
    }

    override fun getItemCount(): Int {
        return if (trackList == null){ 0
        } else { trackList?.size!! }
    }

    class TrackHolder(view: View): RecyclerView.ViewHolder(view) {
        val artWorkImageView:ImageView = view.findViewById(R.id.artWorkImageView)
        val trackNameTextView:TextView = view.findViewById(R.id.trackNameTextView)

        fun bind(data: Track, activity: Activity){
            trackNameTextView.text = data.trackName
            Glide.with(activity)
                .load(data.artworkUrl100)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .placeholder(R.drawable.img_no_image)
                .override(500,500).into(artWorkImageView)
        }

    }
}