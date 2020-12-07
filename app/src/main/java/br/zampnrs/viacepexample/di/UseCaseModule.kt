package br.zampnrs.viacepexample.di

import br.zampnrs.viacepexample.usecase.ViaCepUseCase
import br.zampnrs.viacepexample.usecase.ViaCepUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<ViaCepUseCase> {
        ViaCepUseCaseImpl(
            get()
        )
    }
}