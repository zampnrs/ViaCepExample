package br.zampnrs.viacepexample.usecase

import br.zampnrs.viacepexample.model.CepResponse

interface ViaCepUseCase {

    suspend fun loadAddress(cep: String): CepResponse

}