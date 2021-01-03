package br.zampnrs.viacepexample.di

import br.zampnrs.viacepexample.api.RetrofitFactory
import br.zampnrs.viacepexample.api.ViaCepApi
import org.koin.dsl.module
import retrofit2.create

val webServiceModule = module {
    single {
        RetrofitFactory.provideRetrofit(get()).create<ViaCepApi>()
    }
}