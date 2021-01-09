package br.zampnrs.viacepexample.module.home.viewmodel

import android.util.Log
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

    private val TAG = "DB"
    var contactsFromDb = emptyList<ContactEntity>()

    sealed class ViewState {
        class LoadAddressSuccess(val address: CepResponse): ViewState()
        object LoadAddressError: ViewState()
        object LoadContactSuccess : ViewState()
        object LoadContactError: ViewState()
        object InsertSuccess: ViewState()
        object InsertError: ViewState()
    }

    val mutableLiveData = MutableLiveData<ViewState>()

    fun loadAddress(cep: String) = viewModelScope.launch {
        try {
            viaCepUseCase.loadAddress(cep).also {
                mutableLiveData.postValue(ViewState.LoadAddressSuccess(it))
            }
        } catch (e: Exception) {
            mutableLiveData.postValue(ViewState.LoadAddressError)
        }
    }

    fun getContacts() = viewModelScope.launch {
        try {
            Thread {
                contactDao.getAll().also {
                    contactsFromDb = it
                    mutableLiveData.postValue(ViewState.LoadContactSuccess)
                }
            }.start()
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "")
            mutableLiveData.postValue(ViewState.LoadContactError)
        }
    }

    fun insertContact(contactEntity: ContactEntity) {
        try {
            Thread {
                contactDao.insertAll(contactEntity)
                mutableLiveData.postValue(ViewState.InsertSuccess)
            }.start()
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "")
            mutableLiveData.postValue(ViewState.InsertError)
        }
    }

    fun deleteContact(contactEntity: ContactEntity) {
        contactDao.delete(contactEntity)
    }
}