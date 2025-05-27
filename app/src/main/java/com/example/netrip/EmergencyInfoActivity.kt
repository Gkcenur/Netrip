package com.example.netrip

import android.os.Bundle
import android.widget.ImageButton

class EmergencyInfoActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }
    }

    override fun getLayoutId(): Int = R.layout.activity_emergency_info
}
