package com.example.lettuceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.lettuceapp.data.*

class RestaurantDashboard : AppCompatActivity() {
    private val restaurantDomain = RestaurantDomain(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_dashboard)

        val restaurantList = getLoggedInRestaurant()
        val restaurantNameText = findViewById<TextView>(R.id.restaurantName)
        val emailFromLastActivity = intent.getStringExtra("email")
        restaurantNameText.text = restaurantList[0].restaurantName

        val createTableLayout = findViewById<Button>(R.id.createTableLayout)
        createTableLayout.setOnClickListener(){
            val intent = Intent(this, RestaurantTableLayout::class.java)
            intent.putExtra("restaurantID",restaurantList[0].id)
            startActivity(intent)
        }

        val modifyRestaurantProfile = findViewById<Button>(R.id.modifyRestaurantProfile)
        modifyRestaurantProfile.setOnClickListener(){
            val intent = Intent(this, EditRestaurantProfile::class.java)
            intent.putExtra("email", emailFromLastActivity)
            startActivity(intent)
        }

        val scheduleTime = findViewById<Button>(R.id.modifySchedule)
        scheduleTime.setOnClickListener {
            val intent = Intent(this, RestaurantTimes::class.java)
            intent.putExtra("restaurantID", restaurantList[0].id)
            startActivity(intent)
        }
    }

    private fun getLoggedInRestaurant() : List<RestaurantDto> {
        val email = intent.getStringExtra("email")
        return restaurantDomain.getRestaurantByEmail(email.toString())
    }

    fun onClick(view: View) {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }
}