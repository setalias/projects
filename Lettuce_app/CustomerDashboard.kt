package com.example.lettuceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.lettuceapp.data.CustomerDto
import com.lettuceapp.data.CustomerDomain

class CustomerDashboard : AppCompatActivity() {
    private val customerDomain = CustomerDomain(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_dashboard)
        val customerList = getLoggedInCustomer()
        val customerNameText = findViewById<TextView>(R.id.customerName)
        val emailFromLastActivity = intent.getStringExtra("email")
        customerNameText.text = customerList[0].firstName + " " + customerList[0].lastName
        val customerIDFromLastActivity = customerList[0].id


        // edit profile
        val modifyCustomerProfile = findViewById<Button>(R.id.modifyCustomerProfile)
        modifyCustomerProfile.setOnClickListener(){
            val intent = Intent(this, EditCustomerProfile::class.java)
            intent.putExtra("email", emailFromLastActivity)
            startActivity(intent)
        }

        // create a reservation
        val createCustomerReservation = findViewById<Button>(R.id.createCustomerReservation)
        createCustomerReservation.setOnClickListener(){
            val intent = Intent(this, SearchRestaurant::class.java)
            intent.putExtra("email", emailFromLastActivity)
            startActivity(intent)
        }

        // view/modify reservation
        val viewReservationDisplay = findViewById<Button>(R.id.modifyReservation)
        viewReservationDisplay.setOnClickListener(){
            val intent = Intent(this, ReservationDisplay::class.java)
                intent.putExtra("customerID", customerIDFromLastActivity)
                startActivity(intent)
        }
    }

    private fun getLoggedInCustomer() : List<CustomerDto> {
        val email = intent.getStringExtra("email")
        return customerDomain.getCustomerByEmail(email.toString())
    }

    fun onClick(view: View) {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }
}