package com.example.lettuceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.lettuceapp.data.RestaurantDomain


class SelectionPage : AppCompatActivity() {
    private val restaurantDomain = RestaurantDomain(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_page)

        val zipCodeIntent = intent
        val zipCode = zipCodeIntent.getIntExtra("zipCode", 0)
        val data = restaurantDomain.getRestaurantByZipCode(zipCode)
        val layout = findViewById<LinearLayout>(R.id.restaurantsearchlayout)
        val emailFromLastActivity = intent.getStringExtra("email")

        for (i in 0 until data.size) {
            val restaurantButtons = Button(this) // create button
            restaurantButtons.text = data[i].restaurantName + "\n" + "Food: " + data[i].foodType + "\n"
            restaurantButtons.setOnClickListener {
                val intent = Intent(this, RestaurantDisplay::class.java)
                val intentBundle = Bundle()
                intentBundle.putString("email",emailFromLastActivity)
                intentBundle.putInt("restaurantID", data[i].id)
                intent.putExtras(intentBundle)
                startActivity(intent)
            }
            layout.addView(restaurantButtons)
        }
    }


    fun onClick(view: View) {
        val intent = Intent(this, SearchRestaurant::class.java)
        startActivity(intent)
    }
}