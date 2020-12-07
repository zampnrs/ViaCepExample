package br.zampnrs.viacepexample.di

import br.zampnrs.viacepexample.module.home.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
}