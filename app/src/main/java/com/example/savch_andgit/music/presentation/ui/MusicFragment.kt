package com.example.savch_andgit.music.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savch_andgit.R
import com.example.savch_andgit.music.presentation.adapter.MusicAdapter
import com.example.savch_andgit.music.presentation.viewmodel.MusicViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.core.widget.addTextChangedListener

class MusicFragment : Fragment() {
    private val viewModel: MusicViewModel by viewModel()

    private lateinit var etQuery: EditText
    private lateinit var rvTracks: RecyclerView
    private lateinit var adapter: MusicAdapter
    private lateinit var tvLoading: TextView
    private var suppressTextChange = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        etQuery = view.findViewById(R.id.etQuery)
        rvTracks = view.findViewById(R.id.rvTracks)
        tvLoading = view.findViewById(R.id.tvLoading)

        adapter = MusicAdapter(
            emptyList(),
            emptySet(),
            onToggleFavorite = { track, isFavorite -> viewModel.toggleFavorite(track, isFavorite) },
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
                    artistUrl = track.artistViewUrl,
                    collectionUrl = track.collectionViewUrl,
                    previewUrl = track.previewUrl
                )
                sheet.show(parentFragmentManager, "track_details")
            }
        )
        rvTracks.layoutManager = LinearLayoutManager(requireContext())
        rvTracks.adapter = adapter

        etQuery.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.onQueryChanged(etQuery.text?.toString().orEmpty())
                true
            } else false
        }

        etQuery.addTextChangedListener { text ->
            if (suppressTextChange) return@addTextChangedListener
            viewModel.onQueryChanged(text?.toString().orEmpty())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (etQuery.text?.toString() != state.query) {
                        suppressTextChange = true
                        etQuery.setText(state.query)
                        etQuery.setSelection(state.query.length)
                        suppressTextChange = false
                    }
                    adapter.submit(state.results, state.favoritesIds)
                    tvLoading.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                }
            }
        }
    }
}
