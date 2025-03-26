package com.example.simplebankingapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)

        //input for login credentials
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        signUpText = findViewById(R.id.signUpText)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            //verify login
            val storedUsername = sharedPreferences.getString("username", "")
            val storedPassword = sharedPreferences.getString("password", "")
            if (username == storedUsername && password == storedPassword) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                //Proceed to main activity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Redirect to the MainActivity
                Toast.makeText(this, "Invalid credentials, please try again.", Toast.LENGTH_SHORT)
                    .show()
            }


        }
        signUpText.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun login(username: String, password: String): Boolean {
        val savedUsername = sharedPreferences.getString("username", null)
        val savedPassword = sharedPreferences.getString("password", null)

        return username == savedUsername && password == savedPassword
    }
}
