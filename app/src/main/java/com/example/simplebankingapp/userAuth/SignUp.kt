package com.example.simplebankingapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.simplebankingapp.R

class SignUpActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        //retrieve data from sharedpreferences
        sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)

        //credential input for signing up
        usernameEditText = findViewById(R.id.signupUsernameEditText)
        passwordEditText = findViewById(R.id.signupPasswordEditText)
        signUpButton = findViewById(R.id.signupButton)


        signUpButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (signUp(username, password)) {
                // Redirect to InitialBalanceActivity if sign-up is successful
                val intent = Intent(this, InitialBalanceActivity::class.java)
                startActivity(intent)
                finish() // Close the sign-up screen
            } else {
                // Show an error message
                Toast.makeText(this, "Sign-up failed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //store data into sharedpreference
    private fun signUp(username: String, password: String): Boolean {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            val editor = sharedPreferences.edit()
            editor.putString("username", username)
            editor.putString("password", password)
            editor.putBoolean("isNewUser", true) // Mark as new user
            editor.putStringSet("$username-transactions", setOf()) // Initialize empty transaction set for the user
            editor.putFloat("$username-initialBalance", 0f) // Initial balance set to 0
            editor.apply()
            return true
        }
        return false
    }
}
