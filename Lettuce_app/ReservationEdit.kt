package com.example.lettuceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.lettuceapp.data.*

class ReservationEdit : AppCompatActivity() {
    private val reservationDomain = ReservationDomain(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_edit)

        val getReservationIDIntent = intent
        val reservationID = getReservationIDIntent.getIntExtra("reservationID", 0)
        val data = reservationDomain.getReservationByReservationID(reservationID)

            val resID = findViewById<TextView>(R.id.ResID)
            resID.text = data[0].reservationID.toString()

            val dateOfRes = findViewById<TextView>(R.id.ResDate)
            dateOfRes.text = data[0].reservationDate

            val guestNum = findViewById<TextView>(R.id.numGuests)
            guestNum.text = data[0].guestAmt.toString()

            val resTime = findViewById<TextView>(R.id.editTime)
            resTime.text = data[0].timeSlot

        val updateRes = findViewById<Button>(R.id.saveEdit)

        updateRes.setOnClickListener{
            updateReservation(ReservationID, CustResID, RestResID, ConfirmationSent, TableResID )
            val intent = Intent(this, ReservationDisplay::class.java)
            intent.putExtra("customerID", 0)
            startActivity(intent)
        }
    }

    private fun updateReservation(ReservationID : String, CustResID : String, RestResID : String, ConfirmationSent :String, TableResID : String ) : Boolean {
        val context = this
        val editDate = findViewById<EditText>(R.id.ResDate)
        val editGuests = findViewById<EditText>(R.id.numGuests)
        val editTimeSlot = findViewById<TextView>(R.id.editTime)

        // validation
        if (
                editDate.text.toString().isNotEmpty() &&
                editGuests.text.toString().isNotEmpty() &&
                editTimeSlot.text.toString().isNotEmpty()

        ) {
            val reservation = ReservationDto(
                    ReservationID.toInt(),
                    CustResID.toInt(),
                    RestResID.toInt(),
                    editGuests.text.toString().toInt(),
                    editDate.text.toString(),
                    editTimeSlot.text.toString(),
                    ConfirmationSent.toInt(),
                    TableResID.toInt()

            )

            reservationDomain.updateReservation(reservation) // customerDomain updates customer
            clearField() // clear fields
            return true
        }
        else {
            Toast.makeText(context, "All fields are required to be filled in.", Toast.LENGTH_SHORT).show()
            return false
        }
    }



    fun onClick(view: View) {
        val getCustomerIDIntent = intent
        val customerIDFromLastActivity = getCustomerIDIntent.getIntExtra("customerID", 0)
        val intent = Intent(this, ReservationDisplay::class.java)
            intent.putExtra("customerID", customerIDFromLastActivity)
        startActivity(intent)
    }

    private fun clearField() {
        val editDate = findViewById<EditText>(R.id.ResDate)
        val editGuests = findViewById<EditText>(R.id.numGuests)
        val editTimeSlot = findViewById<TextView>(R.id.editTime)
        editDate.text.clear()
        editGuests.text.clear()
        //editTimeSlot.text.clear()
    }



}