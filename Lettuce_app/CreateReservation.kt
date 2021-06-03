package com.example.lettuceapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.lettuceapp.data.*
import java.text.SimpleDateFormat
import com.lettuceapp.data.CustomerDomain
import java.util.*


class CreateReservation : AppCompatActivity() {
    private val customerDomain = CustomerDomain(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_reservation)

        val restaurantId = intent.getIntExtra("restaurantID", 0)
        val emailFromLastActivity = intent.getStringExtra("email")

        setDatePicker() // calendar
        setTimeSlotSpinner() // drop down

        val tableSelection = findViewById<Button>(R.id.tableSelect)

        tableSelection.setOnClickListener {
            val customerID = getCustomerID(emailFromLastActivity.toString())
            val guestAmt = getGuestAmt()
            val reservationDate = getReservationDate()
            val timeSlot = getTimeSlot()

            val intent = Intent(this, CustomerTableLayout::class.java)
            val intentBundle = Bundle()

            intentBundle.putInt("customerID", customerID)
            intentBundle.putInt("restaurantID", restaurantId)
            intentBundle.putInt("guestAmt", guestAmt)
            intentBundle.putString("reservationDate", reservationDate)
            intentBundle.putString("timeSlot", timeSlot)
            intentBundle.putString("email", emailFromLastActivity)
            intent.putExtras(intentBundle)
            startActivity(intent)
        }
    }

    private fun getCustomerID(email : String) : Int{
        val data = customerDomain.getCustomerByEmail(email)
        val custID = data[0].id
        return custID
    }

    private fun getGuestAmt() : Int {
        val editGuestAmt = findViewById<EditText>(R.id.guestAmt)
        return editGuestAmt.text.toString().toInt()
    }

    private fun getReservationDate() : String {
        val editReservationDate  = findViewById<EditText>(R.id.reservationDate)
        return editReservationDate.text.toString()
    }
    private fun getTimeSlot() : String {
        val editTimeSlot = findViewById<Spinner>(R.id.timeSlot)
        return editTimeSlot.selectedItem.toString()
    }


    private fun setDatePicker() {
        val myCalendar: Calendar = Calendar.getInstance() // get instance of calendar
        val editresDate = findViewById<EditText>(R.id.reservationDate)

        val date =
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    myCalendar.set(Calendar.YEAR, year)
                    myCalendar.set(Calendar.MONTH, monthOfYear)
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateLabel(editresDate, myCalendar)
                }

        editresDate.setOnClickListener {
            DatePickerDialog(
                    this@CreateReservation,
                    date,
                    myCalendar[Calendar.YEAR],
                    myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }
    }

    private fun updateLabel(dobText: EditText, calendar: Calendar) {
        val myFormat = "MM/dd/yy" // In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dobText.setText(sdf.format(calendar.getTime()))
    }

    private fun setTimeSlotSpinner() {
        val spinner: Spinner = findViewById(R.id.timeSlot)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
                this,
                R.array.timeslot_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }


//    override fun clearField() {
//        val editGuestAmt = findViewById<EditText>(R.id.editGuestAmt)
//        val editReservationDate = findViewById<EditText>(R.id.editReservationDate)
//        val editTimeSlot = findViewById<Spinner>(R.id.editTimeSlot)
//
//        editGuestAmt.text.clear()
//        editReservationDate.text.clear()
//        editTimeSlot.setAdapter.clear()
//
//    }
//

}
