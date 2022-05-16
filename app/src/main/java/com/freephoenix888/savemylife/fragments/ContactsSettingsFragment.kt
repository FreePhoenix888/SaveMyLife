package com.freephoenix888.savemylife.fragments

import android.Manifest.permission.READ_CONTACTS
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.activity.result.registerForActivityResult
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.SaveMyLifeApplication
import com.freephoenix888.savemylife.adapters.ContactAdapter
import com.freephoenix888.savemylife.data.db.entities.ContactEntity
import com.freephoenix888.savemylife.data.models.ContactModel
import com.freephoenix888.savemylife.databinding.FragmentContactsSettingsBinding
import com.freephoenix888.savemylife.ui.ContactViewModel
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class ContactsSettingsFragment : Fragment() {

    companion object {
        val TAG = this::class.qualifiedName!!
        const val PERMISSIONS_REQUEST_CODE = 1
        val PROJECTION = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
            ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER,
        )
        val PERMISSIONS = arrayOf(READ_CONTACTS)
    }

    private lateinit var binding: FragmentContactsSettingsBinding
    @Inject lateinit var contactViewModel: ContactViewModel

    private val pickContactActivityLauncher = registerForActivityResult(ActivityResultContracts.PickContact()) { contactData: Uri? ->
        Log.d(TAG, "ContactUri: $contactData")

        if(contactData == null){
            return@registerForActivityResult
        }

        contactViewModel.viewModelScope.launch {
            contactViewModel.insert(ContactEntity(uri = contactData.toString()))
        }
    }

    private fun getContactsFromUri(uri: String): List<ContactModel>{
        val contestResolver = requireContext().contentResolver
        val cursor = contestResolver.query(uri.toUri(), null, null, null, null)
            ?: throw Throwable("Contact with uri $uri does not exist.")
        val contacts = mutableListOf<ContactModel>()
        if(cursor.moveToFirst()){
            val idColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            val contactId = cursor.getString(idColumnIndex)
            Log.d(TAG, "Contact id: $contactId")

            val displayNameColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val name = cursor.getString(displayNameColumnIndex)
            Log.d(TAG, "Display name: $name")

            val thumbnailUriColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI)
            val thumbnailUri: String? = cursor.getString(thumbnailUriColumnIndex)
            Log.d(TAG, "Thumbnail uri: $thumbnailUri")

            val hasPhoneColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
            val hasPhoneNumber = cursor.getString(hasPhoneColumnIndex).toInt() == 1
            if(hasPhoneNumber){
                val cursor2 = contestResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null,
                    null
                )
                    ?: throw Throwable("Contact does not have phone number.")

                while (cursor2.moveToNext()){
                    contactViewModel.viewModelScope.launch {
                        val phoneNumberColumnIndex = cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        val phoneNumber = cursor2.getString(phoneNumberColumnIndex)
                        val contact = ContactModel(uri = uri, name = name, phoneNumber = phoneNumber, thumbnailUri = thumbnailUri)
                        contacts.add(contact)
                    }
                }

                cursor2.close()
            }
        }
        cursor.close()
        return contacts
    }

    override fun onAttach(context: Context) {
        (context.applicationContext as SaveMyLifeApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentContactsSettingsBinding.inflate(inflater)

        binding.addContactBtn.setOnClickListener {
            val rationale = getString(R.string.read_contacts_permission_rationale)
            if(!EasyPermissions.hasPermissions(requireContext(), READ_CONTACTS)){
                EasyPermissions.requestPermissions(this, rationale, PERMISSIONS_REQUEST_CODE, READ_CONTACTS)
            }
            if(!EasyPermissions.hasPermissions(requireContext(), READ_CONTACTS)){
                Toast.makeText(requireContext(), rationale, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            pickContactActivityLauncher.launch()


        }

        val adapter = ContactAdapter(removeContact = {contact: ContactModel ->
            contactViewModel.viewModelScope.launch {
                contactViewModel.delete(ContactEntity(uri = contact.uri))
            }
        })


        binding.recyclerView.adapter = adapter
        contactViewModel.contacts.observe(viewLifecycleOwner) { contactEntities: List<ContactEntity> ->
            val contacts = mutableListOf<ContactModel>()
            contactEntities.forEach { contactEntity: ContactEntity ->
                contacts.addAll(getContactsByUri(contactEntity.uri))
            }
            adapter.submitContacts(contacts = contacts)
        }

        return binding.root
    }

}