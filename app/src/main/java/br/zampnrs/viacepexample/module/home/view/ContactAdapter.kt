package br.zampnrs.viacepexample.module.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.zampnrs.viacepexample.R
import br.zampnrs.viacepexample.model.Contact
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactAdapter(
        private var contactList: List<Contact> = emptyList()
): RecyclerView.Adapter<ContactViewHolder>() {
    var onSelectContact: ((contact: Contact) -> Unit)? = null
    var onContactOptions: ((contact: Contact) -> Unit)? = null

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ContactViewHolder {
        return ContactViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_contact,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.apply {
            with(contactList[position]) {
                bind(this)

                itemView.profile_name_textView.setOnClickListener {
                    onSelectContact?.invoke(this)
                }

                itemView.more_imageView.setOnClickListener {
                    onContactOptions?.invoke(this)
                }
            }
        }
    }

    override fun getItemCount(): Int = contactList.size

    fun setList(contactList: List<Contact>) {
        this.contactList = contactList
        notifyDataSetChanged()
    }
}