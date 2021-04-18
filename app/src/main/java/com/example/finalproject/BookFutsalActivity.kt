package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Adapter.FutsalAdapter
import com.example.finalproject.entity.Futsal
import com.example.finalproject.repository.FutsalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookFutsalActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnaddnews: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_book_futsal)
        btnaddnews = findViewById((R.id.Add))
        recyclerView = findViewById(R.id.recyclerview_futsal)



        btnaddnews.setOnClickListener {
            startActivity(Intent(this@BookFutsalActivity, AddFutsalActivity::class.java))
        }
        loadNews()
    }


    private fun loadNews() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val futsalRepository = FutsalRepository()
                val response = futsalRepository.getAllfutsal()
                if(response.success==true){
                    val lstfutsal = response.data
                    withContext(Dispatchers.Main){
                        val adapter = FutsalAdapter(lstfutsal  as ArrayList<Futsal>, this@BookFutsalActivity)
                        recyclerView.layoutManager = LinearLayoutManager(this@BookFutsalActivity)
                        recyclerView.adapter = adapter
                    }
                }
            }catch(ex : Exception){
//                withContext(Dispatchers.Main){
//                    Toast.makeText(this@BookFutsalActivity,
//                            "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
//                }
            }
        }
    }
}