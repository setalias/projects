package com.lettuceapp.data

class RestaurantDto (var id: Int,
                     override var email: String,
                     override var password: String,
                     var restaurantName: String,
                     var contactFirstName: String,
                     var contactLastName: String,
                     var foodType: String,
                     override var phoneNumber: String,
                     override var address: String,
                     override var state: String,
                     override var zipcode: Int) : UserDTO() {
}