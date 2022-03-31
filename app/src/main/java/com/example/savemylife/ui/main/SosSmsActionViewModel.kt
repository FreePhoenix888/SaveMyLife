package com.example.savemylife.ui.main

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap

import androidx.test.core.app.ApplicationProvider.getApplicationContext

import android.content.ContextWrapper
import android.content.Intent
import java.lang.Exception

import android.graphics.BitmapFactory
import android.provider.ContactsContract
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.*


class SosSmsActionViewModel : ViewModel() {



    private fun SaveToInternalStorage(bitmapImage: Bitmap): String? {
        val cw = ContextWrapper(getApplicationContext())
        // path to /data/data/yourapp/app_data/imageDir
        val directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE)
        // Create imageDir
        val path = File(directory, "profile.jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(path)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return directory.absolutePath
    }

    private fun LoadImageFromStorage(path: String): Bitmap? {
            val file = File(path, "profile.jpg")
            return BitmapFactory.decodeStream(FileInputStream(file))
    }
}