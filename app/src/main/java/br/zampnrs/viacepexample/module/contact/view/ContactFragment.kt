package br.zampnrs.viacepexample.module.contact.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import bloder.com.blitzcore.enableWhen
import br.zampnrs.viacepexample.R
import br.zampnrs.viacepexample.data.ContactEntity
import br.zampnrs.viacepexample.databinding.FragmentContactBinding
import br.zampnrs.viacepexample.module.home.viewmodel.ContactViewModel
import br.zampnrs.viacepexample.util.equalsTo
import br.zampnrs.viacepexample.util.showToast
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class ContactFragment : Fragment() {

    private lateinit var binding: FragmentContactBinding
    private val viewModel: ContactViewModel by viewModel()
    private val args: ContactFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeLiveData()
        binding.apply {
            contactCepEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (p0?.length == 8) {
                        binding.progressBar.visibility = View.VISIBLE
                        viewModel.loadAddress(p0.toString())
                    }
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
            setUpValidations()
            if (args.contact != null) fillContactFields()
            setUpClickListeners()
        }
    }

    private fun subscribeLiveData() {
        viewModel.mutableLiveData.observe(viewLifecycleOwner, Observer {state ->
            when (state) {
                is ContactViewModel.ViewState.LoadAddressSuccess -> {
                    with(state.address) {
                        binding.apply {
                            progressBar.visibility = View.GONE
                            contactStreetEditText.setText(logradouro)
                            contactCityEditText.setText(localidade)
                            contactUfEditText.setText(uf)
                        }
                    }
                }
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

    private fun FragmentContactBinding.setUpValidations() {
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

            contactCepTextInput.apply {
                contactCepEditText.isFilled() onValidationSuccess {
                    error = null
                } onValidationError {
                    error = getString(R.string.field_required)
                }
            }

            contactStreetTextInput.apply {
                contactStreetEditText.isFilled() onValidationSuccess {
                    error = null
                } onValidationError {
                    error = getString(R.string.field_required)
                }
            }

            contactCityTextInput.apply {
                contactCityEditText.isFilled() onValidationSuccess {
                    error = null
                } onValidationError {
                    error = getString(R.string.field_required)
                }
            }

            contactUfTextInput.apply {
                contactUfEditText.isFilled() onValidationSuccess {
                    error = null
                } onValidationError {
                    error = getString(R.string.field_required)
                }
            }
        }
    }

    private fun FragmentContactBinding.fillContactFields() {
        with(args.contact!!) {
            contactNameEditText.setText(name)
            contactEmailEditText.setText(email)
            contactPhoneEditText.setText(phone)
        }
    }

    private fun FragmentContactBinding.setUpClickListeners() {
        buttonCancel.setOnClickListener {
            findNavController().navigateUp()
        }
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