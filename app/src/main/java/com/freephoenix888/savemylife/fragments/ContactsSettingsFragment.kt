package com.freephoenix888.savemylife.fragments

import android.Manifest.permission.READ_CONTACTS
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.content.ContextCompat
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.databinding.FragmentContactsBinding
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class ContactsSettingsFragment : Fragment() {

    companion object {
        const val TAG = "ContactsFragment"
        const val PERMISSIONS_REQUEST_CODE = 1
        val PROJECTION = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
            ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER,
        )
        val PERMISSIONS = arrayOf(READ_CONTACTS)
    }

    private lateinit var binding: FragmentContactsBinding

    private val pickContactActivityLauncher = registerForActivityResult(ActivityResultContracts.PickContact()) { contactData: Uri? ->
        if(contactData == null){
            return@registerForActivityResult
        }
        val contestResolver = requireContext().contentResolver
        val cursor = contestResolver.query(contactData, null, null, null, null)
            ?: return@registerForActivityResult
        if(cursor.moveToFirst()){
            val idColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            val displayNameColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val thumbnailUriColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI)
            val hasPhoneColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
            val contactId = cursor.getString(idColumnIndex)
            Log.d(TAG, "Contact id: $contactId")
            val displayName = cursor.getString(displayNameColumnIndex)
            Log.d(TAG, "Display name: $displayName")
            val thumbnailUri = cursor.getString(thumbnailUriColumnIndex)
            Log.d(TAG, "Thumbnail uri: $thumbnailUri")
            val hasPhoneNumber = cursor.getString(hasPhoneColumnIndex).toInt() == 1
            if(hasPhoneNumber){
                val cursor2 = contestResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null,
                    null
                ) ?: return@registerForActivityResult
                while (cursor2.moveToNext()){
                    val phoneNumberColumnIndex = cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    val phoneNumber = cursor2.getString(phoneNumberColumnIndex)
                    Log.d(TAG, "$cursor2.position phone number: $phoneNumber")
                    // TODO Save contacts
                }
                cursor2.close()
            }
        }
        cursor.close()
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
        binding = FragmentContactsBinding.inflate(inflater)
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

        return binding.root
    }

}