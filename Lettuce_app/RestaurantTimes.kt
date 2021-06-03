package com.example.lettuceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.lettuceapp.data.CustomerDto
import com.lettuceapp.data.RestaurantScheduleDomain
import com.lettuceapp.data.RestaurantScheduleDto
import com.lettuceapp.data.SaturdayOpeningTime

class RestaurantTimes : AppCompatActivity() {
    private val restaurantScheduleDomain = RestaurantScheduleDomain(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_times)

        val restaurantID = intent.getIntExtra("restaurantID", 0)
        val restaurant = restaurantScheduleDomain.getRestaurantScheduleByRestaurantID(restaurantID)
        setCheckbox()
        setTimeSpinners()
        loadRestaurantSchedule(restaurant)

        val saveBtn = findViewById<Button>(R.id.createRestaurantSchedule)

        saveBtn.setOnClickListener {
            val result = restaurantScheduleDomain.getRestaurantScheduleByRestaurantID(restaurantID)
            val restaurantSchedule = buildRestaurantSchedule(restaurantID)
            if(result == null){
                restaurantScheduleDomain.createRestaurantSchedule(restaurantSchedule)
            }
            else {
                restaurantScheduleDomain.updateRestaurantSchedule(restaurantSchedule)
            }
        }
    }

    private fun setCheckbox(){
        val mondayCheckBox = findViewById<CheckBox>(R.id.mondayCheckbox)
        val tuesdayCheckBox = findViewById<CheckBox>(R.id.tuesdayCheckbox)
        val wednesdayCheckBox = findViewById<CheckBox>(R.id.wednesdayCheckbox)
        val thursdayCheckBox = findViewById<CheckBox>(R.id.thursdayCheckbox)
        val fridayCheckBox = findViewById<CheckBox>(R.id.fridayCheckbox)
        val saturdayCheckBox = findViewById<CheckBox>(R.id.saturdayCheckbox)
        val sundayCheckBox = findViewById<CheckBox>(R.id.sundayCheckbox)
        val mondayOpenSpinner = findViewById<Spinner>(R.id.opening_time_monday_spinner)
        mondayOpenSpinner.isEnabled = false
        val mondayCloseSpinner = findViewById<Spinner>(R.id.closing_time_monday_spinner)
        mondayCloseSpinner.isEnabled = false
        val tuesdayOpenSpinner = findViewById<Spinner>(R.id.opening_time_tuesday_spinner)
        tuesdayOpenSpinner.isEnabled = false
        val tuesdayCloseSpinner = findViewById<Spinner>(R.id.closing_time_tuesday_spinner)
        tuesdayCloseSpinner.isEnabled = false
        val wednesdayOpenSpinner = findViewById<Spinner>(R.id.opening_time_wednesday_spinner)
        wednesdayOpenSpinner.isEnabled = false
        val wednesdayCloseSpinner = findViewById<Spinner>(R.id.closing_time_wednesday_spinner)
        wednesdayCloseSpinner.isEnabled = false
        val thursdayOpenSpinner = findViewById<Spinner>(R.id.opening_time_thursday_spinner)
        thursdayOpenSpinner.isEnabled = false
        val thursdayCloseSpinner = findViewById<Spinner>(R.id.closing_time_thursday_spinner)
        thursdayCloseSpinner.isEnabled = false
        val fridayOpenSpinner = findViewById<Spinner>(R.id.opening_time_friday_spinner)
        fridayOpenSpinner.isEnabled = false
        val fridayCloseSpinner = findViewById<Spinner>(R.id.closing_time_friday_spinner)
        fridayCloseSpinner.isEnabled = false
        val saturdayOpenSpinner = findViewById<Spinner>(R.id.opening_time_saturday_spinner)
        saturdayOpenSpinner.isEnabled = false
        val saturdayCloseSpinner = findViewById<Spinner>(R.id.closing_time_saturday_spinner)
        saturdayCloseSpinner.isEnabled = false
        val sundayOpenSpinner = findViewById<Spinner>(R.id.opening_time_sunday_spinner)
        sundayOpenSpinner.isEnabled = false
        val sundayCloseSpinner = findViewById<Spinner>(R.id.closing_time_sunday_spinner)
        sundayCloseSpinner.isEnabled = false

        mondayCheckBox.setOnClickListener {
            if(mondayCheckBox.isChecked){
                mondayOpenSpinner.isEnabled = true
                mondayCloseSpinner.isEnabled = true
            }
            else if(!mondayCheckBox.isChecked) {
                mondayOpenSpinner.isEnabled = false
                mondayCloseSpinner.isEnabled = false
            }
        }

        tuesdayCheckBox.setOnClickListener {
            if(tuesdayCheckBox.isChecked){
                tuesdayOpenSpinner.isEnabled = true
                tuesdayCloseSpinner.isEnabled = true
            }
            else if(!tuesdayCheckBox.isChecked) {
                tuesdayOpenSpinner.isEnabled = false
                tuesdayCloseSpinner.isEnabled = false
            }
        }

        wednesdayCheckBox.setOnClickListener {
            if(wednesdayCheckBox.isChecked){
                wednesdayOpenSpinner.isEnabled = true
                wednesdayCloseSpinner.isEnabled = true
            }
            else if(!wednesdayCheckBox.isChecked) {
                wednesdayOpenSpinner.isEnabled = false
                wednesdayCloseSpinner.isEnabled = false
            }
        }

        thursdayCheckBox.setOnClickListener {
            if(thursdayCheckBox.isChecked){
                thursdayOpenSpinner.isEnabled = true
                thursdayCloseSpinner.isEnabled = true
            }
            else if(!thursdayCheckBox.isChecked) {
                thursdayOpenSpinner.isEnabled = false
                thursdayCloseSpinner.isEnabled = false
            }
        }

        fridayCheckBox.setOnClickListener {
            if(fridayCheckBox.isChecked){
                fridayOpenSpinner.isEnabled = true
                fridayCloseSpinner.isEnabled = true
            }
            else if(!fridayCheckBox.isChecked) {
                fridayOpenSpinner.isEnabled = false
                fridayCloseSpinner.isEnabled = false
            }
        }

        saturdayCheckBox.setOnClickListener {
            if(saturdayCheckBox.isChecked){
                saturdayOpenSpinner.isEnabled = true
                saturdayCloseSpinner.isEnabled = true
            }
            else if(!saturdayCheckBox.isChecked) {
                saturdayOpenSpinner.isEnabled = false
                saturdayCloseSpinner.isEnabled = false
            }
        }

        sundayCheckBox.setOnClickListener {
            if(sundayCheckBox.isChecked){
                sundayOpenSpinner.isEnabled = true
                sundayCloseSpinner.isEnabled = true
            }
            else if(!sundayCheckBox.isChecked) {
                sundayOpenSpinner.isEnabled = false
                sundayCloseSpinner.isEnabled = false
            }
        }
    }

    private fun setTimeSpinners(){
        val mondayOpenSpinner = findViewById<Spinner>(R.id.opening_time_monday_spinner)
        val mondayCloseSpinner = findViewById<Spinner>(R.id.closing_time_monday_spinner)
        val tuesdayOpenSpinner = findViewById<Spinner>(R.id.opening_time_tuesday_spinner)
        val tuesdayCloseSpinner = findViewById<Spinner>(R.id.closing_time_tuesday_spinner)
        val wednesdayOpenSpinner = findViewById<Spinner>(R.id.opening_time_wednesday_spinner)
        val wednesdayCloseSpinner = findViewById<Spinner>(R.id.closing_time_wednesday_spinner)
        val thursdayOpenSpinner = findViewById<Spinner>(R.id.opening_time_thursday_spinner)
        val thursdayCloseSpinner = findViewById<Spinner>(R.id.closing_time_thursday_spinner)
        val fridayOpenSpinner = findViewById<Spinner>(R.id.opening_time_friday_spinner)
        val fridayCloseSpinner = findViewById<Spinner>(R.id.closing_time_friday_spinner)
        val saturdayOpenSpinner = findViewById<Spinner>(R.id.opening_time_saturday_spinner)
        val saturdayCloseSpinner = findViewById<Spinner>(R.id.closing_time_saturday_spinner)
        val sundayOpenSpinner = findViewById<Spinner>(R.id.opening_time_sunday_spinner)
        val sundayCloseSpinner = findViewById<Spinner>(R.id.closing_time_sunday_spinner)

        createTimeSpinner(mondayOpenSpinner)
        createTimeSpinner(mondayCloseSpinner)
        createTimeSpinner(tuesdayOpenSpinner)
        createTimeSpinner(tuesdayCloseSpinner)
        createTimeSpinner(wednesdayOpenSpinner)
        createTimeSpinner(wednesdayCloseSpinner)
        createTimeSpinner(thursdayOpenSpinner)
        createTimeSpinner(thursdayCloseSpinner)
        createTimeSpinner(fridayOpenSpinner)
        createTimeSpinner(fridayCloseSpinner)
        createTimeSpinner(saturdayOpenSpinner)
        createTimeSpinner(saturdayCloseSpinner)
        createTimeSpinner(sundayOpenSpinner)
        createTimeSpinner(sundayCloseSpinner)
    }

    private fun createTimeSpinner(spinner : Spinner){
        ArrayAdapter.createFromResource(
            this,
            R.array.times_array, // this array is being grabbed from strings.xml in values folder
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    private fun buildRestaurantSchedule(restaurantID : Int) : RestaurantScheduleDto{
        val mondayOpenSpinner = findViewById<Spinner>(R.id.opening_time_monday_spinner)
        val mondayCloseSpinner = findViewById<Spinner>(R.id.closing_time_monday_spinner)
        val tuesdayOpenSpinner = findViewById<Spinner>(R.id.opening_time_tuesday_spinner)
        val tuesdayCloseSpinner = findViewById<Spinner>(R.id.closing_time_tuesday_spinner)
        val wednesdayOpenSpinner = findViewById<Spinner>(R.id.opening_time_wednesday_spinner)
        val wednesdayCloseSpinner = findViewById<Spinner>(R.id.closing_time_wednesday_spinner)
        val thursdayOpenSpinner = findViewById<Spinner>(R.id.opening_time_thursday_spinner)
        val thursdayCloseSpinner = findViewById<Spinner>(R.id.closing_time_thursday_spinner)
        val fridayOpenSpinner = findViewById<Spinner>(R.id.opening_time_friday_spinner)
        val fridayCloseSpinner = findViewById<Spinner>(R.id.closing_time_friday_spinner)
        val saturdayOpenSpinner = findViewById<Spinner>(R.id.opening_time_saturday_spinner)
        val saturdayCloseSpinner = findViewById<Spinner>(R.id.closing_time_saturday_spinner)
        val sundayOpenSpinner = findViewById<Spinner>(R.id.opening_time_sunday_spinner)
        val sundayCloseSpinner = findViewById<Spinner>(R.id.closing_time_sunday_spinner)

        val mondayCheckBox = findViewById<CheckBox>(R.id.mondayCheckbox)
        val tuesdayCheckBox = findViewById<CheckBox>(R.id.tuesdayCheckbox)
        val wednesdayCheckBox = findViewById<CheckBox>(R.id.wednesdayCheckbox)
        val thursdayCheckBox = findViewById<CheckBox>(R.id.thursdayCheckbox)
        val fridayCheckBox = findViewById<CheckBox>(R.id.fridayCheckbox)
        val saturdayCheckBox = findViewById<CheckBox>(R.id.saturdayCheckbox)
        val sundayCheckBox = findViewById<CheckBox>(R.id.sundayCheckbox)

        var mondayOpenVal : String? = null
        var mondayCloseVal : String? = null
        var tuesdayOpenVal : String? = null
        var tuesdayCloseVal : String? = null
        var wednesdayOpenVal : String? = null
        var wednesdayCloseVal : String? = null
        var thursdayOpenVal : String? = null
        var thursdayCloseVal : String? = null
        var fridayOpenVal : String? = null
        var fridayCloseVal : String? = null
        var saturdayOpenVal : String? = null
        var saturdayCloseVal : String? = null
        var sundayOpenVal : String? = null
        var sundayCloseVal : String? = null

        if(mondayCheckBox.isChecked){
            mondayOpenVal = mondayOpenSpinner.selectedItem.toString()
            mondayCloseVal = mondayCloseSpinner.selectedItem.toString()
        }

        if(tuesdayCheckBox.isChecked){
            tuesdayOpenVal = tuesdayOpenSpinner.selectedItem.toString()
            tuesdayCloseVal = tuesdayCloseSpinner.selectedItem.toString()
        }

        if(wednesdayCheckBox.isChecked){
            wednesdayOpenVal = wednesdayOpenSpinner.selectedItem.toString()
            wednesdayCloseVal = wednesdayCloseSpinner.selectedItem.toString()
        }

        if(thursdayCheckBox.isChecked){
            thursdayOpenVal = thursdayOpenSpinner.selectedItem.toString()
            thursdayCloseVal = thursdayCloseSpinner.selectedItem.toString()
        }

        if(fridayCheckBox.isChecked){
            fridayOpenVal = fridayOpenSpinner.selectedItem.toString()
            fridayCloseVal = fridayCloseSpinner.selectedItem.toString()
        }

        if(saturdayCheckBox.isChecked){
            saturdayOpenVal = saturdayOpenSpinner.selectedItem.toString()
            saturdayCloseVal = saturdayCloseSpinner.selectedItem.toString()
        }

        if(sundayCheckBox.isChecked){
            sundayOpenVal = sundayOpenSpinner.selectedItem.toString()
            sundayCloseVal = sundayCloseSpinner.selectedItem.toString()
        }

        return RestaurantScheduleDto(
            0,restaurantID,
            mondayOpenVal,mondayCloseVal,
            tuesdayOpenVal,tuesdayCloseVal,
            wednesdayOpenVal,wednesdayCloseVal,
            thursdayOpenVal,thursdayCloseVal,
            fridayOpenVal,fridayCloseVal,
            saturdayOpenVal,saturdayCloseVal,
            sundayOpenVal,sundayCloseVal
        )
    }

    private fun loadRestaurantSchedule(restaurantSchedule: RestaurantScheduleDto?){
        if(restaurantSchedule != null) {
            try {
                val timeArray = resources.getStringArray(R.array.times_array)

                val mondayOpenState = timeArray.indexOf(restaurantSchedule.mondayOpeningTime)
                val mondayCloseState = timeArray.indexOf(restaurantSchedule.mondayClosingTime)
                val tuesdayOpenState = timeArray.indexOf(restaurantSchedule.tuesdayOpeningTime)
                val tuesdayCloseState = timeArray.indexOf(restaurantSchedule.tuesdayClosingTime)
                val wednesdayOpenState = timeArray.indexOf(restaurantSchedule.wednesdayOpeningTime)
                val wednesdayCloseState = timeArray.indexOf(restaurantSchedule.wednesdayClosingTime)
                val thursdayOpenState = timeArray.indexOf(restaurantSchedule.thursdayOpeningTime)
                val thursdayCloseState = timeArray.indexOf(restaurantSchedule.thursdayClosingTime)
                val fridayOpenState = timeArray.indexOf(restaurantSchedule.fridayOpeningTime)
                val fridayCloseState = timeArray.indexOf(restaurantSchedule.fridayClosingTime)
                val saturdayOpenState = timeArray.indexOf(restaurantSchedule.saturdayOpeningTime)
                val saturdayCloseState = timeArray.indexOf(restaurantSchedule.saturdayClosingTime)
                val sundayOpenState = timeArray.indexOf(restaurantSchedule.sundayOpeningTime)
                val sundayCloseState = timeArray.indexOf(restaurantSchedule.sundayClosingTime)

                if(mondayOpenState == -1 || mondayCloseState == -1 || tuesdayOpenState == -1 || tuesdayCloseState == -1 || wednesdayOpenState == -1 || wednesdayCloseState == -1
                        || thursdayOpenState == -1 || thursdayCloseState == -1 || fridayOpenState == -1 || fridayCloseState == -1 || saturdayOpenState == -1 || saturdayCloseState == -1
                        || sundayOpenState == -1 || sundayCloseState == -1 ){
                    throw Exception("Time does not exist in current list of times!")
                }

                val mondayOpenSpinner = findViewById<Spinner>(R.id.opening_time_monday_spinner)
                val mondayCloseSpinner = findViewById<Spinner>(R.id.closing_time_monday_spinner)
                val tuesdayOpenSpinner = findViewById<Spinner>(R.id.opening_time_tuesday_spinner)
                val tuesdayCloseSpinner = findViewById<Spinner>(R.id.closing_time_tuesday_spinner)
                val wednesdayOpenSpinner = findViewById<Spinner>(R.id.opening_time_wednesday_spinner)
                val wednesdayCloseSpinner = findViewById<Spinner>(R.id.closing_time_wednesday_spinner)
                val thursdayOpenSpinner = findViewById<Spinner>(R.id.opening_time_thursday_spinner)
                val thursdayCloseSpinner = findViewById<Spinner>(R.id.closing_time_thursday_spinner)
                val fridayOpenSpinner = findViewById<Spinner>(R.id.opening_time_friday_spinner)
                val fridayCloseSpinner = findViewById<Spinner>(R.id.closing_time_friday_spinner)
                val saturdayOpenSpinner = findViewById<Spinner>(R.id.opening_time_saturday_spinner)
                val saturdayCloseSpinner = findViewById<Spinner>(R.id.closing_time_saturday_spinner)
                val sundayOpenSpinner = findViewById<Spinner>(R.id.opening_time_sunday_spinner)
                val sundayCloseSpinner = findViewById<Spinner>(R.id.closing_time_sunday_spinner)

                val mondayCheckBox = findViewById<CheckBox>(R.id.mondayCheckbox)
                val tuesdayCheckBox = findViewById<CheckBox>(R.id.tuesdayCheckbox)
                val wednesdayCheckBox = findViewById<CheckBox>(R.id.wednesdayCheckbox)
                val thursdayCheckBox = findViewById<CheckBox>(R.id.thursdayCheckbox)
                val fridayCheckBox = findViewById<CheckBox>(R.id.fridayCheckbox)
                val saturdayCheckBox = findViewById<CheckBox>(R.id.saturdayCheckbox)
                val sundayCheckBox = findViewById<CheckBox>(R.id.sundayCheckbox)

                if (restaurantSchedule.mondayOpeningTime != null && restaurantSchedule.mondayClosingTime != null) {
                    mondayOpenSpinner.setSelection(mondayOpenState)
                    mondayCloseSpinner.setSelection(mondayCloseState)
                    mondayOpenSpinner.isEnabled = true
                    mondayCloseSpinner.isEnabled = true
                    mondayCheckBox.isChecked = true
                } else {
                    mondayOpenSpinner.setSelection(0)
                    mondayCloseSpinner.setSelection(0)
                }

                if (restaurantSchedule.tuesdayOpeningTime != null && restaurantSchedule.tuesdayClosingTime != null) {
                    tuesdayOpenSpinner.setSelection(tuesdayOpenState)
                    tuesdayCloseSpinner.setSelection(tuesdayCloseState)
                    tuesdayOpenSpinner.isEnabled = true
                    tuesdayCloseSpinner.isEnabled = true
                    tuesdayCheckBox.isChecked = true
                } else {
                    tuesdayOpenSpinner.setSelection(0)
                    tuesdayOpenSpinner.setSelection(0)
                }

                if (restaurantSchedule.wednesdayOpeningTime != null && restaurantSchedule.wednesdayClosingTime != null) {
                    wednesdayOpenSpinner.setSelection(wednesdayOpenState)
                    wednesdayCloseSpinner.setSelection(wednesdayCloseState)
                    wednesdayOpenSpinner.isEnabled = true
                    wednesdayCloseSpinner.isEnabled = true
                    wednesdayCheckBox.isChecked = true
                } else {
                    wednesdayOpenSpinner.setSelection(0)
                    wednesdayCloseSpinner.setSelection(0)
                }

                if (restaurantSchedule.thursdayOpeningTime != null && restaurantSchedule.thursdayClosingTime != null) {
                    thursdayOpenSpinner.setSelection(thursdayOpenState)
                    thursdayCloseSpinner.setSelection(thursdayCloseState)
                    thursdayOpenSpinner.isEnabled = true
                    thursdayCloseSpinner.isEnabled = true
                    thursdayCheckBox.isChecked = true
                } else {
                    thursdayOpenSpinner.setSelection(0)
                    thursdayCloseSpinner.setSelection(0)
                }

                if (restaurantSchedule.fridayOpeningTime != null && restaurantSchedule.fridayClosingTime != null) {
                    fridayOpenSpinner.setSelection(fridayOpenState)
                    fridayCloseSpinner.setSelection(fridayCloseState)
                    fridayOpenSpinner.isEnabled = true
                    fridayCloseSpinner.isEnabled = true
                    fridayCheckBox.isChecked = true
                } else {
                    fridayOpenSpinner.setSelection(0)
                    fridayCloseSpinner.setSelection(0)
                }

                if (restaurantSchedule.saturdayOpeningTime != null && restaurantSchedule.saturdayClosingTime != null) {
                    saturdayOpenSpinner.setSelection(saturdayOpenState)
                    saturdayCloseSpinner.setSelection(saturdayCloseState)
                    saturdayOpenSpinner.isEnabled = true
                    saturdayCloseSpinner.isEnabled = true
                    saturdayCheckBox.isChecked = true
                } else {
                    saturdayOpenSpinner.setSelection(0)
                    saturdayCloseSpinner.setSelection(0)
                }

                if (restaurantSchedule.sundayOpeningTime != null && restaurantSchedule.sundayClosingTime != null) {
                    sundayOpenSpinner.setSelection(sundayOpenState)
                    sundayCloseSpinner.setSelection(sundayCloseState)
                    sundayOpenSpinner.isEnabled = true
                    sundayCloseSpinner.isEnabled = true
                    sundayCheckBox.isChecked = true
                } else {
                    sundayOpenSpinner.setSelection(0)
                    sundayCloseSpinner.setSelection(0)
                }
            }
            catch(e : Exception){
                print(e)
            }
        }
    }
}