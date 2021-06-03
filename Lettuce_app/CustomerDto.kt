package com.lettuceapp.data

//creating a Data Model Class for Customers
class CustomerDto (
    var id: Int, var email: String, var password: String,
    var firstName: String, var lastName: String,
    var dob: String, var phoneNumber: String, var address: String,
    var state: String, var zipcode: Int, var creditCardNumber: Int?
)