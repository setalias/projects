package com.example.lettuceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.lettuceapp.data.*

class ReservationDisplay : AppCompatActivity() {
    private val reservationDomain = ReservationDomain(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_display)

        val getCustomerIDIntent = intent
        val customerIDFromLastActivity = getCustomerIDIntent.getIntExtra("customerID", 0)
        val data = reservationDomain.getReservationByCustomerID(customerIDFromLastActivity)
        val layout = findViewById<LinearLayout>(R.id.reservationsearchlayout)


        for (i in 0 until data.size) {
            val reservationButtons = Button(this) // create button
            reservationButtons.text = data[i].restaurantID.toString() + "\n" + data[i].reservationDate + "\n"
            reservationButtons.setOnClickListener {
                val intent = Intent(this, ReservationEdit::class.java)
                intent.putExtra(
                        "reservationID", data[i].reservationID
                )
                intent.putExtra(
                        "customerID", customerIDFromLastActivity
                )
                startActivity(intent)
            }
            layout.addView(reservationButtons)
        }
    }

    fun onClick(view: View) {
        val intent = Intent(this, CustomerDashboard::class.java)
        startActivity(intent)
    }
}