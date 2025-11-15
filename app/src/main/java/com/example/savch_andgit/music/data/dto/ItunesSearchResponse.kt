package com.example.savch_andgit.music.data.dto

import com.squareup.moshi.Json

data class ItunesSearchResponse(
    @Json(name = "resultCount") val resultCount: Int,
    @Json(name = "results") val results: List<ItunesTrackDto>
)

data class ItunesTrackDto(
    @Json(name = "trackId") val trackId: Long?,
    @Json(name = "trackName") val trackName: String?,
    @Json(name = "artistName") val artistName: String?,
    @Json(name = "artistViewUrl") val artistViewUrl: String?,
    @Json(name = "artworkUrl100") val artworkUrl100: String?,
    @Json(name = "previewUrl") val previewUrl: String?,
    @Json(name = "trackViewUrl") val trackViewUrl: String?,
    @Json(name = "collectionId") val collectionId: Long?,
    @Json(name = "collectionName") val collectionName: String?,
    @Json(name = "collectionViewUrl") val collectionViewUrl: String?,
    @Json(name = "releaseDate") val releaseDate: String?,
    @Json(name = "primaryGenreName") val primaryGenreName: String?,
    @Json(name = "country") val country: String?,
    @Json(name = "trackTimeMillis") val trackTimeMillis: Long?,
    @Json(name = "trackNumber") val trackNumber: Int?,
    @Json(name = "discNumber") val discNumber: Int?,
    @Json(name = "trackExplicitness") val trackExplicitness: String?,
    @Json(name = "trackPrice") val trackPrice: Double?,
    @Json(name = "currency") val currency: String?,
    @Json(name = "isStreamable") val isStreamable: Boolean?
)
