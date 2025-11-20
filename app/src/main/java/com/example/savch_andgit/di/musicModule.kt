package com.example.savch_andgit.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import androidx.room.Room
import com.example.savch_andgit.music.data.service.ItunesApi
import com.example.savch_andgit.music.data.repository.MusicRepositoryImpl
import com.example.savch_andgit.music.domain.repository.MusicRepository
import com.example.savch_andgit.music.domain.usecase.GetFavoriteTracksUseCase
import com.example.savch_andgit.music.domain.usecase.RemoveFavoriteTrackUseCase
import com.example.savch_andgit.music.domain.usecase.SaveFavoriteTrackUseCase
import com.example.savch_andgit.music.domain.usecase.SearchTracksUseCase
import com.example.savch_andgit.music.presentation.viewmodel.MusicViewModel
import com.example.savch_andgit.music.presentation.viewmodel.FavoritesViewModel
import com.example.savch_andgit.music.data.local.MusicDatabase

val musicModule = module {
    single { ItunesApi() }
    // Database for favorites
    single { Room.databaseBuilder(androidContext(), MusicDatabase::class.java, "music.db").fallbackToDestructiveMigration().build() }
    single { get<MusicDatabase>().trackDao() }
    single<MusicRepository> { MusicRepositoryImpl(get(), get()) }
    single { SearchTracksUseCase(get()) }
    single { SaveFavoriteTrackUseCase(get()) }
    single { GetFavoriteTracksUseCase(get()) }
    single { RemoveFavoriteTrackUseCase(get()) }
    viewModel { MusicViewModel(get(), get(), get(), get()) }
    viewModel { FavoritesViewModel(get(), get()) }
}

val appModule = listOf(musicModule)
