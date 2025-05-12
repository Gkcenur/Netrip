package com.example.netrip

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TravelDiaryActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var diaryAdapter: DiaryEntryAdapter
    private val diaryList = mutableListOf<DiaryEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_diary)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<ImageButton>(R.id.btnAdd).setOnClickListener {
            startActivity(Intent(this, AddDiaryActivity::class.java))
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        diaryAdapter = DiaryEntryAdapter(diaryList)
        recyclerView.adapter = diaryAdapter

        fetchDiaryEntries()
    }

    private fun fetchDiaryEntries() {
        val firestore = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        firestore.collection("diaryEntries")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                diaryList.clear()
                for (document in result) {
                    val entry = document.toObject(DiaryEntry::class.java)
                    diaryList.add(entry)
                }
                diaryAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                // Hata mesajı göster
            }
    }
}
