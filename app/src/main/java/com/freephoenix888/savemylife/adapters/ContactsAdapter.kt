package com.freephoenix888.savemylife.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.freephoenix888.savemylife.data.models.ContactModel
import com.freephoenix888.savemylife.databinding.FragmentContactBinding

class ContactsAdapter(val removeContact: (contact: ContactModel) -> Unit): RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private val _contacts = mutableListOf<ContactModel>()

    class ViewHolder(val binding: FragmentContactBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentContactBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = _contacts[position]
        contact.thumbnailUri?.let { holder.binding.image.setImageURI(it.toUri()) }
        holder.binding.name.text = contact.name
        holder.binding.phoneNumber.text = contact.phoneNumber
        holder.binding.buttonRemoveContact.setOnClickListener {
            removeContact(contact)
        }
    }

    override fun getItemCount(): Int {
        return _contacts.size
    }

    public fun submitContacts(contacts: List<ContactModel>) {
        _contacts.clear()
        _contacts.addAll(contacts)
        notifyDataSetChanged()
    }

}