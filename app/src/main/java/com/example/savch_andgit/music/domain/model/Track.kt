package com.example.savch_andgit.music.domain.model

data class Track(
    val id: Long,
    val name: String,
    val artist: String,
    val artworkUrl: String?,
    val previewUrl: String?,
    val trackUrl: String?,
    val artistViewUrl: String? = null,
    val albumName: String? = null,
    val releaseDate: String? = null,
    val description: String? = null,
    val collectionId: Long? = null,
    val collectionViewUrl: String? = null,
    val primaryGenreName: String? = null,
    val country: String? = null,
    val trackTimeMillis: Long? = null
)
