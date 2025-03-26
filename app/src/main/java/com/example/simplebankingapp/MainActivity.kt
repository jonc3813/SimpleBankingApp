package com.example.simplebankingapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var balanceTextView: TextView
    private lateinit var depositButton: Button
    private lateinit var withdrawButton: Button
    private lateinit var depositEditText: EditText
    private lateinit var withdrawEditText: EditText
    private lateinit var transactionListView: ListView
    private lateinit var transactionAdapter: ArrayAdapter<String>
    private lateinit var username: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitymain)

        //retrieve data from sharedpreferences
        sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)


        // Initialize views
        balanceTextView = findViewById(R.id.balanceTextView)
        depositButton = findViewById(R.id.depositButton)
        withdrawButton = findViewById(R.id.withdrawalButton)
        depositEditText = findViewById(R.id.depositEditText)
        withdrawEditText = findViewById(R.id.withdrawEditText)
        transactionListView = findViewById(R.id.transactionListView)

        // Assuming username is saved during login
        username = sharedPreferences.getString("username", "") ?: ""

        // Get current balance from SharedPreferences
        val currentBalance = sharedPreferences.getFloat("$username-balance", 0f)
        balanceTextView.text = "Balance: RM${"%.2f".format(currentBalance)}"

        // Set up ListView to show transactions
        transactionAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, getTransactionHistoryDisplayList())
        transactionListView.adapter = transactionAdapter


        depositButton.setOnClickListener {
            val depositAmount = depositEditText.text.toString().toFloatOrNull()
            if (depositAmount != null && depositAmount > 0) {
                updateBalance(depositAmount)
                val transaction = Transaction("Deposit", depositAmount, System.currentTimeMillis())
                saveTransaction(transaction)
                updateTransactionHistory()
            } else {
                Toast.makeText(this, "Please enter a valid deposit amount", Toast.LENGTH_SHORT).show()
            }
        }

        withdrawButton.setOnClickListener {
            val withdrawAmount = withdrawEditText.text.toString().toFloatOrNull()
            if (withdrawAmount != null && withdrawAmount > 0) {
                updateBalance(-withdrawAmount)
                val transaction = Transaction("Withdraw", withdrawAmount, System.currentTimeMillis())
                saveTransaction(transaction)
                updateTransactionHistory()
            } else {
                Toast.makeText(this, "Please enter a valid withdrawal amount", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Update the balance and save it in SharedPreferences
    private fun updateBalance(amount: Float) {
        val currentBalance = sharedPreferences.getFloat("$username-balance", 0f)
        val newBalance = currentBalance + amount
        val editor = sharedPreferences.edit()
        editor.putFloat("$username-balance", newBalance)
        editor.apply()

        // Update the displayed balance
        balanceTextView.text = "Balance: RM${"%.2f".format(newBalance)}"
    }

    // Save the transaction in SharedPreferences
    private fun saveTransaction(transaction: Transaction) {
        val transactionList = getTransactionHistory()

        // Add the new transaction to the list
        transactionList.add(transaction)

        // Convert the list to JSON
        val gson = Gson()
        val json = gson.toJson(transactionList)

        // Save the updated transaction list in SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("$username-transaction-history", json)
        editor.apply()
    }

    // Get the list of transactions from SharedPreferences
    private fun getTransactionHistory(): MutableList<Transaction> {
        val gson = Gson()
        val json = sharedPreferences.getString("$username-transaction-history", "[]")
        val type = object : TypeToken<MutableList<Transaction>>() {}.type
        return gson.fromJson(json, type)
    }

    // Get the transaction history formatted for display in the ListView
    private fun getTransactionHistoryDisplayList(): List<String> {
        val transactionList = getTransactionHistory()
        val displayList = mutableListOf<String>()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        // Format transactions with a timestamp
        for (transaction in transactionList) {
            val date = dateFormat.format(Date(transaction.timestamp))
            displayList.add("${transaction.type}: RM${"%.2f".format(transaction.amount)} on $date")
        }

        return displayList
    }

    // Update the ListView with the latest transaction history
    private fun updateTransactionHistory() {
        val transactionHistory = getTransactionHistoryDisplayList()
        transactionAdapter.clear()
        transactionAdapter.addAll(transactionHistory)
    }

    // Transaction model to store transaction data
    data class Transaction(
        val type: String, // "Deposit" or "Withdraw"
        val amount: Float,
        val timestamp: Long // Time in milliseconds
    )
}
