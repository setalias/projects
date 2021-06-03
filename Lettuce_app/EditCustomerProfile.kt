package com.example.lettuceapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.lettuceapp.data.CustomerDto
import com.lettuceapp.data.CustomerDomain
import java.text.SimpleDateFormat
import java.util.*


class EditCustomerProfile : AppCompatActivity() {
    private val customerDomain = CustomerDomain(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_customer_profile)

        //Populates current users data for display
        val email = intent.getStringExtra("email")
        val data = customerDomain.getCustomerByEmail(email.toString())

        setDatePicker() // calendar
        setStateSpinner() // drop down
        loadCustomer(data)

        val updateCustomerProfile = findViewById<Button>(R.id.updateProfileData)
        updateCustomerProfile.setOnClickListener {
            val result = updateProfile(data[0].id)
            if(result) {
                val intent = Intent(this, CustomerDashboard::class.java)
                intent.putExtra("email", data[0].email)
                startActivity(intent)
            }
        }
    }

    private fun updateProfile(customerId : Int) : Boolean {
        val context = this
        val editEmail = findViewById<EditText>(R.id.editEmail)
        val editPassword = findViewById<EditText>(R.id.editPassword)
        val editFirstName = findViewById<EditText>(R.id.editFirstName)
        val editLastName = findViewById<EditText>(R.id.editLastName)
        val editDob = findViewById<EditText>(R.id.editDOB)
        val editPhoneNumber = findViewById<EditText>(R.id.editPhoneNumber)
        val editAddress = findViewById<EditText>(R.id.editAddress)
        val editState = findViewById<Spinner>(R.id.state_spinner)
        val editZipcode = findViewById<EditText>(R.id.editZipCode)

        // validation
        if (
                editEmail.text.toString().isNotEmpty() &&
                editPassword.text.toString().isNotEmpty() &&
                editFirstName.text.toString().isNotEmpty() &&
                editLastName.text.toString().isNotEmpty() &&
                editDob.text.toString().isNotEmpty() &&
                editPhoneNumber.text.toString().isNotEmpty() &&
                editAddress.text.toString().isNotEmpty() &&
                editState.selectedItem.toString().isNotEmpty() &&
                editZipcode.text.toString().isNotEmpty()
        ) {
                    val customer = CustomerDto(
                            customerId,
                            editEmail.text.toString(),
                            editPassword.text.toString(),
                            editFirstName.text.toString(),
                            editLastName.text.toString(),
                            editDob.text.toString(),
                            editPhoneNumber.text.toString(),
                            editAddress.text.toString(),
                            editState.selectedItem.toString(),
                            editZipcode.text.toString().toInt(),
                            null
                    )

                    customerDomain.updateCustomer(customer) // customerDomain updates customer
                    clearField() // clear fields
                    return true
            }
        else {
            Toast.makeText(context, "All fields are required to be filled in.", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    private fun setDatePicker() {
        val myCalendar: Calendar = Calendar.getInstance() // get instance of calendar
        val editDOB = findViewById<EditText>(R.id.editDOB)

        val date =
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    myCalendar.set(Calendar.YEAR, year)
                    myCalendar.set(Calendar.MONTH, monthOfYear)
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateLabel(editDOB, myCalendar)
                }

        editDOB.setOnClickListener {
            DatePickerDialog(
                    this@EditCustomerProfile,
                    date,
                    myCalendar[Calendar.YEAR],
                    myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }
    }

    private fun setStateSpinner() { // drop down view
        val spinner: Spinner = findViewById(R.id.state_spinner)

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
                this,
                R.array.states_array, // this array is being grabbed from strings.xml in values folder
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    private fun updateLabel(dobText: EditText, calendar: Calendar) {
        val myFormat = "MM/dd/yy" // In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dobText.setText(sdf.format(calendar.getTime()))
    }

    private fun clearField() {
        val editEmail = findViewById<EditText>(R.id.editEmail)
        val editPassword = findViewById<EditText>(R.id.editPassword)
        val editFirstName = findViewById<EditText>(R.id.editFirstName)
        val editLastName = findViewById<EditText>(R.id.editLastName)
        val editDob = findViewById<EditText>(R.id.editDOB)
        val editPhoneNumber = findViewById<EditText>(R.id.editPhoneNumber)
        val editAddress = findViewById<EditText>(R.id.editAddress)
        val editState = findViewById<Spinner>(R.id.state_spinner)
        val editZipcode = findViewById<EditText>(R.id.editZipCode)
        editEmail.text.clear()
        editPassword.text.clear()
        editFirstName.text.clear()
        editLastName.text.clear()
        editDob.text.clear()
        editPhoneNumber.text.clear()
        editAddress.text.clear()
        editState.adapter = null
        editZipcode.text.clear()
    }

    private fun loadCustomer(customer: MutableList<CustomerDto>) {
        try {
            val stateArray = resources.getStringArray(R.array.states_array)
            var stateArrayIndex = stateArray.indexOf(customer[0].state)

            if(stateArrayIndex == -1){
                throw Exception("State does not exist in the list of known states!")
            }

            val editEmail = findViewById<EditText>(R.id.editEmail)
            val editPassword = findViewById<EditText>(R.id.editPassword)
            val editFirstName = findViewById<EditText>(R.id.editFirstName)
            val editLastName = findViewById<EditText>(R.id.editLastName)
            val editDob = findViewById<EditText>(R.id.editDOB)
            val editPhoneNumber = findViewById<EditText>(R.id.editPhoneNumber)
            val editAddress = findViewById<EditText>(R.id.editAddress)
            val editState = findViewById<Spinner>(R.id.state_spinner)
            val editZipcode = findViewById<EditText>(R.id.editZipCode)

            editEmail.setText(customer[0].email)
            editPassword.setText(customer[0].password)
            editFirstName.setText(customer[0].firstName)
            editLastName.setText(customer[0].lastName)
            editDob.setText(customer[0].dob)
            editPhoneNumber.setText(customer[0].phoneNumber)
            editAddress.setText(customer[0].address)
            editState.setSelection(stateArrayIndex)
            editZipcode.setText(customer[0].zipcode.toString())
        }
        catch(e : Exception){
            print(e)
        }
    }
}

