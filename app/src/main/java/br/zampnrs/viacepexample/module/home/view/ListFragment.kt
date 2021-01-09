package br.zampnrs.viacepexample.module.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.zampnrs.viacepexample.databinding.FragmentListBinding
import br.zampnrs.viacepexample.model.Contact
import br.zampnrs.viacepexample.module.home.viewmodel.ContactViewModel
import br.zampnrs.viacepexample.util.mapToContactClass
import org.koin.android.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val viewModel: ContactViewModel by viewModel()
    private val contactAdapter = ContactAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getContacts().also { fromDb ->
            if (fromDb.isNotEmpty())
                contactAdapter.apply {
                    setList(fromDb.mapToContactClass())
                    onSelectContact = ::onContactSelected
                }.also {
                    binding.recyclerContacts.adapter = it
                }
        }
        binding.setUpAddContact()
    }

    private fun onContactSelected(contact: Contact) {
        findNavController()
            .navigate(
                ListFragmentDirections
                    .actionListFragmentToContactBottomSheet()
                    .setContact(contact)
            )
    }

    private fun FragmentListBinding.setUpAddContact() {
        addContactFab.setOnClickListener {
            findNavController()
                .navigate(
                    ListFragmentDirections
                        .actionListFragmentToContactBottomSheet()
                )
        }
    }
}