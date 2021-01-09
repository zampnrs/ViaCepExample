package br.zampnrs.viacepexample.util

import br.zampnrs.viacepexample.data.ContactEntity
import br.zampnrs.viacepexample.model.Contact

fun List<ContactEntity>.mapToContactClass(): List<Contact> {
    return ArrayList<Contact>().also {
        for (contactEntity in this) {
            it.add(
                Contact(
                    uuid = contactEntity.uuid,
                    name = contactEntity.name,
                    email = contactEntity.email,
                    phone = contactEntity.phone
                )
            )
        }
    }.toList()
}