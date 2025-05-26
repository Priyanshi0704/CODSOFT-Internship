package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        val logo = findViewById<ImageView>(R.id.logo)
        logo.postDelayed({
            val signUp = Intent(this, SignUpActivity::class.java)
            startActivity(signUp)
            finish()
        },2000)
    }
}