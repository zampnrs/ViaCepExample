package br.zampnrs.viacepexample.di

import br.zampnrs.viacepexample.api.RetrofitFactory
import br.zampnrs.viacepexample.api.ViaCepApi
import br.zampnrs.viacepexample.usecase.ViaCepUseCase
import br.zampnrs.viacepexample.usecase.ViaCepUseCaseImpl
import org.koin.dsl.module

val networkModule = module {
    single <ViaCepApi>{
        RetrofitFactory.provideNetworkService()
    }
}