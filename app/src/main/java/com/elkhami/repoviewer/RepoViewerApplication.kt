package com.elkhami.repoviewer

import android.app.Application
import com.elkhami.core.data.di.coreDataModule
import com.elkhami.core.database.di.databaseModule
import com.elkhami.core.presentation.di.uiComponentsModule
import com.elkhami.repoviewer.data.di.repoViewerDataModule
import com.elkhami.repoviewer.presentation.di.repoViewerViewModelModule
import timber.log.Timber
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RepoViewerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@RepoViewerApplication)
            modules(
                uiComponentsModule,
                coreDataModule,
                repoViewerViewModelModule,
                repoViewerDataModule,
                databaseModule
            )
        }
    }
}