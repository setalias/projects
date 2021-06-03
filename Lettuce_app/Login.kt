package com.example.lettuceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.lettuceapp.data.CustomerDomain
import com.lettuceapp.data.RestaurantDomain

class Login : AppCompatActivity() {
    private val customerDomain = CustomerDomain(this)
    private val restaurantDomain = RestaurantDomain(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // xml
        setUserSpinner() // drop down
        val context = this

        val loginBtn = findViewById<Button>(R.id.loginButton)
        val registerBtn = findViewById<Button>(R.id.registerButton)

        registerBtn.setOnClickListener {
            val intent = Intent(this, CreateProfileChoice::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            val userSpinner = findViewById<Spinner>(R.id.user_spinner)
            val selectedUser = userSpinner.selectedItem // grab value from drop down selection

            if(verifyLogin(selectedUser.toString())) {
                if (selectedUser.toString() == "Customer") {
                    val intent = Intent(this, CustomerDashboard::class.java)
                    intent.putExtra( // save the email to use in other activities
                        "email",
                        findViewById<EditText>(R.id.loginEmail).text.toString().toLowerCase()
                    )
                    startActivity(intent)
                }
                else if(selectedUser.toString() == "Restaurant") {
                    val intent = Intent(this, RestaurantDashboard::class.java)
                    intent.putExtra(
                        "email",
                        findViewById<EditText>(R.id.loginEmail).text.toString().toLowerCase()
                    )
                    startActivity(intent)
                }
            }
            else {
                Toast.makeText(context, "Invalid username or password!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun verifyLogin(selectedUserType : String) : Boolean {
        val loginEmail = findViewById<EditText>(R.id.loginEmail)
        val loginPassword = findViewById<EditText>(R.id.loginPassword)

        // check dB to see if email and password matches
        if(selectedUserType == "Customer") {
            val response = customerDomain.getPasswordFromEmail(loginEmail.text.toString().toLowerCase())
            return loginPassword.text.toString() == response
        } else if(selectedUserType == "Restaurant") {
            val response = restaurantDomain.getPasswordFromEmail(loginEmail.text.toString().toLowerCase())
            return loginPassword.text.toString() == response
        }

        return false
    }

    private fun setUserSpinner() {
        val spinner: Spinner = findViewById(R.id.user_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.User_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }
}