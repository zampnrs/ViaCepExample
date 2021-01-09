package br.zampnrs.viacepexample.module.home.view

import androidx.recyclerview.widget.RecyclerView
import br.zampnrs.viacepexample.databinding.ItemContactBinding
import br.zampnrs.viacepexample.model.Contact

class ContactViewHolder(
    private val binding: ItemContactBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(contact: Contact) {
        binding.profileNameTextView.text = contact.name
    }
}