package com.lettuceapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class RestaurantDomain(context : Context){
    private val activityContext = context

    fun createRestaurant(restaurant: RestaurantDto) {
        val db = DatabaseHandler(activityContext)
        val contentValues = ContentValues()

        // attributes of table (do not need auto-incremented values)
        contentValues.put(RestEmail, restaurant.email.toLowerCase())
        contentValues.put(RestPassword, restaurant.password)
        contentValues.put(RestName, restaurant.restaurantName)
        contentValues.put(RestContactFirstName, restaurant.contactFirstName.toLowerCase())
        contentValues.put(RestContactLastName, restaurant.contactLastName.toLowerCase())
        contentValues.put(RestFoodType, restaurant.foodType)
        contentValues.put(RestPhoneNumber, restaurant.phoneNumber)
        contentValues.put(RestAddress, restaurant.address.toLowerCase())
        contentValues.put(RestState, restaurant.state)
        contentValues.put(RestZipCode, restaurant.zipcode)

        // toast to show fail/success of added data
        val result = db.insertToDatabase(TABLENAME_Restaurant, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(activityContext, "Something went wrong! Try Again.", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(activityContext, "Profile Created!", Toast.LENGTH_SHORT).show()
        }
    }

    fun getAllRestaurants(): MutableList<RestaurantDto> {
        val list: MutableList<RestaurantDto> = ArrayList() // list to get value in table
        val db = DatabaseHandler(activityContext)
        val query = "Select * from $TABLENAME_Restaurant" // the query select ALL
        val result = db.getData(query)
        if (result != null) {
            if (result.moveToFirst()) {
                do {
                    val restaurant = RestaurantDto(
                        result.getString(result.getColumnIndex(RestID)).toInt(),
                        result.getString(result.getColumnIndex(RestEmail)),
                        result.getString(result.getColumnIndex(RestPassword)),
                        result.getString(result.getColumnIndex(RestName)),
                        result.getString(result.getColumnIndex(RestContactFirstName)),
                        result.getString(result.getColumnIndex(RestContactLastName)),
                        result.getString(result.getColumnIndex(RestFoodType)),
                        result.getString(result.getColumnIndex(RestPhoneNumber)),
                        result.getString(result.getColumnIndex(RestAddress)),
                        result.getString(result.getColumnIndex(RestState)),
                        result.getString(result.getColumnIndex(RestZipCode)).toInt())
                    list.add(restaurant)
                }
                while (result.moveToNext())
            }
        }
        return list
    }

    fun getRestaurantByZipCode(zipCode : Int): MutableList<RestaurantDto> {
        val list: MutableList<RestaurantDto> = ArrayList() // list to get value in table
        val db = DatabaseHandler(activityContext)
        val query = "Select * FROM $TABLENAME_Restaurant WHERE $RestZipCode = '$zipCode'"

        val result = db.getData(query)
        if (result != null) {
            if (result.moveToFirst()) {
                do {
                    val restaurant = RestaurantDto(
                            result.getString(result.getColumnIndex(RestID)).toInt(),
                            result.getString(result.getColumnIndex(RestEmail)),
                            result.getString(result.getColumnIndex(RestPassword)),
                            result.getString(result.getColumnIndex(RestName)),
                            result.getString(result.getColumnIndex(RestContactFirstName)),
                            result.getString(result.getColumnIndex(RestContactLastName)),
                            result.getString(result.getColumnIndex(RestFoodType)),
                            result.getString(result.getColumnIndex(RestPhoneNumber)),
                            result.getString(result.getColumnIndex(RestAddress)),
                            result.getString(result.getColumnIndex(RestState)),
                            result.getString(result.getColumnIndex(RestZipCode)).toInt())
                    list.add(restaurant)
                }
                while (result.moveToNext())
            }
        }
        return list
    }

    fun getRestaurantById(restID : Int) : MutableList<RestaurantDto>{
        val list: MutableList<RestaurantDto> = ArrayList()//List to get value in table
        val db = DatabaseHandler(activityContext)
        val query = "Select * FROM $TABLENAME_Restaurant WHERE $RestID = '$restID'"

        val result = db.getData(query)
        if (result != null) {
            if (result.moveToFirst()) {
                do {
                    val restaurant = RestaurantDto(
                            result.getString(result.getColumnIndex(RestID)).toInt(),
                            result.getString(result.getColumnIndex(RestEmail)),
                            result.getString(result.getColumnIndex(RestPassword)),
                            result.getString(result.getColumnIndex(RestName)),
                            result.getString(result.getColumnIndex(RestContactFirstName)),
                            result.getString(result.getColumnIndex(RestContactLastName)),
                            result.getString(result.getColumnIndex(RestFoodType)),
                            result.getString(result.getColumnIndex(RestPhoneNumber)),
                            result.getString(result.getColumnIndex(RestAddress)),
                            result.getString(result.getColumnIndex(RestState)),
                            result.getString(result.getColumnIndex(RestZipCode)).toInt())
                    list.add(restaurant)
                }
                while (result.moveToNext())
            }
        }
        return list
    }


    fun getRestaurantByEmail(email : String): MutableList<RestaurantDto> {
        val list: MutableList<RestaurantDto> = ArrayList() // list to get value in table
        val db = DatabaseHandler(activityContext)
        val query = "Select * from $TABLENAME_Restaurant WHERE email = '$email'"

        val result = db.getData(query)
        if (result != null) {
            if (result.moveToFirst()) {
                do {
                    val restaurant = RestaurantDto(
                        result.getString(result.getColumnIndex(RestID)).toInt(),
                        result.getString(result.getColumnIndex(RestEmail)),
                        result.getString(result.getColumnIndex(RestPassword)),
                        result.getString(result.getColumnIndex(RestName)),
                        result.getString(result.getColumnIndex(RestContactFirstName)),
                        result.getString(result.getColumnIndex(RestContactLastName)),
                        result.getString(result.getColumnIndex(RestFoodType)),
                        result.getString(result.getColumnIndex(RestPhoneNumber)),
                        result.getString(result.getColumnIndex(RestAddress)),
                        result.getString(result.getColumnIndex(RestState)),
                        result.getString(result.getColumnIndex(RestZipCode)).toInt())
                    list.add(restaurant)
                }
                while (result.moveToNext())
            }
        }
        return list
    }

    fun getPasswordFromEmail(email : String): String? {
        var password = ""
        val db = DatabaseHandler(activityContext)
        val query = "Select password from $TABLENAME_Restaurant WHERE email = '$email'"

        val result = db.getData(query)
        if (result != null) {
            if (result.moveToFirst()) {
                do {
                    password = result.getString(result.getColumnIndex(RestPassword))
                }
                while (result.moveToNext())
            }
        }

        if(result == null){
            return null
        }
        return password
    }

    fun updateRestaurant(restaurant: RestaurantDto) {
            val db = DatabaseHandler(activityContext)

            val status = db.updateRestaurant(TABLENAME_Restaurant, restaurant)
            if (status > -1) {
                Toast.makeText(activityContext, "Record Updated.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(activityContext, "Value was left blank", Toast.LENGTH_LONG).show()
            }
    }
}