package com.example.savemylife

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.example.savemylife.databinding.SosSmsActionActivityBinding

class SosSmsActionActivity : AppCompatActivity() {

    private lateinit var binding: SosSmsActionActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SosSmsActionActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addContactBtn.setOnClickListener { PickContact() }
    }

    public fun PickContact() {
        val pickContactIntent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        pickContactIntent.setDataAndType(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE)

        val getResult =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == Activity.RESULT_OK) {
                val value = it.data?.getStringExtra("input")
            }
        }

        getResult.launch(pickContactIntent)
    }

}