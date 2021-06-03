package com.example.lettuceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CreateProfileChoice : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile_choice)

        val createCustomerBtn = findViewById<Button>(R.id.createCustomerProfile)
        createCustomerBtn.setOnClickListener{
            val intent = Intent(this, CreateCustomer::class.java)
            startActivity(intent)
        }

        val createRestaurantBtn = findViewById<Button>(R.id.createRestaurantProfile) // Login Btn
        createRestaurantBtn.setOnClickListener{
            val intent = Intent(this, CreateRestaurant::class.java)
            startActivity(intent)
        }
    }
}