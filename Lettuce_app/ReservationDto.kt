package com.lettuceapp.data

import java.util.*

open class ReservationDto (var reservationID: Int,
                           var restaurantID: Int,
                           var customerID: Int,
                           var tableID : Int,
                           var reservationDate : String,
                           var timeSlot : String,
                           var guestAmt : Int,
                           var confirmationSent : Int){
}