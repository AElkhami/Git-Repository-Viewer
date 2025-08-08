package com.elkhami.repoviewer.presentation.di

import com.elkhami.repoviewer.presentation.repolist.RepoListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val repoViewerViewModelModule = module {
    viewModelOf(::RepoListViewModel)
}