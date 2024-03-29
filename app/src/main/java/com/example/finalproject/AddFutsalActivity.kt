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
import com.example.finalproject.entity.Futsal
import com.example.finalproject.repository.FutsalRepository
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

class AddFutsalActivity : AppCompatActivity() {
    private lateinit var etfutsalname: EditText
    private lateinit var etprice: EditText
    private lateinit var etlocation: EditText
//    private lateinit var etduration: TextInputEditText
    private lateinit var btnaddfut: Button
    private lateinit var imgfutsal: ImageView
    private lateinit var etphone: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_futsal)

        etfutsalname = findViewById(R.id.etfutname)
        etprice = findViewById(R.id.etprice)
        etlocation = findViewById(R.id.etlocation)
//        etduration = findViewById(R.id.etduration)
        btnaddfut = findViewById(R.id.btnaddfut)
        imgfutsal = findViewById(R.id.imgFutsal)
        etphone = findViewById(R.id.etphone)

        btnaddfut.setOnClickListener {
            saveNews()
        }

        imgfutsal.setOnClickListener {
            loadPopupMenu()
        }
    }

    private var REQUEST_GALLERY_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
    private var imageUrl: String? = null

    private fun loadPopupMenu() {
        val popupMenu = PopupMenu(this, imgfutsal)
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

    private fun saveNews() {
        val name = etfutsalname.text.toString()
        val location = etlocation.text.toString()
        val price = etprice.text.toString()
        val phn = etphone.text.toString()

        val futsal = Futsal (name = name, phoneno = phn,price = price,location = location)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val newsfeedRepository = FutsalRepository()
                val response = newsfeedRepository.addfutsal(futsal)
                if(response.success == true){
                    if (imageUrl != null){
                        uploadImage(response.data!!._id!!)


                    }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                                this@AddFutsalActivity,
                                "Newsfeed has been uploaded", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (ex: Exception) {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(
//                            this@AddFutsalActivity,
//                            ex.toString(), Toast.LENGTH_SHORT
//                    ).show()
//                }
            }
        }
    }

    private fun uploadImage(futsalId: String) {
        if (imageUrl != null) {
            val file = File(imageUrl!!)
            val reqFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body =
                MultipartBody.Part.createFormData("file", file.name, reqFile)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val futsalRepo = FutsalRepository()
                    val response = futsalRepo.uploadImage(futsalId, body)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@AddFutsalActivity, "Uploaded", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } catch (ex: Exception) {
//                    withContext(Dispatchers.Main) {
//                        Log.d("My Error ", ex.localizedMessage)
//                        Toast.makeText(
//                            this@InsertFoodActivity,
//                            ex.localizedMessage,
//                            Toast.LENGTH_SHORT
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
                imageUrl = cursor.getString(columnIndex)
                imgfutsal.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                imgfutsal.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
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
}