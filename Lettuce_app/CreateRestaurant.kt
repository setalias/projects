package com.example.lettuceapp

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.lettuceapp.data.*
import java.text.SimpleDateFormat
import java.util.*


class CreateRestaurant : AppCompatActivity(), User {
    private val restaurantDomain = RestaurantDomain(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_restaurant)

        val createRestaurantProfile = findViewById<Button>(R.id.createProfile)
        setStateSpinner()
        // onclick for insert btn
        createRestaurantProfile.setOnClickListener {
            var isSuccess = createProfile()
            if(isSuccess) {
                val intent = Intent(this, MainPage::class.java)
                startActivity(intent)
            }
        }
    }

    override fun createProfile() : Boolean {
        val context = this
        val editEmail = findViewById<EditText>(R.id.editEmail)
        val editPassword = findViewById<EditText>(R.id.editPassword)
        val editRestaurantName = findViewById<EditText>(R.id.editRestaurantName)
        val editContactFirstName = findViewById<EditText>(R.id.editContactFirstName)
        val editContactLastName = findViewById<EditText>(R.id.editContactFirstName)
        val editFoodType = findViewById<EditText>(R.id.editFoodType)
        val editPhoneNumber = findViewById<EditText>(R.id.editPhoneNumber)
        val editAddress = findViewById<EditText>(R.id.editAddress)
        val editState = findViewById<Spinner>(R.id.state_spinner)
        val editZipcode = findViewById<EditText>(R.id.editZipCode)

        if (editEmail.text.toString().isNotEmpty() &&
            editPassword.text.toString().isNotEmpty() &&
            editContactFirstName.text.toString().isNotEmpty() &&
            editContactLastName.text.toString().isNotEmpty() &&
            editFoodType.text.toString().isNotEmpty() &&
            editPhoneNumber.text.toString().isNotEmpty() &&
            editAddress.text.toString().isNotEmpty() &&
            editState.selectedItem.toString().isNotEmpty() &&
            editZipcode.text.toString().isNotEmpty()
        ) {
            // enter restaurant
            when(isDuplicateUser(editEmail.text.toString())){
                1 -> Toast.makeText(context, "Email already exists!", Toast.LENGTH_SHORT).show()
                else -> {
                    val restaurant = RestaurantDto(
                        0,
                        editEmail.text.toString(),
                        editPassword.text.toString(),
                        editRestaurantName.text.toString(),
                        editContactFirstName.text.toString(),
                        editContactLastName.text.toString(),
                        editFoodType.text.toString(),
                        editPhoneNumber.text.toString(),
                        editAddress.text.toString(),
                        editState.selectedItem.toString(),
                        editZipcode.text.toString().toInt()
                    )
                    restaurantDomain.createRestaurant(restaurant)
                    clearField()
                    return true
                }
            }
        }
        else {
            Toast.makeText(context, "All fields are required to be filled in.", Toast.LENGTH_SHORT).show()
            return false
        }
        return false
    }

    override fun clearField() {
        val editEmail = findViewById<EditText>(R.id.editEmail)
        val editPassword = findViewById<EditText>(R.id.editPassword)
        val editRestaurantName = findViewById<EditText>(R.id.editRestaurantName)
        val editContactFirstName = findViewById<EditText>(R.id.editContactFirstName)
        val editContactLastName = findViewById<EditText>(R.id.editContactFirstName)
        val editFoodType = findViewById<EditText>(R.id.editFoodType)
        val editPhoneNumber = findViewById<EditText>(R.id.editPhoneNumber)
        val editAddress = findViewById<EditText>(R.id.editAddress)
        val editState = findViewById<Spinner>(R.id.state_spinner)
        val editZipcode = findViewById<EditText>(R.id.editZipCode)
        editEmail.text.clear()
        editPassword.text.clear()
        editRestaurantName.text.clear()
        editContactFirstName.text.clear()
        editContactLastName.text.clear()
        editFoodType.text.clear()
        editPhoneNumber.text.clear()
        editAddress.text.clear()
        editState.setAdapter(null)
        editZipcode.text.clear()
    }

    private fun setStateSpinner() {
        val spinner: Spinner = findViewById(R.id.state_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.states_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    override fun isDuplicateUser(email : String) : Int{
        var dataEmail = restaurantDomain.getRestaurantByEmail(email)
        return if(dataEmail.count() > 0){
            1
        } else{
            0
        }
    }

}