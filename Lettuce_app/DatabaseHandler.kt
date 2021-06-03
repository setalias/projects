package com.lettuceapp.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

// our database basically
val TABLENAME_Customer = "Customers"
val TABLENAME_Restaurant = "Restaurant"
val TABLENAME_Reservation  = "Reservation"
val TABLENAME_Table = "Tables"
val TABLENAME_ImageMetadata = "ImageMetadata"
val TABLENAME_Restaurant_Schedule = "Restaurant_Schedule"

// Customer rows
val CustID = "id"
val CustEmail = "email"
val CustPassword = "password"
val CustFirstName = "firstName"
val CustLastName = "lastName"
val CustDOB = "dob"
val CustPhoneNumber = "phoneNumber"
val CustAddress = "address"
val CustState = "state"
val CustZipCode = "zipcode"
val CustCreditCard = "creditcard"

// Restaurant rows
val RestID = "id"
val RestEmail = "email"
val RestPassword = "password"
val RestName = "restaurantName"
val RestContactFirstName = "contactFirstName"
val RestContactLastName = "contactLastName"
val RestFoodType = "foodType"
val RestPhoneNumber = "phoneNumber"
val RestAddress = "address"
val RestState = "state"
val RestZipCode = "zipcode"

// Reservation rows
val ReservationID = "id"
val CustResID ="custresid"
val RestResID ="restresid"
val GuestAmt = "guestAmt"
val ReservationDate = "reservationDate"
val TimeSlot = "timeSlot"
val ConfirmationSent = "confirmationSent"
val TableResID = "tableResid"

// Table rows
val TableID = "id"
val RestaurantId = "restaurantId"
val SeatsAmt = "seatsAmt"
var IsReserved = "isReserved"
val TableSeatingType = "tableSeatingType"
val TableLocation = "tableLocation"
val TableXLocation = "tableXLocation"
val TableYLocation = "tableYLocation"
val TableImageId = "tableImageId"
val IsDraft = "isDraft"

// ImageMetadata rows
val TableImageMetadataId = "tableImageMetadataId"
val ImageDescription = "imageDescription"

// Restaurant_Schedule rows
val ScheduleID = "id"
val RestaurantID = "restId"
val MondayOpeningTime = "mondayOpeningTime"
val MondayClosingTime = "mondayClosingTime"
val TuesdayOpeningTime = "tuesdayOpeningTime"
val TuesdayClosingTime = "tuesdayClosingTime"
val WednesdayOpeningTime = "wednesdayOpeningTime"
val WednesdayClosingTime = "wednesdayClosingTime"
val ThursdayOpeningTime = "thursdayOpeningTime"
val ThursdayClosingTime = "thursdayClosingTime"
val FridayOpeningTime = "fridayOpeningTime"
val FridayClosingTime = "fridayClosingTime"
val SaturdayOpeningTime = "saturdayOpeningTime"
val SaturdayClosingTime = "saturdayClosingTime"
val SundayOpeningTime = "sundayOpeningTime"
val SundayClosingTime = "sundayClosingTime"

class DatabaseHandler(var context : Context) : SQLiteOpenHelper(context, "LettuceDatabase", null, 8) {

