package com.example.savch_andgit.music.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.savch_andgit.R
import coil.load

class TrackDetailsBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_track_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val ivArtwork = view.findViewById<ImageView>(R.id.ivArtwork)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvArtist = view.findViewById<TextView>(R.id.tvArtist)
        val tvAlbum = view.findViewById<TextView>(R.id.tvAlbum)
        val tvRelease = view.findViewById<TextView>(R.id.tvRelease)
        val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
        val tvGenre = view.findViewById<TextView>(R.id.tvGenre)
        val tvCountry = view.findViewById<TextView>(R.id.tvCountry)
        val tvLinks = view.findViewById<TextView>(R.id.tvLinks)

        val title = arguments?.getString(ARG_TITLE).orEmpty()
        val artist = arguments?.getString(ARG_ARTIST).orEmpty()
        val album = arguments?.getString(ARG_ALBUM)
        val release = arguments?.getString(ARG_RELEASE)
        val description = arguments?.getString(ARG_DESCRIPTION)
        val artwork = arguments?.getString(ARG_ARTWORK)
        val genre = arguments?.getString(ARG_GENRE)
        val country = arguments?.getString(ARG_COUNTRY)
        val trackUrl = arguments?.getString(ARG_ARTIST_URL)
        val collectionUrl = arguments?.getString(ARG_COLLECTION_URL)
        val previewUrl = arguments?.getString(ARG_PREVIEW_URL)

        tvTitle.text = title
        tvArtist.text = artist
        tvAlbum.text = album ?: ""
        tvAlbum.visibility = if (album.isNullOrBlank()) View.GONE else View.VISIBLE
        tvRelease.text = release?.let { formatDate(it) } ?: ""
        tvRelease.visibility = if (release.isNullOrBlank()) View.GONE else View.VISIBLE
        tvDescription.text = description ?: ""
        tvDescription.visibility = if (description.isNullOrBlank()) View.GONE else View.VISIBLE
        tvGenre.text = genre?.let { "Genre: $it" } ?: ""
        tvGenre.visibility = if (genre.isNullOrBlank()) View.GONE else View.VISIBLE
        tvCountry.text = country?.let { "Country: $it" } ?: ""
        tvCountry.visibility = if (country.isNullOrBlank()) View.GONE else View.VISIBLE
        val links = buildString {
            if (!trackUrl.isNullOrBlank()) append("Artist: ").append(trackUrl).append('\n')
            if (!collectionUrl.isNullOrBlank()) append("Album: ").append(collectionUrl).append('\n')
            if (!previewUrl.isNullOrBlank()) append("Preview: ").append(previewUrl)
        }
        tvLinks.text = links.trim()
        tvLinks.visibility = if (links.isBlank()) View.GONE else View.VISIBLE
        tvLinks.movementMethod = android.text.method.LinkMovementMethod.getInstance()

        if (artwork.isNullOrBlank()) {
            ivArtwork.setImageResource(R.drawable.ic_music)
        } else {
            ivArtwork.load(artwork) {
                crossfade(true)
                placeholder(R.drawable.ic_music)
                error(R.drawable.ic_music)
            }
        }
    }

    private fun formatDate(raw: String): String {
        // Raw example: 2011-07-17T12:00:00Z
        return try {
            val instant = java.time.Instant.parse(raw)
            val dt = java.time.ZonedDateTime.ofInstant(instant, java.time.ZoneId.systemDefault())
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd").format(dt)
        } catch (_: Exception) {
            raw
        }
    }

    companion object {
        private const val ARG_TITLE = "arg_title"
        private const val ARG_ARTIST = "arg_artist"
        private const val ARG_ALBUM = "arg_album"
        private const val ARG_RELEASE = "arg_release"
        private const val ARG_DESCRIPTION = "arg_description"
        private const val ARG_ARTWORK = "arg_artwork"
        private const val ARG_GENRE = "arg_genre"
        private const val ARG_COUNTRY = "arg_country"
        private const val ARG_DURATION = "arg_duration"
        private const val ARG_ARTIST_URL = "arg_artist_url"
        private const val ARG_COLLECTION_URL = "arg_collection_url"
        private const val ARG_PREVIEW_URL = "arg_preview_url"

        fun newInstance(
            title: String,
            artist: String,
            album: String?,
            release: String?,
            description: String?,
            artwork: String?,
            genre: String?,
            country: String?,
            durationMs: Long?,
            artistUrl: String?,
            collectionUrl: String?,
            previewUrl: String?
        ): TrackDetailsBottomSheet {
            val f = TrackDetailsBottomSheet()
            f.arguments = Bundle().apply {
                putString(ARG_TITLE, title)
                putString(ARG_ARTIST, artist)
                putString(ARG_ALBUM, album)
                putString(ARG_RELEASE, release)
                putString(ARG_DESCRIPTION, description)
                putString(ARG_ARTWORK, artwork)
                putString(ARG_GENRE, genre)
                putString(ARG_COUNTRY, country)
                if (durationMs != null) putLong(ARG_DURATION, durationMs)
                putString(ARG_ARTIST_URL, artistUrl)
                putString(ARG_COLLECTION_URL, collectionUrl)
                putString(ARG_PREVIEW_URL, previewUrl)
            }
            return f
        }
    }
}
