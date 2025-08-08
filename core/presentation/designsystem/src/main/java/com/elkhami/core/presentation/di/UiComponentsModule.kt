package com.elkhami.core.presentation.di

import coil.ImageLoader
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val uiComponentsModule = module {
    single {
        ImageLoader.Builder(androidContext())
            .crossfade(true)
            .build()
    }
}
