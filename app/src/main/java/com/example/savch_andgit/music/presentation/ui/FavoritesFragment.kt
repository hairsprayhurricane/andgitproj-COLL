package com.example.savch_andgit.music.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savch_andgit.R
import com.example.savch_andgit.music.presentation.adapter.MusicAdapter
import com.example.savch_andgit.music.presentation.viewmodel.FavoritesViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {
    private val viewModel: FavoritesViewModel by viewModel()

    private lateinit var rvFavorites: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var adapter: MusicAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvFavorites = view.findViewById(R.id.rvFavorites)
        emptyView = view.findViewById(R.id.tvEmpty)
        adapter = MusicAdapter(
            emptyList(),
            emptySet(),
            onToggleFavorite = { track, isFavorite -> if (isFavorite) viewModel.remove(track) },
            onItemClick = { track ->
                val sheet = TrackDetailsBottomSheet.newInstance(
                    title = track.name,
                    artist = track.artist,
                    album = track.albumName,
                    release = track.releaseDate,
                    description = track.description,
                    artwork = track.artworkUrl,
                    genre = track.primaryGenreName,
                    country = track.country,
                    durationMs = track.trackTimeMillis,
                    artistUrl = /*track.artistViewUrl*/"",
                    collectionUrl = /*track.collectionViewUrl*/"",
                    previewUrl = /*track.previewUrl*/""
                )
                sheet.show(parentFragmentManager, "track_details")
            }
        )
        rvFavorites.layoutManager = LinearLayoutManager(requireContext())
        rvFavorites.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favorites.collect { list ->
                    emptyView.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
                    adapter.submit(list, list.map { it.id }.toSet())
                }
            }
        }
    }
}
