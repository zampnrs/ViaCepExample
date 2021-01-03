package br.zampnrs.viacepexample.di

import br.zampnrs.viacepexample.api.RetrofitFactory
import org.koin.dsl.module

val networkModule = module {
    single { RetrofitFactory.provideOkHttp3() }
}