package com.example.finalproject

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import com.example.finalproject.entity.User
import com.example.finalproject.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etPhone: EditText
    private lateinit var btnsignin: Button
    private lateinit var btnregister: Button
    private lateinit var imgProfile: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnsignin = findViewById(R.id.btnsignin)
        btnregister = findViewById(R.id.btnregister)
        etPhone = findViewById(R.id.etPhone)
        imgProfile = findViewById(R.id.imgProfile)

        imgProfile.setOnClickListener {
            loadPopupMenu()
        }

        btnregister.setOnClickListener{
            registerUser()
        }
    }

    private var REQUEST_GALLERY_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
    private var imageURL: String? = null

    private fun loadPopupMenu() {
        val popupMenu = PopupMenu(this, imgProfile)
        popupMenu.menuInflater.inflate(R.menu.gallery_camera, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuCamera ->
                    openCamera()
                R.id.menuGallery ->
                    openGallery()
            }
            true
        }
        popupMenu.show()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }

    private fun registerUser() {
        val username = etUsername.text.toString()
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        val phone = etPhone.text.toString()

        val user = User(username = username,phoneNo = phone, password = password, email = email)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepo = UserRepository()
                val response = userRepo.registerUser(user)
                if(response.success == true){
                    if (imageURL != null){
                        uploadImage(response.data!!._id!!)
                    }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                                this@RegisterActivity,
                                "User Registered", Toast.LENGTH_SHORT
                        ).show()
                        emptyForm()
                    }
                }
            } catch (ex: Exception) {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(
//                            this@RegisterActivity,
//                            ex.toString(), Toast.LENGTH_LONG
//                    ).show()
//                }
            }
        }
    }

    private fun uploadImage(userId: String) {
        if (imageURL != null) {
            val imageFile = File(imageURL!!)
            val requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), imageFile)
            val body =
                    MultipartBody.Part.createFormData("file", imageFile.name, requestFile)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val userRepo = UserRepository()
                    val response = userRepo.userImageUpload(userId, body)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@RegisterActivity, "Uploaded", Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }
                } catch (ex: Exception) {
//                    withContext(Dispatchers.Main) {
//                        Log.d("My Error ", ex.localizedMessage)
//                        Toast.makeText(
//                                this@RegisterActivity,
//                                ex.localizedMessage,
//                                Toast.LENGTH_SHORT
//                        ).show()
//                    }
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = contentResolver
                val cursor =
                        contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageURL = cursor.getString(columnIndex)
                imgProfile.setImageBitmap(BitmapFactory.decodeFile(imageURL))
                cursor.close()
            } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageURL = file!!.absolutePath
                imgProfile.setImageBitmap(BitmapFactory.decodeFile(imageURL))
            }
        }
    }

    private fun bitmapToFile(
            bitmap: Bitmap,
            fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                            .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }

    private fun emptyForm() {
        etUsername.setText("")
        etEmail.setText("")
        etPassword.setText("")
        etPhone.setText("")
    }
}