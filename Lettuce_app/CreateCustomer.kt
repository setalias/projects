package com.example.lettuceapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.lettuceapp.data.CustomerDto
import com.lettuceapp.data.CustomerDomain
import java.text.SimpleDateFormat
import java.util.*


class CreateCustomer : AppCompatActivity(), User {
    private val customerDomain = CustomerDomain(this) // set up domain so we can use it

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN) // so stuff don't push to top on UI
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_customer)

        setDatePicker() // calendar
        setStateSpinner() // drop down

        // onclick for insert btn
        val createCustomerProfile = findViewById<Button>(R.id.createProfile)
        createCustomerProfile.setOnClickListener {
            var isSuccess = createProfile();
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
        val editFirstName = findViewById<EditText>(R.id.editFirstName)
        val editLastName = findViewById<EditText>(R.id.editLastName)
        val editDob = findViewById<EditText>(R.id.editDOB)
        val editPhoneNumber = findViewById<EditText>(R.id.editPhoneNumber)
        val editAddress = findViewById<EditText>(R.id.editAddress)
        val editState = findViewById<Spinner>(R.id.state_spinner)
        val editZipcode = findViewById<EditText>(R.id.editZipCode)

        // validation
        if (editEmail.text.toString().isNotEmpty() &&
            editPassword.text.toString().isNotEmpty() &&
            editFirstName.text.toString().isNotEmpty() &&
            editLastName.text.toString().isNotEmpty() &&
            editDob.text.toString().isNotEmpty() &&
            editPhoneNumber.text.toString().isNotEmpty() &&
            editAddress.text.toString().isNotEmpty() &&
            editState.selectedItem.toString().isNotEmpty() &&
            editZipcode.text.toString().isNotEmpty()
        ) {

            // check for duplicate email
            when(isDuplicateUser(editEmail.text.toString())){
                1 -> Toast.makeText(context, "Email already exists!", Toast.LENGTH_SHORT).show()
                else -> {
                    val customer = CustomerDto(
                        0,
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
                    customerDomain.createCustomer(customer) // customerDomain creates customer
                    clearField() // clear fields
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

    private fun updateLabel(dobText: EditText, calendar: Calendar) {
        val myFormat = "MM/dd/yy" // In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dobText.setText(sdf.format(calendar.getTime()))
    }

    override fun clearField() {
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
                this@CreateCustomer,
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

    override fun isDuplicateUser(email : String) : Int{
        var dataEmail = customerDomain.getCustomerByEmail(email)
        return if(dataEmail.count() > 0){
            1
        } else{
            0
        }
    }

}