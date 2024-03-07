package com.example.contentproviderexercise

import android.Manifest
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.contentproviderexercise.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGrant ->
        if(isGrant){
            getContact()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLoad.setOnClickListener {
            requestPermission.launch(Manifest.permission.READ_CONTACTS)
        }

    }

    @SuppressLint("Recycle")
    private fun getContact() {
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        contentResolver.query(uri, projection, null, null, null)?.let{
            if(it.count > 0){
                val displayNameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val phoneNumberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

                val list = mutableListOf<Contact>()

                while (it.moveToNext()){
                    val contactName = it.getString(displayNameIndex)
                    val phoneNumber = it.getString(phoneNumberIndex)
                    list.add(Contact(contactName, phoneNumber))
                }
                val adapter = ContactAdapter()
                binding.listItem.adapter = adapter
                adapter.setListData(list)
            }
        }
    }
}