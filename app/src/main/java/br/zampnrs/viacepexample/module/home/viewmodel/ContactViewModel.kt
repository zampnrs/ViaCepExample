package br.zampnrs.viacepexample.module.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.zampnrs.viacepexample.data.ContactDao
import br.zampnrs.viacepexample.data.ContactEntity
import br.zampnrs.viacepexample.model.CepResponse
import br.zampnrs.viacepexample.usecase.ViaCepUseCase
import kotlinx.coroutines.launch
import java.lang.Exception

class ContactViewModel(
    private val viaCepUseCase: ViaCepUseCase,
    private val contactDao: ContactDao
): ViewModel() {

    sealed class ViewState {
        class Success(val address: CepResponse): ViewState()
        object Error: ViewState()
    }

    val mutableLiveData = MutableLiveData<ViewState>()

    fun loadAddress(cep: String) = viewModelScope.launch {
        try {
            viaCepUseCase.loadAddress(cep).also {
                mutableLiveData.postValue(ViewState.Success(it))
            }
        } catch (e: Exception) {
            mutableLiveData.postValue(ViewState.Error)
        }
    }

    fun getContacts(): List<ContactEntity> {
        return contactDao.getAll()
    }

    fun insertContact(contactEntity: ContactEntity) {
        contactDao.insertAll(contactEntity)
    }

    fun deleteContact(contactEntity: ContactEntity) {
        contactDao.delete(contactEntity)
    }

}