package com.lettuceapp.data

class Restaurant(
    var id: Int,
    var email: String,
    var password: String,
    var restaurantName: String,
    var contactFirstName: String,
    var contactLastName: String,
    var foodType: String,
    var phoneNumber: String,
    var address: String,
    var state: String,
    var zipcode: Int,
    var tableList: List<Table>
)