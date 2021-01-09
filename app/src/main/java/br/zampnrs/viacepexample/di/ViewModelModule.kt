package br.zampnrs.viacepexample.di

import br.zampnrs.viacepexample.module.home.viewmodel.ContactViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ContactViewModel(get(), get()) }
}