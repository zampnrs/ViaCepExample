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
        object DeleteSuccess: ViewState()
        object DeleteError: ViewState()
        object UpdateSuccess : ViewState()
        object UpdateError : ViewState()
    }

    val mutableLiveData = MutableLiveData<ViewState>()

    fun loadAddress(cep: String) = viewModelScope.launch {
        try {
            viaCepUseCase.loadAddress(cep).also {
                mutableLiveData.postValue(ViewState.LoadAddressSuccess(it))
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "")
            mutableLiveData.postValue(ViewState.LoadAddressError)
        }
    }

    fun getContacts() = Thread {
        try {
            contactDao.getAll().also {
                contactsFromDb = it
                mutableLiveData.postValue(ViewState.LoadContactSuccess)
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "")
            mutableLiveData.postValue(ViewState.LoadContactError)
        }
    }.start()

    fun insertContact(contactEntity: ContactEntity) = Thread {
        try {
            contactDao.insert(contactEntity)
            mutableLiveData.postValue(ViewState.InsertSuccess)
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "")
            mutableLiveData.postValue(ViewState.InsertError)
        }
    }.start()

    fun updateContact(contactEntity: ContactEntity) = Thread {
        try {
            contactDao.update(contactEntity)
            mutableLiveData.postValue(ViewState.UpdateSuccess)
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "")
            mutableLiveData.postValue(ViewState.UpdateError)
        }
    }.start()

    fun deleteContact(contactEntity: ContactEntity) = Thread {
        try {
            contactDao.delete(contactEntity)
            mutableLiveData.postValue(ViewState.DeleteSuccess)
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "")
            mutableLiveData.postValue(ViewState.DeleteError)
        }
    }.start()
}