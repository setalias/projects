package com.example.lettuceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.lettuceapp.data.CustomerDto
import com.lettuceapp.data.RestaurantDomain
import com.lettuceapp.data.RestaurantDto

class EditRestaurantProfile : AppCompatActivity() {
    private val restaurantDomain = RestaurantDomain(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_restaurant_profile)

        val email = intent.getStringExtra("email")
        val data = restaurantDomain.getRestaurantByEmail(email!!)

        setStateSpinner()
        loadRestaurant(data)

        val updateRestaurantProfile = findViewById<Button>(R.id.updateProfileData)
        updateRestaurantProfile.setOnClickListener{
            val result = updateProfile(data[0].id)
            if(result) {
                val intent = Intent(this, RestaurantDashboard::class.java)
                intent.putExtra("email", data[0].email)
                startActivity(intent)
            }
        }
    }

    private fun updateProfile(restaurantID : Int) : Boolean {
        val context = this
        val editEmail = findViewById<EditText>(R.id.editEmail)
        val editPassword = findViewById<EditText>(R.id.editPassword)
        val editRestaurantName = findViewById<EditText>(R.id.editRestaurantName)
        val editContactFirstName = findViewById<EditText>(R.id.editContactFirstName)
        val editContactLastName = findViewById<EditText>(R.id.editContactLastName)
        val editFoodType = findViewById<EditText>(R.id.editFoodType)
        val editPhoneNumber = findViewById<EditText>(R.id.editPhoneNumber)
        val editAddress = findViewById<EditText>(R.id.editAddress)
        val editState = findViewById<Spinner>(R.id.state_spinner)
        val editZipcode = findViewById<EditText>(R.id.editZipCode)

        if (
                editEmail.text.toString().isNotEmpty() &&
                editPassword.text.toString().isNotEmpty() &&
                editContactFirstName.text.toString().isNotEmpty() &&
                editContactLastName.text.toString().isNotEmpty() &&
                editFoodType.text.toString().isNotEmpty() &&
                editPhoneNumber.text.toString().isNotEmpty() &&
                editAddress.text.toString().isNotEmpty() &&
                editState.selectedItem.toString().isNotEmpty() &&
                editZipcode.text.toString().isNotEmpty()
        ) {
                    val restaurantDto = RestaurantDto(
                            restaurantID,
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

                    restaurantDomain.updateRestaurant(restaurantDto)
                    clearField()
                    return true
        }
        else {
            Toast.makeText(context, "All fields are required to be filled in.", Toast.LENGTH_SHORT).show()
            return false
        }
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

    private fun clearField() {
        val editEmail = findViewById<EditText>(R.id.editEmail)
        val editPassword = findViewById<EditText>(R.id.editPassword)
        val editRestaurantName = findViewById<EditText>(R.id.editRestaurantName)
        val editContactFirstName = findViewById<EditText>(R.id.editContactFirstName)
        val editContactLastName = findViewById<EditText>(R.id.editContactLastName)
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
        editState.adapter = null
        editZipcode.text.clear()
    }

    private fun loadRestaurant(restaurant: MutableList<RestaurantDto>){
        try {
            val stateArray = resources.getStringArray(R.array.states_array)
            var stateArrayIndex = stateArray.indexOf(restaurant[0].state)
            if(stateArrayIndex == -1){
                throw Exception("State does not exist in the list of known states!")
            }

            val editEmail = findViewById<EditText>(R.id.editEmail)
            val editPassword = findViewById<EditText>(R.id.editPassword)
            val editRestaurantName = findViewById<EditText>(R.id.editRestaurantName)
            val editContactFirstName = findViewById<EditText>(R.id.editContactFirstName)
            val editContactLastName = findViewById<EditText>(R.id.editContactLastName)
            val editFoodType = findViewById<EditText>(R.id.editFoodType)
            val editPhoneNumber = findViewById<EditText>(R.id.editPhoneNumber)
            val editAddress = findViewById<EditText>(R.id.editAddress)
            val editState = findViewById<Spinner>(R.id.state_spinner)
            val editZipcode = findViewById<EditText>(R.id.editZipCode)

            editEmail.setText(restaurant[0].email)
            editPassword.setText(restaurant[0].password)
            editRestaurantName.setText(restaurant[0].restaurantName)
            editContactFirstName.setText(restaurant[0].contactFirstName)
            editContactLastName.setText(restaurant[0].contactLastName)
            editFoodType.setText(restaurant[0].foodType)
            editPhoneNumber.setText(restaurant[0].phoneNumber)
            editAddress.setText(restaurant[0].address)
            editState.setSelection(stateArrayIndex)
            editZipcode.setText(restaurant[0].zipcode.toString())
        } catch (e : Exception){
            print(e)
        }
    }
}