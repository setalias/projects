package com.example.lettuceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.Button
import android.widget.TextView
import androidx.core.text.bold
import com.lettuceapp.data.RestaurantDomain
import com.lettuceapp.data.RestaurantDto
import com.lettuceapp.data.RestaurantScheduleDomain
import com.lettuceapp.data.RestaurantScheduleDto

class RestaurantDisplay : AppCompatActivity() {

    private val restaurantDomain = RestaurantDomain(this)
    private val restaurantScheduleDomain = RestaurantScheduleDomain(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_display)

        val restInfo = getRestInfo()
        val restScheduleInfo = getRestScheduleInfo()
        val emailFromLastActivity = intent.getStringExtra("email")

        // restaurant name displayed
        val restDisplayName = findViewById<TextView>(R.id.displayRestName)
        restDisplayName.text = restInfo[0].restaurantName

        // restaurant food type
        val restDisplayType = findViewById<TextView>(R.id.displayRestType)
        restDisplayType.text = restInfo[0].foodType

        // restaurant street address + zipcode
        val restStreetAddress = findViewById<TextView>(R.id.RestaurantStreet)
        restStreetAddress.text = restInfo[0].address
        val restZipcode = findViewById<TextView>(R.id.RestaurantZipcode)
        restZipcode.text = restInfo[0].zipcode.toString()

        // restaurant phone number
        val restDisplayPhone = findViewById<TextView>(R.id.displayRestPhone)
        restDisplayPhone.text = restInfo[0].phoneNumber

        // restaurant times displayed
        val restMondaySchedule = findViewById<TextView>(R.id.displayMondayRestSchedule)
        if (restScheduleInfo?.mondayOpeningTime != null && restScheduleInfo?.mondayClosingTime != null) {
            restMondaySchedule.text = "Monday : " + restScheduleInfo.mondayOpeningTime + " - " + restScheduleInfo.mondayClosingTime
        } else{
            restMondaySchedule.text = "Monday : Closed"
        }

        val restTuesdaySchedule = findViewById<TextView>(R.id.displayTuesdayRestSchedule)
        if (restScheduleInfo?.tuesdayOpeningTime != null && restScheduleInfo?.tuesdayClosingTime != null) {
            restTuesdaySchedule.text = "Tuesday : " + restScheduleInfo.tuesdayOpeningTime + " - " + restScheduleInfo.tuesdayClosingTime
        } else{
            restTuesdaySchedule.text = "Tuesday : Closed"
        }

        val restWednesdaySchedule = findViewById<TextView>(R.id.displayWednesdayRestSchedule)
        if (restScheduleInfo?.wednesdayOpeningTime != null && restScheduleInfo?.wednesdayClosingTime != null) {
            restWednesdaySchedule.text = "Wednesday : " + restScheduleInfo.wednesdayOpeningTime + " - " + restScheduleInfo.wednesdayClosingTime
        } else{
            restWednesdaySchedule.text = "Wednesday : Closed"
        }

        val restThursdaySchedule = findViewById<TextView>(R.id.displayThursdayRestSchedule)
        if (restScheduleInfo?.thursdayOpeningTime != null && restScheduleInfo?.thursdayClosingTime != null) {
            restThursdaySchedule.text = "Thursday : " + restScheduleInfo.thursdayOpeningTime + " - " + restScheduleInfo.thursdayClosingTime
        } else{
            restThursdaySchedule.text = "Thursday : Closed"
        }

        val restFridaySchedule = findViewById<TextView>(R.id.displayFridayRestSchedule)
        if (restScheduleInfo?.fridayOpeningTime != null && restScheduleInfo?.fridayClosingTime != null) {
            restFridaySchedule.text = "Friday : " + restScheduleInfo.fridayOpeningTime + " - " + restScheduleInfo.fridayClosingTime
        } else{
            restFridaySchedule.text = "Friday : Closed"
        }

        val restSaturdaySchedule = findViewById<TextView>(R.id.displaySaturdayRestSchedule)
        if (restScheduleInfo?.saturdayOpeningTime != null && restScheduleInfo?.saturdayClosingTime != null) {
            restSaturdaySchedule.text = "Saturday : " + restScheduleInfo.saturdayOpeningTime + " - " + restScheduleInfo.saturdayClosingTime
        } else{
            restSaturdaySchedule.text = "Saturday : Closed"
        }

        val restSundaySchedule = findViewById<TextView>(R.id.displaySundayRestSchedule)
        if (restScheduleInfo?.sundayOpeningTime != null && restScheduleInfo?.sundayClosingTime != null) {
            restSundaySchedule.text = "Sunday : " + restScheduleInfo.sundayOpeningTime + " - " + restScheduleInfo.sundayClosingTime
        } else{
            restSundaySchedule.text = "Sunday : Closed"
        }

        // button "Make a Reservation"
        val displayCreateReservation = findViewById<Button>(R.id.displayCreateReservation)
        displayCreateReservation.setOnClickListener(){
            val intent = Intent(this, CreateReservation::class.java)
            //val intent = Intent(this, CustomerTableLayout::class.java)
            val intentBundle = Bundle()
            intentBundle.putString("email",emailFromLastActivity) // pass customer email
            intentBundle.putInt("restaurantID", restInfo[0].id) // pass restaurant ID
            intent.putExtras(intentBundle)
            startActivity(intent)
        }
    }

    private fun getRestInfo() : List<RestaurantDto> {
        val restID = intent.getIntExtra("restaurantID", 0)
        return restaurantDomain.getRestaurantById(restID)
    }

    private fun getRestScheduleInfo() : RestaurantScheduleDto? {
        val restID = intent.getIntExtra("restaurantID", 0)
        return restaurantScheduleDomain.getRestaurantScheduleByRestaurantID(restID)
    }
}