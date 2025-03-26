package com.example.simplebankingapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.simplebankingapp.R

class InitialBalanceActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var initialBalanceEditText: EditText
    private lateinit var submitBalanceButton: Button
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.initbalance)

        sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        initialBalanceEditText = findViewById(R.id.initialBalanceEditText)
        submitBalanceButton = findViewById(R.id.submitBalanceButton)

        // Assuming the username is passed from the sign-up activity or is saved in shared preferences
        username = sharedPreferences.getString("username", "") ?: ""

        submitBalanceButton.setOnClickListener {
            val initialBalance = initialBalanceEditText.text.toString().toFloatOrNull()

            if (initialBalance != null && initialBalance >= 0) {
                // Save the initial balance in SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putFloat("$username-balance", initialBalance)
                editor.apply()

                // Redirect to MainActivity after saving the initial balance
                Toast.makeText(this, "Initial balance set successfully!", Toast.LENGTH_SHORT).show()

                // Go to MainActivity where transactions will happen
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Show an error message if the balance is invalid
                Toast.makeText(this, "Please enter a valid initial balance.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