    // CREATE TABLE with these attributes (NOTE: If you add any new columns, you will need to delete old table and recreate!)
    override fun onCreate(db: SQLiteDatabase?) {
        val createCustomerTable = "CREATE TABLE IF NOT EXISTS $TABLENAME_Customer ($CustID INTEGER PRIMARY KEY AUTOINCREMENT,$CustEmail VARCHAR(256),$CustPassword VARCHAR(256), $CustFirstName VARCHAR(256), $CustLastName VARCHAR(256), $CustDOB INTEGER,$CustPhoneNumber VARCHAR(10),$CustAddress VARCHAR(1000),$CustState VARCHAR(2),$CustZipCode INTEGER,$CustCreditCard INTEGER)"
        db?.execSQL(createCustomerTable) // create this
        val createRestaurantTable = "CREATE TABLE IF NOT EXISTS $TABLENAME_Restaurant ($RestID INTEGER PRIMARY KEY AUTOINCREMENT,$RestEmail VARCHAR(256),$RestPassword VARCHAR(256), $RestName VARCHAR(256), $RestContactFirstName VARCHAR(256), $RestContactLastName VARCHAR(256), $RestFoodType VARCHAR(256),$RestPhoneNumber VARCHAR(10),$RestAddress VARCHAR(1000),$RestState VARCHAR(2),$RestZipCode INTEGER)"
        db?.execSQL(createRestaurantTable) // create this
        val createReservationTable = "CREATE TABLE IF NOT EXISTS $TABLENAME_Reservation ($ReservationID INTEGER PRIMARY KEY AUTOINCREMENT,$RestResID INTEGER, $CustResID INTEGER, $TableResID INTEGER, $ReservationDate INTEGER, $TimeSlot VARCHAR(256), $GuestAmt INTEGER, $ConfirmationSent INTEGER)"
        db?.execSQL(createReservationTable) // create this
        val createTableTable = "CREATE TABLE IF NOT EXISTS $TABLENAME_Table ($TableID INTEGER PRIMARY KEY AUTOINCREMENT,$RestaurantId INTEGER,$SeatsAmt INTEGER, $IsReserved INTEGER, $TableSeatingType VARCHAR(256), $TableLocation VARCHAR(256), $TableXLocation DECIMAL, $TableYLocation DECIMAL, $TableImageId INTEGER, $IsDraft INTEGER)"
        db?.execSQL(createTableTable)
        val createImageMetadataTable = "CREATE TABLE IF NOT EXISTS $TABLENAME_ImageMetadata($TableImageMetadataId INTEGER PRIMARY KEY,$ImageDescription VARCHAR(256))"
        db?.execSQL(createImageMetadataTable)
        val insertDataIntoImageMetadataTable = "INSERT INTO $TABLENAME_ImageMetadata VALUES (1,'circle'),(2,'square'),(3,'rectangle'),(4,'door'),(5,'window')"
        db?.execSQL(insertDataIntoImageMetadataTable)
        val createRestaurantScheduleTable = "CREATE TABLE IF NOT EXISTS $TABLENAME_Restaurant_Schedule($ScheduleID INTEGER PRIMARY KEY,$RestaurantID INTEGER, $MondayOpeningTime VARCHAR(256), $MondayClosingTime VARCHAR(256), $TuesdayOpeningTime VARCHAR(256), $TuesdayClosingTime VARCHAR(256), $WednesdayOpeningTime VARCHAR(256), $WednesdayClosingTime VARCHAR(256), $ThursdayOpeningTime VARCHAR(256), $ThursdayClosingTime VARCHAR(256), $FridayOpeningTime VARCHAR(256), $FridayClosingTime VARCHAR(256), $SaturdayOpeningTime VARCHAR(256), $SaturdayClosingTime VARCHAR(256), $SundayOpeningTime VARCHAR(256), $SundayClosingTime VARCHAR(256))"
        db?.execSQL(createRestaurantScheduleTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLENAME_Customer")
        db?.execSQL("DROP TABLE IF EXISTS $TABLENAME_Restaurant")
        db?.execSQL("DROP TABLE IF EXISTS $TABLENAME_Reservation")
        db?.execSQL("DROP TABLE IF EXISTS $TABLENAME_Table")
        db?.execSQL("DROP TABLE IF EXISTS $TABLENAME_ImageMetadata")
        db?.execSQL("DROP TABLE IF EXISTS $TABLENAME_Restaurant_Schedule")
        onCreate(db)
    }

    fun insertToDatabase(tableName : String, contentValues : ContentValues) : Long {
        val database = this.writableDatabase
        return database.insert(tableName, null, contentValues)
    }

    fun getData(query : String) : Cursor? {
        val database = this.readableDatabase
        return database.rawQuery(query, null)
    }

    fun updateData(query : String) {
        val database = this.writableDatabase
        val c = database.rawQuery(query, null)
        c.moveToFirst()
        c.close()
    }

    fun updateAllData(tableName: String,contentValues : ContentValues, key : Int): Int {
        val database = this.writableDatabase
        return database.update(tableName, contentValues, "$RestID=$key", null)
    }

    fun deleteData(query : String) {
        val database = this.writableDatabase
        database.execSQL(query)
    }


    // update Data still needs to be tested
    fun updateCustomer(tableName : String, customer : CustomerDto) : Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(CustEmail, customer.email.toLowerCase())
        contentValues.put(CustPassword, customer.password)
        contentValues.put(CustFirstName, customer.firstName.toLowerCase(Locale.ROOT))
        contentValues.put(CustLastName, customer.lastName.toLowerCase())
        contentValues.put(CustDOB, customer.dob)
        contentValues.put(CustPhoneNumber, customer.phoneNumber)
        contentValues.put(CustAddress, customer.address.toLowerCase())
        contentValues.put(CustState, customer.state)
        contentValues.put(CustZipCode, customer.zipcode)

        val success = database.update(tableName, contentValues, CustID + "=" + customer.id, null)
        database.close()
        return success
    }


    fun updateRestaurant(tableName : String, restaurant : RestaurantDto) : Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()

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

        val success = database.update(tableName, contentValues, CustID + "=" + restaurant.id, null)
        database.close()
        return success
    }

    // need to put update and delete

    fun updateReservation(tableName: String, reservation : ReservationDto) : Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(RestResID, reservation.restaurantID)
        contentValues.put(CustResID, reservation.customerID)
        contentValues.put(TableResID, reservation.tableID)
        contentValues.put(ReservationDate, reservation.reservationDate)
        contentValues.put(TimeSlot, reservation.timeSlot)
        contentValues.put(GuestAmt, reservation.guestAmt)
        contentValues.put(ConfirmationSent, reservation.confirmationSent)

        val success = database.update(tableName, contentValues, ReservationID + "=" + reservation.reservationID, null)
        database.close()
        return success
    }


}