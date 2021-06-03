package com.lettuceapp.data

import android.content.ContentValues
import android.content.Context
import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList

class CustomerDomain(context : Context) {
    private val activityContext = context

    fun createCustomer(customer: CustomerDto) {
        val db = DatabaseHandler(activityContext)
        val contentValues = ContentValues()

        // attributes of table (do not need auto-incremented values)
        contentValues.put(CustEmail, customer.email.toLowerCase())
        contentValues.put(CustPassword, customer.password)
        contentValues.put(CustFirstName, customer.firstName.toLowerCase(Locale.ROOT))
        contentValues.put(CustLastName, customer.lastName.toLowerCase())
        contentValues.put(CustDOB, customer.dob)
        contentValues.put(CustPhoneNumber, customer.phoneNumber)
        contentValues.put(CustAddress, customer.address.toLowerCase())
        contentValues.put(CustState, customer.state)
        contentValues.put(CustZipCode, customer.zipcode)
        contentValues.put(CustCreditCard, customer.creditCardNumber)

        // toast to show fail/success of added data
        val result = db.insertToDatabase(TABLENAME_Customer, contentValues)
        db.close()
        if (result == (0).toLong()) {
            Toast.makeText(activityContext, "Something went wrong! Try Again.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activityContext, "Profile Created!", Toast.LENGTH_SHORT).show()
        }
    }

    fun getAllCustomers(): MutableList<CustomerDto> {
        val db = DatabaseHandler(activityContext)
        val list: MutableList<CustomerDto> = ArrayList() // list to get value in table
        val query = "Select * from $TABLENAME_Customer" // the query select ALL

        val result = db.getData(query)
        if (result != null) {
            if (result.moveToFirst()) {
                do {
                    val customer = CustomerDto(
                            result.getString(result.getColumnIndex(CustID)).toInt(),
                            result.getString(result.getColumnIndex(CustEmail)),
                            result.getString(result.getColumnIndex(CustPassword)),
                            result.getString(result.getColumnIndex(CustFirstName)),
                            result.getString(result.getColumnIndex(CustLastName)),
                            result.getString(result.getColumnIndex(CustDOB)),
                            result.getString(result.getColumnIndex(CustPhoneNumber)),
                            result.getString(result.getColumnIndex(CustAddress)),
                            result.getString(result.getColumnIndex(CustState)),
                            result.getString(result.getColumnIndex(CustZipCode)).toInt(), null)
                    list.add(customer) // save to result list
                } while (result.moveToNext())
            }
        }
        db.close()
        return list
    }

    fun getCustomerByEmail(email: String): MutableList<CustomerDto> {
        val list: MutableList<CustomerDto> = ArrayList() // list to get value in table
        val db = DatabaseHandler(activityContext)
        val query = "Select * from $TABLENAME_Customer WHERE email = '$email'"

        val result = db.getData(query)
        if (result != null) {
            if (result.moveToFirst()) {
                do {
                    val customer = CustomerDto(
                            result.getString(result.getColumnIndex(CustID)).toInt(),
                            result.getString(result.getColumnIndex(CustEmail)),
                            result.getString(result.getColumnIndex(CustPassword)),
                            result.getString(result.getColumnIndex(CustFirstName)),
                            result.getString(result.getColumnIndex(CustLastName)),
                            result.getString(result.getColumnIndex(CustDOB)),
                            result.getString(result.getColumnIndex(CustPhoneNumber)),
                            result.getString(result.getColumnIndex(CustAddress)),
                            result.getString(result.getColumnIndex(CustState)),
                            result.getString(result.getColumnIndex(CustZipCode)).toInt(),
                            null)
                    list.add(customer)
                } while (result.moveToNext())
            }
        }
        return list
    }

    fun getPasswordFromEmail(email: String): String? {
        var password = ""
        val db = DatabaseHandler(activityContext)
        val query = "Select password from $TABLENAME_Customer WHERE email = '$email'"

        val result = db.getData(query)
        if (result != null) {
            if (result.moveToFirst()) {
                do {
                    password = result.getString(result.getColumnIndex(CustPassword))
                } while (result.moveToNext())
            }
        }

        if (result == null) {
            return null
        }
        return password
    }


    fun updateCustomer(customer: CustomerDto) {
        val db = DatabaseHandler(activityContext)

        val status = db.updateCustomer(TABLENAME_Customer, customer)
        if (status > -1) {
            Toast.makeText(activityContext, "Record Updated.", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(activityContext, "Value was left blank", Toast.LENGTH_LONG).show()
        }
    }
}