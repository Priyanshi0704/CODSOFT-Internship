package com.example.todolist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.firestore.auth.User

class SignUpActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase
        database = FirebaseDatabase.getInstance("https://data-base-269ba-default-rtdb.firebaseio.com/")
            .getReference("Users")

        val signUpButton = findViewById<Button>(R.id.btnSignup)
        val etName = findViewById<TextInputEditText>(R.id.etname)
        val etMail = findViewById<TextInputEditText>(R.id.etmail)
        val etPass = findViewById<TextInputEditText>(R.id.etpass)
        val etUN = findViewById<TextInputEditText>(R.id.etu)
        val signInTextview = findViewById<TextView>(R.id.tvsignin)

        signUpButton.setOnClickListener {
            try {
                // Your existing code
            } catch (e: Exception) {
                Log.e("SignUp", "Signup crashed", e)
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
            val name = etName.text.toString().trim()
            val mail = etMail.text.toString().trim()
            val password = etPass.text.toString().trim()
            val username = etUN.text.toString().trim()

            // Validate all fields
            if (username.isEmpty() || name.isEmpty() || mail.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if username exists
            database.child(username).get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show()
                } else {
                    // Create new user
                    val user = Users(name, mail, password, username)

                    database.child(username).setValue(user)
                        .addOnSuccessListener {
                            Log.d("Firebase", "User created successfully")
                            Toast.makeText(this, "Signed Up Successfully!", Toast.LENGTH_SHORT).show()

                            // Clear fields
                            etName.text?.clear()
                            etMail.text?.clear()
                            etPass.text?.clear()
                            etUN.text?.clear()

                            // Navigate to MainActivity
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Log.e("Firebase", "Registration failed", e)
                            Toast.makeText(this, "Registration failed: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                }
            }.addOnFailureListener { e ->
                Toast.makeText(this, "Error checking username: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        signInTextview.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
        }
    }
}