package com.example.savch_andgit.music.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.savch_andgit.R
import com.example.savch_andgit.music.domain.model.Track
import coil.load

class MusicAdapter(
    private var items: List<Track>,
    private var favorites: Set<Long>,
    private val onToggleFavorite: (Track, Boolean) -> Unit,
    private val onItemClick: (Track) -> Unit
) : RecyclerView.Adapter<MusicAdapter.VH>() {

    fun submit(list: List<Track>, favs: Set<Long>) {
        items = list
        favorites = favs
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.bind(item, favorites.contains(item.id), onToggleFavorite, onItemClick)
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tvTitle)
        private val artist: TextView = itemView.findViewById(R.id.tvArtist)
        private val btnFav: Button = itemView.findViewById(R.id.btnFavorite)
        private val artwork: ImageView = itemView.findViewById(R.id.ivArtwork)

        fun bind(track: Track, isFavorite: Boolean, onToggleFavorite: (Track, Boolean) -> Unit, onItemClick: (Track) -> Unit) {
            title.text = track.name
            artist.text = track.artist
            btnFav.text = if (isFavorite) "-" else "+"
            btnFav.setOnClickListener { onToggleFavorite(track, isFavorite) }
            itemView.setOnClickListener { onItemClick(track) }
            val url = track.artworkUrl
            if (url.isNullOrBlank()) {
                artwork.setImageResource(R.drawable.ic_music)
            } else {
                artwork.load(url) {
                    crossfade(true)
                    placeholder(R.drawable.ic_music)
                    error(R.drawable.ic_music)
                }
            }
        }
    }
}
