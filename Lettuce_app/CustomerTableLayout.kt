package com.example.lettuceapp

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.lettuceapp.data.*

class CustomerTableLayout : AppCompatActivity() {
    private val reservationDomain = ReservationDomain(this)
    private var tableId = 0
    private var clickedImage : ImageView? = null
    private val tableDomain = TableDomain(this)

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_table_layout)

        val restaurantId = intent.getIntExtra("restaurantID", 0)

        val workArea = findViewById<ImageView>(R.id.customerWorkArea)
        val tableIDText = findViewById<TextView>(R.id.tableIDText)
        val tableIDNum = findViewById<TextView>(R.id.tableIDNum)
        val submit = findViewById<Button>(R.id.submitSelection)

        setTableLayout(restaurantId)

        workArea.setOnClickListener {
            tableIDText.visibility = View.INVISIBLE
            tableIDNum.visibility = View.INVISIBLE
            submit.visibility = View.INVISIBLE
        }
    }

    private fun setTableLayout(restaurantId: Int) {
        val emailFromLastActivity = intent.getStringExtra("email")
        val myLayout = findViewById<ConstraintLayout>(R.id.customerTableLayout)
        val tableList = tableDomain.getAllTableFromRestaurantId(restaurantId)
        for (table in tableList) {
            try{
                var result = tableDomain.getTableMetadata()
                if(table.tableImageId!! > result.size){
                    throw Exception("Table image does not exist in metadata table!!")
                }
            }catch(e : Exception){
                print(e)
            }

            var image = ImageView(this)
            var vp = ConstraintLayout.LayoutParams(150, 150)
            image.x = table.tableXLocation
            image.y = table.tableYLocation
            image.layoutParams = vp
            val tableImage = getDrawableImageFromDB(table.tableImageId!!)
            image.setImageResource(getDrawableImage(tableImage!!.imageDescription))
            myLayout.addView(image)

            tableId = table.id

            if((tableId % 2 == 0) && (table.tableImageId == 1 || table.tableImageId == 2 || table.tableImageId == 3)){
                    image.setColorFilter(Color.parseColor("darkGrey"))
            }

            if ((tableId % 2 != 0) && (table.tableImageId == 1 || table.tableImageId == 2 || table.tableImageId == 3)) {
                image.setOnClickListener {

                    tableId = table.id

                    val tableIDText = findViewById<TextView>(R.id.tableIDText)
                    val tableIDNum = findViewById<TextView>(R.id.tableIDNum)
                    val submit = findViewById<Button>(R.id.submitSelection)
                    val table = tableDomain.getTableFromTableId(tableId)

                    if (table != null && clickedImage == null) {
                        //tableIDNum.text = tableId.toString()
                        image.setColorFilter(Color.parseColor("#046704"))
                        clickedImage = image
                    }
                    else if (table != null) {
                        clickedImage?.colorFilter = null
                        //tableIDNum.text = tableId.toString()
                        image.setColorFilter(Color.parseColor("#046704"))
                        clickedImage = image
                    }

                    tableIDText.visibility = View.VISIBLE
                    tableIDNum.visibility = View.VISIBLE
                    submit.visibility = View.VISIBLE

                    submit.setOnClickListener {

                        var isSuccess = createReservation(tableId.toString().toInt())
                        if (isSuccess) {

                            tableDomain.updateTableIsReserved(tableId, 1)

                            val intent = Intent(this, CustomerDashboard::class.java)
                            intent.putExtra("email", emailFromLastActivity)
                            startActivity(intent)

                        }
                    }
                }
            }
        }
    }

    private fun createReservation(passTableId: Int): Boolean {
        val restaurantId = intent.getIntExtra("restaurantID", 0)
        val customerId = intent.getIntExtra("customerID", 0)
        val tableId = passTableId
        val reservationDate = intent.getStringExtra("reservationDate").toString()
        val timeSlot = intent.getStringExtra("timeSlot").toString()
        val guestAmt = intent.getIntExtra("guestAmt", 0)


        val reservationDto = ReservationDto(
            0,
                restaurantId,
                customerId,
                tableId,
                reservationDate,
                timeSlot,
                guestAmt,
                1
        )
        reservationDomain.createReservation(reservationDto)

        return true
    }



    private fun getDrawableImageFromDB(imageId: Int): ImageMetadataDto? {

        return tableDomain.getTableMetadataDescriptionFromId(imageId)
    }

    private fun getDrawableImageFromDBWithDescription(imageDescription: String): ImageMetadataDto? {

        return tableDomain.getTableMetadataIdFromDescription(imageDescription)
    }

    private fun getDrawableImage(imageName: String): Int {
        return when (imageName) {
            "circle" -> R.drawable.circle
            "rectangle" -> R.drawable.rectangle
            "door" -> R.drawable.door
            "window" -> R.drawable.window
            "square" -> R.drawable.square
            else -> {
                0
            }
        }
    }
}