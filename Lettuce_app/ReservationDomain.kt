package com.lettuceapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class ReservationDomain(context : Context){
    private val activityContext = context

    fun createReservation(reservation : ReservationDto) {
        val db = DatabaseHandler(activityContext)
        val contentValues = ContentValues()

        // attributes of table (do not need auto-incremented values)
        contentValues.put(RestResID, reservation.restaurantID)
        contentValues.put(CustResID, reservation.customerID)
        contentValues.put(TableResID, reservation.tableID)
        contentValues.put(ReservationDate, reservation.reservationDate)
        contentValues.put(TimeSlot, reservation.timeSlot)
        contentValues.put(GuestAmt, reservation.guestAmt)
        contentValues.put(ConfirmationSent, reservation.confirmationSent)

        // toast to show fail/success of added data
        val result = db.insertToDatabase(TABLENAME_Reservation, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(activityContext, "Something went wrong! Try Again.", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(activityContext, "Reservation Created!", Toast.LENGTH_SHORT).show()
        }
    }

    fun getReservationByCustomerID(customerID : Int) : MutableList<ReservationDto>{
        val list: MutableList<ReservationDto> = ArrayList()//List to get value in table
        val query = "Select * FROM $TABLENAME_Reservation WHERE $CustResID = '$customerID'"
        val db = DatabaseHandler(activityContext)
        val result = db.getData(query)
        if (result != null) {
            if (result.moveToFirst()) {
                do {
                    val reservation = ReservationDto(
                        result.getString(result.getColumnIndex(ReservationID)).toInt(),
                        result.getString(result.getColumnIndex(CustResID)).toInt(),
                        result.getString(result.getColumnIndex(RestResID)).toInt(),
                        result.getString(result.getColumnIndex(TableResID)).toInt(),
                        result.getString(result.getColumnIndex(ReservationDate)),
                        result.getString(result.getColumnIndex(TimeSlot)),
                        result.getString(result.getColumnIndex(GuestAmt)).toInt(),
                        result.getString(result.getColumnIndex(ConfirmationSent)).toInt())
                    list.add(reservation)

                                    }
                while (result.moveToNext())
            }
        }
        return list

    }

    fun getReservationByReservationID(reservationID : Int) : MutableList<ReservationDto>{
        val list: MutableList<ReservationDto> = ArrayList()//List to get value in table
        val query = "Select * FROM $TABLENAME_Reservation WHERE $ReservationID = '$reservationID'"
        val db = DatabaseHandler(activityContext)
        val result = db.getData(query)
        if (result != null) {
            if (result.moveToFirst()) {
                do {
                    val reservation = ReservationDto(
                            result.getString(result.getColumnIndex(ReservationID)).toInt(),
                            result.getString(result.getColumnIndex(CustResID)).toInt(),
                            result.getString(result.getColumnIndex(RestResID)).toInt(),
                            result.getString(result.getColumnIndex(TableResID)).toInt(),
                            result.getString(result.getColumnIndex(ReservationDate)),
                            result.getString(result.getColumnIndex(TimeSlot)),
                            result.getString(result.getColumnIndex(GuestAmt)).toInt(),
                            result.getString(result.getColumnIndex(ConfirmationSent)).toInt())
                    list.add(reservation)

                }
                while (result.moveToNext())
            }
        }
        return list

    }





    fun updateReservation(reservation: ReservationDto){
        val db = DatabaseHandler(activityContext)

        val status = db.updateReservation(TABLENAME_Reservation, reservation)
        if (status > -1) {
            Toast.makeText(activityContext, "Record Updated.", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(activityContext, "Value was left blank", Toast.LENGTH_LONG).show()
        }

    }


//
//    fun getReservationByID(reservationDto : Int): Int? {
//        var id = ""
//        val db = DatabaseHandler(activityContext)
//        val query = "Select ID from $TABLENAME_Reservation WHERE id = '$id'"
//
//        val result = db.getData(query)
//        if (result != null) {
//            if (result.moveToFirst()) {
//                do {
//                    id = result.getString(result.getColumnIndex(ReservationID))
//                }
//                while (result.moveToNext())
//            }
//        }
//
//        if(result == null){
//            return null
//        }
//    }
//
//    fun modifyReservation(r : ReservationDto): Int? {
//        var id = ""
//        val db = DatabaseHandler(activityContext)
//        val query = "Select ID from $TABLENAME_Reservation WHERE id = '$id'"
//
//        val result = db.getData(query)
//        if (result != null) {
//            if (result.moveToFirst()) {
//                do {
//                    id = result.getString(result.getColumnIndex(ReservationID))
//                }
//                while (result.moveToNext())
//            }
//        }
//
//        if(result == null){
//            return null
//        }
//    }
//
//    fun deleteReservationByID(reservationDto : Int): Int? {
//        var id = ""
//        val db = DatabaseHandler(activityContext)
//        val query = "Select ID from $TABLENAME_Reservation WHERE id = '$id'"
//
//        val result = db.getData(query)
//        if (result != null) {
//            if (result.moveToFirst()) {
//                do {
//                    id = result.getString(result.getColumnIndex(ReservationID))
//                }
//                while (result.moveToNext())
//            }
//        }
//
//        if(result == null){
//            return null
//        }
//    }
}
