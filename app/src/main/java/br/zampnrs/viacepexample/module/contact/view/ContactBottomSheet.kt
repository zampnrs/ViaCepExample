package br.zampnrs.viacepexample.module.contact.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import bloder.com.blitzcore.enableWhen
import br.zampnrs.viacepexample.R
import br.zampnrs.viacepexample.data.ContactEntity
import br.zampnrs.viacepexample.databinding.BottomsheetContactBinding
import br.zampnrs.viacepexample.model.CepResponse
import br.zampnrs.viacepexample.module.home.viewmodel.ContactViewModel
import br.zampnrs.viacepexample.util.equalsTo
import br.zampnrs.viacepexample.util.showToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class ContactBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetContactBinding
    private val viewModel: ContactViewModel by viewModel()
    private val args: ContactBottomSheetArgs by navArgs()
    private lateinit var address: CepResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeLiveData()
        binding.apply {
            setUpValidations()
            if (args.contact != null) fillContactFields()
            setUpClickListeners()
        }
    }

    private fun subscribeLiveData() {
        viewModel.mutableLiveData.observe(viewLifecycleOwner, Observer {state ->
            when (state) {
                is ContactViewModel.ViewState.LoadAddressSuccess -> address = state.address
                is ContactViewModel.ViewState.LoadAddressError ->
                    showToast(getString(R.string.load_error),true)
                is ContactViewModel.ViewState.InsertSuccess -> {
                    findNavController().popBackStack(R.id.listFragment, false)
                    showToast(getString(R.string.contact_insert_success), true)
                }
                is ContactViewModel.ViewState.InsertError ->
                    showToast(getString(R.string.insert_error),true)
            }
        })
    }

    private fun BottomsheetContactBinding.setUpValidations() {
        buttonOk.enableWhen {
            contactNameTextInput.apply {
                contactNameEditText.isFilled() onValidationSuccess {
                    error = null
                } onValidationError {
                    error = getString(R.string.field_required)
                }
            }

            contactEmailTextInput.apply {
                contactEmailEditText.isEmail() onValidationSuccess {
                    error = null
                } onValidationError {
                    error = getString(R.string.field_required)
                }
            }

            contactPhoneTextInput.apply {
                contactPhoneEditText.isFilled() onValidationSuccess {
                    error = null
                } onValidationError {
                    error = getString(R.string.field_required)
                }
            }
        }
    }

    private fun BottomsheetContactBinding.fillContactFields() {
        with(args.contact!!) {
            contactNameEditText.setText(name)
            contactEmailEditText.setText(email)
            contactPhoneEditText.setText(phone)
        }
    }

    private fun BottomsheetContactBinding.setUpClickListeners() {
        buttonOk.setOnClickListener {
            ContactEntity(
                uuid = UUID.randomUUID().toString(),
                name = contactNameEditText.text.toString(),
                email = contactEmailEditText.text.toString(),
                phone = contactPhoneEditText.text.toString()
            ).let { contactEntity ->
                if (args.contact != null && contactEntity.equalsTo(args.contact!!))
                    findNavController().navigateUp()
                else viewModel.insertContact(contactEntity)
            }
        }
    }
}