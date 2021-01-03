package br.zampnrs.viacepexample.api

import br.zampnrs.viacepexample.model.CepResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepApi {

    @GET("{cep}/json")
    suspend fun loadAddress(
        @Path("cep") cep: String
    ): CepResponse
}