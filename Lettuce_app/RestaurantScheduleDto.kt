package com.lettuceapp.data

class RestaurantScheduleDto (var id: Int, var restaurantID: Int,
                             var mondayOpeningTime: String?, var mondayClosingTime: String?,
                             var tuesdayOpeningTime: String?, var tuesdayClosingTime: String?,
                             var wednesdayOpeningTime: String?, var wednesdayClosingTime: String?,
                             var thursdayOpeningTime: String?, var thursdayClosingTime: String?,
                             var fridayOpeningTime: String?, var fridayClosingTime: String?,
                             var saturdayOpeningTime: String?, var saturdayClosingTime: String?,
                             var sundayOpeningTime: String?, var sundayClosingTime: String?)