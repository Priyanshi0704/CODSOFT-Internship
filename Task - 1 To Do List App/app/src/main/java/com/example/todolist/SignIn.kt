package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignIn : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference

    companion object {
        const val KEY1 = "com.example.Database.SignInActivity.mail"
        const val KEY2 = "com.example.Database.SignInActivity.name"
        const val KEY3 = "com.example.Database.SignInActivity.username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_sign_in)

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance("https://data-base-269ba-default-rtdb.firebaseio.com/").getReference("Users")

        val signIn = findViewById<Button>(R.id.btnSignIn)
        val username = findViewById<TextInputEditText>(R.id.etu)
        val signUpText = findViewById<TextView>(R.id.tvSignUp)

        // Set up sign up text click listener
        signUpText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        signIn.setOnClickListener {
            val usernameString = username.text.toString().trim()
            if(usernameString.isNotEmpty()) {
                readData(usernameString)
            } else {
                Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readData(usernameString: String) {
        databaseReference.child(usernameString).get().addOnSuccessListener { snapshot ->
            if(snapshot.exists()) {
                // User exists - proceed to main activity
                val mail = snapshot.child("mail").value.toString()
                val name = snapshot.child("name").value.toString()
                val uniqueUsername = snapshot.child("username").value.toString()

                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra(KEY1, mail)
                    putExtra(KEY2, name)
                    putExtra(KEY3, uniqueUsername)
                }
                startActivity(intent)
                finish() // Close sign in activity
            } else {
                // User doesn't exist
                Toast.makeText(this,
                    "Username doesn't exist, please sign up first",
                    Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Database error: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }
}