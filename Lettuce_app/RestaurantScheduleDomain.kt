package com.lettuceapp.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat


class RestaurantScheduleDomain(context : Context){
    private val activityContext = context

    fun createRestaurantSchedule(restaurantSchedule: RestaurantScheduleDto) {
        val db = DatabaseHandler(activityContext)
        val contentValues = ContentValues()

        // attributes of table (do not need auto-incremented values)
        contentValues.put(RestID, restaurantSchedule.restaurantID)
        contentValues.put(MondayOpeningTime, restaurantSchedule.mondayOpeningTime)
        contentValues.put(MondayClosingTime, restaurantSchedule.mondayClosingTime)
        contentValues.put(TuesdayOpeningTime, restaurantSchedule.tuesdayOpeningTime)
        contentValues.put(TuesdayClosingTime, restaurantSchedule.tuesdayClosingTime)
        contentValues.put(WednesdayOpeningTime, restaurantSchedule.wednesdayOpeningTime)
        contentValues.put(WednesdayClosingTime, restaurantSchedule.wednesdayClosingTime)
        contentValues.put(TuesdayOpeningTime, restaurantSchedule.thursdayOpeningTime)
        contentValues.put(ThursdayClosingTime, restaurantSchedule.thursdayClosingTime)
        contentValues.put(FridayOpeningTime, restaurantSchedule.fridayOpeningTime)
        contentValues.put(FridayClosingTime, restaurantSchedule.fridayClosingTime)
        contentValues.put(SaturdayOpeningTime, restaurantSchedule.saturdayOpeningTime)
        contentValues.put(SaturdayClosingTime, restaurantSchedule.saturdayClosingTime)
        contentValues.put(SundayOpeningTime, restaurantSchedule.sundayOpeningTime)
        contentValues.put(SundayClosingTime, restaurantSchedule.sundayClosingTime)

        // toast to show fail/success of added data
        val result = db.insertToDatabase(TABLENAME_Restaurant_Schedule, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(activityContext, "Something went wrong! Try Again.", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(activityContext, "Schedule Created!", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateRestaurantSchedule(restaurantSchedule: RestaurantScheduleDto) {
        val db = DatabaseHandler(activityContext)
        val contentValues = ContentValues()

        // attributes of table (do not need auto-incremented values)
        contentValues.put(RestID, restaurantSchedule.restaurantID)
        contentValues.put(MondayOpeningTime, restaurantSchedule.mondayOpeningTime)
        contentValues.put(MondayClosingTime, restaurantSchedule.mondayClosingTime)
        contentValues.put(TuesdayOpeningTime, restaurantSchedule.tuesdayOpeningTime)
        contentValues.put(TuesdayClosingTime, restaurantSchedule.tuesdayClosingTime)
        contentValues.put(WednesdayOpeningTime, restaurantSchedule.wednesdayOpeningTime)
        contentValues.put(WednesdayClosingTime, restaurantSchedule.wednesdayClosingTime)
        contentValues.put(ThursdayOpeningTime, restaurantSchedule.thursdayOpeningTime)
        contentValues.put(ThursdayClosingTime, restaurantSchedule.thursdayClosingTime)
        contentValues.put(FridayOpeningTime, restaurantSchedule.fridayOpeningTime)
        contentValues.put(FridayClosingTime, restaurantSchedule.fridayClosingTime)
        contentValues.put(SaturdayOpeningTime, restaurantSchedule.saturdayOpeningTime)
        contentValues.put(SaturdayClosingTime, restaurantSchedule.saturdayClosingTime)
        contentValues.put(SundayOpeningTime, restaurantSchedule.sundayOpeningTime)
        contentValues.put(SundayClosingTime, restaurantSchedule.sundayClosingTime)

        // toast to show fail/success of added data
        val result = db.updateAllData(TABLENAME_Restaurant_Schedule, contentValues, restaurantSchedule.restaurantID)
        if (result == 0) {
            Toast.makeText(activityContext, "Something went wrong! Try Again.", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(activityContext, "Schedule Updated!", Toast.LENGTH_SHORT).show()
        }
    }

    fun getRestaurantScheduleByRestaurantID(restaurantID : Int): RestaurantScheduleDto? {
        val db = DatabaseHandler(activityContext)
        val query = "SELECT * FROM $TABLENAME_Restaurant_Schedule WHERE $RestID = $restaurantID"
        val result = db.getData(query)
        if (result != null) {
            if (result.moveToFirst()) {
                return RestaurantScheduleDto(
                    result.getInt(result.getColumnIndex(ScheduleID)),
                    result.getInt(result.getColumnIndex(RestID)),
                    result.getString(result.getColumnIndex(MondayOpeningTime)),
                    result.getString(result.getColumnIndex(MondayClosingTime)),
                    result.getString(result.getColumnIndex(TuesdayOpeningTime)),
                    result.getString(result.getColumnIndex(TuesdayClosingTime)),
                    result.getString(result.getColumnIndex(WednesdayOpeningTime)),
                    result.getString(result.getColumnIndex(WednesdayClosingTime)),
                    result.getString(result.getColumnIndex(ThursdayOpeningTime)),
                    result.getString(result.getColumnIndex(ThursdayClosingTime)),
                    result.getString(result.getColumnIndex(FridayOpeningTime)),
                    result.getString(result.getColumnIndex(FridayClosingTime)),
                    result.getString(result.getColumnIndex(SaturdayOpeningTime)),
                    result.getString(result.getColumnIndex(SaturdayClosingTime)),
                    result.getString(result.getColumnIndex(SundayOpeningTime)),
                    result.getString(result.getColumnIndex(SaturdayClosingTime))
                )
            }
        }
        return null
    }
}