package com.example.lettuceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Selection
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.*
import com.lettuceapp.data.CustomerDomain
import java.time.temporal.ValueRange


class SearchRestaurant : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_restaurant)
        setFoodTypeSpinner()
        setDistanceSpinner()

        val emailFromLastActivity = intent.getStringExtra("email")
        val searchRestaurantButton = findViewById<Button>(R.id.searchRestaurantButton)
        searchRestaurantButton.setOnClickListener{

        val zipCodeIntent = Intent(this, SelectionPage::class.java)
        val intentBundle = Bundle()

        val value = findViewById<EditText>(R.id.editSearchZipCode)
        intentBundle.putString("email",emailFromLastActivity)
        intentBundle.putInt("zipCode", value.text.toString().toInt())
        zipCodeIntent.putExtras(intentBundle)
        startActivity(zipCodeIntent)
        }
    }

    private fun setFoodTypeSpinner() {
        val spinner: Spinner = findViewById(R.id.foodType_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.FoodType_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    private fun setDistanceSpinner() {
        val spinner: Spinner = findViewById(R.id.distance_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.Distance_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    fun onClick(view: View) {
        val intent = Intent(this, CustomerDashboard::class.java)
        startActivity(intent)
    }
}

