package com.example.lettuceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.lettuceapp.data.CustomerDomain
import com.lettuceapp.data.RestaurantDomain
import com.lettuceapp.data.TableDomain

class DisplayDBData : AppCompatActivity() {
    private val restaurantDomain = RestaurantDomain(this)
    private val customerDomain = CustomerDomain(this)
    private val tableDomain = TableDomain(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_d_b_data)
        val result = findViewById<TextView>(R.id.result)

        val btnCustomerData = findViewById<Button>(R.id.displayCustomerData)
        btnCustomerData.setOnClickListener {
            val data = customerDomain.getAllCustomers()
            result.text = ""
            for (i in 0 until data.size) {
                result.append(
                    "CustomerID: " + data[i].id.toString()  + "\n" +
                    "CustomerEmail: " + data[i].email  + "\n" +
                    "CustomerPassword: " + data[i].password  + "\n" +
                    "CustomerFirstName: " + data[i].firstName + "\n" +
                    "CustomerLastName: " + data[i].lastName  + "\n" +
                    "CustomerDOB: " + data[i].dob + "\n" +
                    "CustomerPhoneNumber: " + data[i].phoneNumber  + "\n" +
                    "CustomerAddress: " + data[i].address  + "\n" +
                    "CustomerState: " + data[i].state + "\n" +
                    "CustomerZipCode: " + data[i].zipcode.toString()  + "\n" +
                    "CustomerCreditCard: " + data[i].creditCardNumber.toString() + "\n"
                )
            }
        }

        val btnRestaurantData = findViewById<Button>(R.id.displayRestaurantData)
        btnRestaurantData.setOnClickListener {
            val data = restaurantDomain.getAllRestaurants()
            result.text = ""
            for (i in 0 until data.size) {
                result.append(
                    "RestaurantId: " + data[i].id.toString()  + "\n" +
                            "RestaurantEmail: " + data[i].email  + "\n" +
                            "RestaurantPassword: " + data[i].password  + "\n" +
                            "RestaurantContactFirstName: " + data[i].contactFirstName + "\n" +
                            "RestaurantContactLastName: " + data[i].contactLastName  + "\n" +
                            "RestaurantFoodType: " + data[i].foodType + "\n" +
                            "RestaurantPhoneNumber: " + data[i].phoneNumber  + "\n" +
                            "RestaurantAddress: " + data[i].address  + "\n" +
                            "RestaurantState: " + data[i].state + "\n" +
                            "RestaurantZipCode: " + data[i].zipcode.toString()  + "\n"
                )
            }
        }

        val btnMetadataData = findViewById<Button>(R.id.displayMetadata)
        btnMetadataData.setOnClickListener {
            val data = tableDomain.getTableMetadata()
            result.text = ""
            for (i in 0 until data.size) {
                result.append(
                        "MetadataId: " + data[i].imageMetadataId.toString()  + "\n" +
                        "MetadataDescription: " + data[i].imageDescription  + "\n"
                )
            }
        }
    }
}