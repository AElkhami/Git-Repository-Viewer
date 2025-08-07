package com.elkhami.core.database.di

import androidx.room.Room
import com.elkhami.core.database.RepoViewerDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            RepoViewerDatabase::class.java,
            "repo_viewer.db"
        ).build()
    }
    single { get<RepoViewerDatabase>().gitRepoDao }
}