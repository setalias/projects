package com.lettuceapp.data

class Table(
    var id: Int, var restaurantId: Int, var seatsAmt: Int?,
    var isReserved: Int, var seatingType: String?, var tableLocation: String?,
    var tableXLocation: Float, var tableYLocation: Float, var tableImageId: Int?,
    var isDraft: Int
)