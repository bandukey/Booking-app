package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Adapter.FutsalAdapter
import com.example.finalproject.entity.Futsal
import com.example.finalproject.repository.FutsalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewFutsalsActivity : AppCompatActivity() {

    private lateinit var rvFutsal: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_futsals)

        rvFutsal = findViewById(R.id.rvFutsal)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val itemRepo = FutsalRepository()
                val response = itemRepo.getAllfutsal()
                if (response.success == true){
                    val lstItems = response.data
                    withContext(Dispatchers.Main){
                        val adapter = FutsalAdapter(lstItems as ArrayList<Futsal>, this@ViewFutsalsActivity)
                        rvFutsal.layoutManager = LinearLayoutManager(this@ViewFutsalsActivity)
                        rvFutsal.adapter = adapter
                    }
                }
            } catch (ex: Exception) {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(activity,
//                        "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
//                }
            }
        }
    }
}