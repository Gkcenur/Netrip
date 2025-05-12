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
    private val allDiaryList = mutableListOf<DiaryEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_diary)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<ImageButton>(R.id.btnAdd).setOnClickListener {
            startActivity(Intent(this, AddDiaryActivity::class.java))
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        diaryAdapter = DiaryEntryAdapter(diaryList) { diaryEntry ->
            deleteDiaryEntry(diaryEntry)
        }
        recyclerView.adapter = diaryAdapter

        val etSearch = findViewById<android.widget.EditText>(R.id.etSearch)
        etSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: android.text.Editable?) {
                val query = s.toString().trim().lowercase()
                diaryList.clear()
                if (query.isEmpty()) {
                    diaryList.addAll(allDiaryList)
                } else {
                    diaryList.addAll(allDiaryList.filter {
                        it.title.lowercase().contains(query) ||
                        it.location.lowercase().contains(query) ||
                        it.notes.lowercase().contains(query)
                    })
                }
                diaryAdapter.notifyDataSetChanged()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

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
                allDiaryList.clear()
                for (document in result) {
                    val entry = document.toObject(DiaryEntry::class.java)
                    diaryList.add(entry)
                    allDiaryList.add(entry)
                }
                diaryAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                // Hata mesajı göster
            }
    }

    private fun deleteDiaryEntry(diaryEntry: DiaryEntry) {
        val firestore = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        // Günlükleri benzersiz tanımlamak için bir id alanı olması gerekir. Eğer yoksa, Firestore'dan silmek için ek bilgi gerekir.
        // Burada örnek olarak title, date, time ve userId ile siliyoruz. Eğer benzersiz bir id varsa onunla silmek daha doğru olur.
        firestore.collection("diaryEntries")
            .whereEqualTo("userId", userId)
            .whereEqualTo("title", diaryEntry.title)
            .whereEqualTo("date", diaryEntry.date)
            .whereEqualTo("time", diaryEntry.time)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    firestore.collection("diaryEntries").document(document.id).delete()
                }
                diaryList.remove(diaryEntry)
                allDiaryList.remove(diaryEntry)
                diaryAdapter.notifyDataSetChanged()
            }
    }
}
