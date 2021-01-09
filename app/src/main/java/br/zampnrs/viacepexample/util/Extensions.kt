package br.zampnrs.viacepexample.util

import android.widget.Toast
import androidx.fragment.app.Fragment
import br.zampnrs.viacepexample.data.ContactEntity
import br.zampnrs.viacepexample.model.Contact
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun List<ContactEntity>.mapToContactClass(): List<Contact> {
    return ArrayList<Contact>().also {
        for (contactEntity in this) {
            it.add(
                Contact(
                    uuid = contactEntity.uuid,
                    name = contactEntity.name,
                    email = contactEntity.email,
                    phone = contactEntity.phone,
                    cep = contactEntity.cep,
                    street = contactEntity.street,
                    number = contactEntity.number,
                    complement = contactEntity.complement,
                    city = contactEntity.city,
                    uf = contactEntity.uf
                )
            )
        }
    }.toList()
}

fun ContactEntity.equalsTo(contact: Contact): Boolean {
    return (this.name == contact.name
            && this.email == contact.email
            && this.phone == contact.phone
            && this.cep == contact.cep
            && this.street == contact.street
            && this.number == contact.number
            && this.complement == contact.complement
            && this.city == contact.city
            && this.uf == contact.uf)
}

fun Fragment.showToast(message: String) {
    Toast.makeText(
        requireContext(),
        message,
        Toast.LENGTH_LONG
    ).show()
}