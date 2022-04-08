package com.example.savemylife.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import com.example.savemylife.R
import com.example.savemylife.databinding.FragmentActionsListBinding
import com.example.savemylife.databinding.FragmentSmsActionSettingsBinding
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class SmsActionSettingsFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    companion object {
        const val PERMISSION_READ_CONTACTS_REQUEST_CODE = 1
    }

    private val PROJECTION: Array<out String> = arrayOf(
        ContactsContract.Data.HAS_PHONE_NUMBER,
        ContactsContract.CommonDataKinds.Phone.NUMBER
//        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
//        ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
    )

    private lateinit var binding: FragmentSmsActionSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSmsActionSettingsBinding.inflate(inflater, container, false)
        val pickContactIntent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        val pickContactIntentCallback: ActivityResultCallback<ActivityResult> = ActivityResultCallback {
            if(it.resultCode != AppCompatActivity.RESULT_OK){
                return@ActivityResultCallback
            }
            val contactUri: Uri = it.data!!.data!!
            val cursor = context?.applicationContext?.contentResolver?.query(contactUri, PROJECTION, null, null, null)
            if (cursor != null){
                cursor.moveToFirst()
                val hasPhoneNumberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER)
                val hasPhoneNumber: Boolean = (cursor.getString(hasPhoneNumberColumnIndex).toInt() > 0)
                if(hasPhoneNumber){
                    val phoneNumberOfColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    val phoneNumber = cursor.getString(phoneNumberOfColumnIndex)
                    Log.println(Log.DEBUG, null,  "Contact with phone number $phoneNumber is picked")
                }
//                    val phoneNumber = cursor.getString(phoneNumberOfColumnIndex)
//                    val contactNameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
//                    val contactName = cursor.getString(contactNameColumnIndex)
                cursor.close()
            }
        }
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), pickContactIntentCallback)

        binding.addContactBtn.setOnClickListener {
            if(!hasReadContactsPermission()){
                requenstReadContactsPermission()
            }
            resultLauncher.launch(pickContactIntent)
        }


        return binding.root
    }

    private fun hasReadContactsPermission() =
        EasyPermissions.hasPermissions(requireContext(), android.Manifest.permission.READ_CONTACTS)

    private fun requenstReadContactsPermission(){
        EasyPermissions.requestPermissions(
            this,
            "App must have permission to read contacts to be able to add emergency contacts.",
            PERMISSION_READ_CONTACTS_REQUEST_CODE,
            android.Manifest.permission.READ_CONTACTS
        )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {


    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)){
            AppSettingsDialog.Builder(requireActivity()).build().show()
        } else {
            requenstReadContactsPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

}