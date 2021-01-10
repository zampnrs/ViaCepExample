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
                    if (p0?.length == 8 && p0.toString() != args.contact?.cep) {
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
                is ContactViewModel.ViewState.LoadAddressSuccess ->
                    with(state.address) {
                        binding.apply {
                            progressBar.visibility = View.GONE
                            contactStreetEditText.setText(logradouro)
                            contactCityEditText.setText(localidade)
                            contactUfEditText.setText(uf)
                        }
                    }

                is ContactViewModel.ViewState.LoadAddressError ->
                    showToast(getString(R.string.load_error))

                is ContactViewModel.ViewState.InsertSuccess -> {
                    findNavController().popBackStack(R.id.listFragment, false)
                    showToast(getString(R.string.contact_insert_success))
                }

                is ContactViewModel.ViewState.InsertError ->
                    showToast(getString(R.string.insert_error))

                is ContactViewModel.ViewState.UpdateSuccess -> {
                    findNavController().popBackStack(R.id.listFragment, false)
                    showToast(getString(R.string.contact_update_success))
                }

                is ContactViewModel.ViewState.UpdateError ->
                    showToast(getString(R.string.contact_update_error))
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
            contactCepEditText.setText(cep)
            contactStreetEditText.setText(street)
            contactStreetNumberEditText.setText(number)
            contactComplementEditText.setText(complement)
            contactCityEditText.setText(city)
            contactUfEditText.setText(uf)
        }
    }

    private fun FragmentContactBinding.setUpClickListeners() {
        buttonCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        buttonOk.setOnClickListener {
            ContactEntity(
                uuid = if (args.contact == null) UUID.randomUUID().toString() else args.contact!!.uuid,
                name = contactNameEditText.text.toString(),
                email = contactEmailEditText.text.toString(),
                phone = contactPhoneEditText.text.toString(),
                cep = contactCepEditText.text.toString(),
                street = contactStreetEditText.text.toString(),
                number = contactStreetNumberEditText.text.toString(),
                complement = contactComplementEditText.text.toString(),
                city = contactCityEditText.text.toString(),
                uf = contactUfEditText.text.toString()
            ).let { contactEntity ->
                with(viewModel) {
                    if (args.contact == null) insertContact(contactEntity)
                    else updateContact(contactEntity)
                }
            }
        }
    }
}