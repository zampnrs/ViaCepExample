package br.zampnrs.viacepexample.usecase

import br.zampnrs.viacepexample.api.ViaCepApi
import br.zampnrs.viacepexample.model.CepResponse

class ViaCepUseCaseImpl(
    private val api: ViaCepApi
): ViaCepUseCase {

    override suspend fun loadAddress(cep: String): CepResponse {
        return api.loadAddress(cep)
    }
}