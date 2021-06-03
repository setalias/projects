package com.lettuceapp.data

import android.content.ContentValues
import android.content.Context
import kotlin.collections.ArrayList

class TableDomain(context : Context) {
    private val activityContext = context
    private val db = DatabaseHandler(activityContext)

    fun createTable(table : Table) : Long {
        val contentValues = ContentValues()

        // attributes of table (do not need auto-incremented values)
        contentValues.put(RestaurantId, table.restaurantId)
        contentValues.put(SeatsAmt, table.seatsAmt)
        contentValues.put(IsReserved, table.isReserved)
        contentValues.put(TableSeatingType, table.seatingType)
        contentValues.put(TableLocation, table.tableLocation)
        contentValues.put(TableXLocation, table.tableXLocation)
        contentValues.put(TableYLocation, table.tableYLocation)
        contentValues.put(TableImageId, table.tableImageId)
        contentValues.put(IsDraft, table.isDraft)

        // toast to show fail/success of added data
        val result = db.insertToDatabase(TABLENAME_Table, contentValues)
        db.close()
        return result
    }

    fun getAllTableFromRestaurantId(restaurantId : Int) : MutableList<Table>{
        val list: MutableList<Table> = ArrayList() // list to get value in table
        val query = "Select * from $TABLENAME_Table WHERE $RestaurantId = $restaurantId" // the query select ALL

        val result = db.getData(query)
        if (result != null) {
            if (result.moveToFirst()) {
                do {
                    val table = Table(
                        result.getInt(result.getColumnIndex(TableID)),
                        result.getInt(result.getColumnIndex(RestaurantId)),
                        result.getInt(result.getColumnIndex(SeatsAmt)),
                        result.getInt(result.getColumnIndex(IsReserved)),
                        result.getString(result.getColumnIndex(TableSeatingType)),
                        result.getString(result.getColumnIndex(TableLocation)),
                        result.getFloat(result.getColumnIndex(TableXLocation)),
                        result.getFloat(result.getColumnIndex(TableYLocation)),
                        result.getInt(result.getColumnIndex(TableImageId)),
                        result.getInt(result.getColumnIndex(IsDraft)))
                    list.add(table) // save to result list
                } while (result.moveToNext())
            }
        }
        return list
    }

    fun getTableFromTableId(tableId : Int) : Table? {
        val query = "Select * from $TABLENAME_Table WHERE $TableID = $tableId" // the query select ALL

        val result = db.getData(query)
        if (result != null) {
            if (result.moveToFirst()) {
                return Table(
                        result.getInt(result.getColumnIndex(TableID)),
                        result.getInt(result.getColumnIndex(RestaurantId)),
                        result.getInt(result.getColumnIndex(SeatsAmt)),
                        result.getInt(result.getColumnIndex(IsReserved)),
                        result.getString(result.getColumnIndex(TableSeatingType)),
                        result.getString(result.getColumnIndex(TableLocation)),
                        result.getFloat(result.getColumnIndex(TableXLocation)),
                        result.getFloat(result.getColumnIndex(TableYLocation)),
                        result.getInt(result.getColumnIndex(TableImageId)),
                        result.getInt(result.getColumnIndex(IsDraft)))
            }
        }
        return null
    }

    fun getTableMetadata() : MutableList<ImageMetadataDto>{
        val list: MutableList<ImageMetadataDto> = ArrayList() // list to get value in table
        val query = "Select * from $TABLENAME_ImageMetadata" // the query select ALL

        val result = db.getData(query)
        if (result != null) {
            if (result.moveToFirst()) {
                do {
                    val imageMetadataDto = ImageMetadataDto(
                            result.getInt(result.getColumnIndex(TableImageMetadataId)),
                            result.getString(result.getColumnIndex(ImageDescription)))
                    list.add(imageMetadataDto) // save to result list
                } while (result.moveToNext())
            }
        }
        return list
    }

    fun updateTableSeatingAmt(tableId : Int, seatingAmt : Int) {
        val query = "UPDATE $TABLENAME_Table SET $SeatsAmt = $seatingAmt WHERE $TableID = $tableId"
        db.updateData(query);
    }


    fun updateTableIsReserved(tableId : Int, isReserved : Int) {
        val query = "UPDATE $TABLENAME_Table SET $IsReserved = $isReserved WHERE $TableID = $tableId"
        db.updateData(query);
    }


    fun deleteTableByTableId(tableId : Int) {
        val query = "DELETE FROM $TABLENAME_Table WHERE $TableID = $tableId"
        db.deleteData(query);
    }

    fun getTableMetadataDescriptionFromId(id : Int) : ImageMetadataDto? {
        val query = "Select * from $TABLENAME_ImageMetadata WHERE $TableImageMetadataId = '$id'" // the query select ALL

        val result = db.getData(query)
        if (result != null) {
            if (result.moveToFirst()) {
                return ImageMetadataDto(
                    result.getInt(result.getColumnIndex(TableImageMetadataId)),
                    result.getString(result.getColumnIndex(ImageDescription))
                )
            }
        }
        return null
    }

    fun getTableMetadataIdFromDescription(description : String) : ImageMetadataDto? {
        val query = "Select * from $TABLENAME_ImageMetadata WHERE $ImageDescription = '$description'" // the query select ALL

        val result = db.getData(query)
        if (result != null) {
            if (result.moveToFirst()) {
                return ImageMetadataDto(
                    result.getInt(result.getColumnIndex(TableImageMetadataId)),
                    result.getString(result.getColumnIndex(ImageDescription))
                )
            }
        }
        return null
    }

}