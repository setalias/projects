package com.example.lettuceapp

import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.lettuceapp.data.*

class RestaurantTableLayout : AppCompatActivity() {
    private var tableId = 0;
    private val tableDomain = TableDomain(this)
    private val restaurantDomain = RestaurantDomain(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_table_layout)
        val restaurantId = intent.getIntExtra("restaurantID", 0)
        val circleImageView = findViewById<ImageView>(R.id.circle)
        val squareImageView = findViewById<ImageView>(R.id.square)
        val rectangleImageView = findViewById<ImageView>(R.id.rectangle)
        val doorImageView = findViewById<ImageView>(R.id.door)
        val windowImageView = findViewById<ImageView>(R.id.window)
        val workArea = findViewById<ImageView>(R.id.restaurantWorkArea)
        val numSeatingText = findViewById<TextView>(R.id.numSeatingText)
        val editNumSeating = findViewById<EditText>(R.id.editNumSeating)
        val editNumSeatingButton = findViewById<Button>(R.id.seatingSaveButton)
        val deleteTableButton = findViewById<Button>(R.id.seatingDeleteButton)
        val layout = findViewById<ConstraintLayout>(R.id.restaurantTableLayout)

        setTableLayout(restaurantId)

        workArea.setOnClickListener {
            numSeatingText.visibility = View.INVISIBLE
            editNumSeating.visibility = View.INVISIBLE
            editNumSeatingButton.visibility = View.INVISIBLE
            deleteTableButton.visibility = View.INVISIBLE
        }

        editNumSeatingButton.setOnClickListener {
            tableDomain.updateTableSeatingAmt(tableId,editNumSeating.text.toString().toInt())
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
        }

        deleteTableButton.setOnClickListener {
            getImageToDelete(layout,tableId);
            tableDomain.deleteTableByTableId(tableId)
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
        }

        setOnLongClick(circleImageView, "circle")
        setOnLongClick(squareImageView, "square")
        setOnLongClick(rectangleImageView, "rectangle")
        setOnLongClick(doorImageView, "door")
        setOnLongClick(windowImageView, "window")

        setOnDrag(workArea,restaurantId)

        val restaurant = getRest()
        val submitBtn = findViewById<Button>(R.id.submitTableLayout)
        submitBtn.setOnClickListener {
            val intent = Intent(this, RestaurantDashboard::class.java)
            intent.putExtra("email", restaurant[0].email)
            startActivity(intent)
        }
    }

    private fun setOnLongClick(view: ImageView, tagName: String){
        val imageViewTag = tagName

        view.apply {
            tag = imageViewTag
            view.setOnLongClickListener {
                var v : View = this
                // Create a new ClipData.
                // This is done in two steps to provide clarity. The convenience method
                // ClipData.newPlainText() can create a plain text ClipData in one step.

                // Create a new ClipData.Item from the ImageView object's tag
                val item = ClipData.Item(v.tag as? CharSequence)

                // Create a new ClipData using the tag as a label, the plain text MIME type, and
                // the already-created item. This will create a new ClipDescription object within the
                // ClipData, and set its MIME type entry to "text/plain"
                val dragData = ClipData(
                        v.tag as? CharSequence,
                        arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                        item)

                // Instantiates the drag shadow builder.
                val myShadow = MyDragShadowBuilder(this)

                // Starts the drag
                v.startDrag(
                        dragData,   // the data to be dragged
                        myShadow,   // the drag shadow builder
                        null,       // no need to use local data
                        0           // flags (not currently used, set to 0)
                )
            }
        }
    }

    private fun setOnDrag(view: ImageView, restaurantId: Int){
        val context = this
        val myLayout = findViewById<ConstraintLayout>(R.id.restaurantTableLayout)

        // the work area
        val onDragListener = View.OnDragListener { v, event ->
            when(event.action){
                DragEvent.ACTION_DRAG_STARTED -> {
                    // Determines if this View can accept the dragged data
                    if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        // As an example of what your application might do,
                        // applies a blue color tint to the View to indicate that it can accept
                        // data.
                        (v as? ImageView)?.setColorFilter(BLUE)

                        // Invalidate the view to force a redraw in the new tint
                        v.invalidate()

                        // returns true to indicate that the View can accept the dragged data.
                        true
                    } else {
                        // Returns false. During the current drag and drop operation, this View will
                        // not receive events again until ACTION_DRAG_ENDED is sent.
                        false
                    }
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    // Applies a green tint to the View. Return true; the return value is ignored.
                    (v as? ImageView)?.setColorFilter(GREEN)

                    // Invalidate the view to force a redraw in the new tint
                    v.invalidate()
                    true
                }

                DragEvent.ACTION_DRAG_LOCATION ->
                    // Ignore the event
                    true
                DragEvent.ACTION_DRAG_EXITED -> {
                    // Re-sets the color tint to blue. Returns true; the return value is ignored.
                    (v as? ImageView)?.setColorFilter(BLUE)

                    // Invalidate the view to force a redraw in the new tint
                    v.invalidate()
                    true
                }

                // the sauce
                DragEvent.ACTION_DROP -> {
                    val item: ClipData.Item = event.clipData.getItemAt(0) // get item we drag

                    val dragData = item.text // get name of image file (circle, door, etc)

                    var image = ImageView(this)
                    var vp = ConstraintLayout.LayoutParams(150,150)
                    image.x = event.x + 40
                    image.y = event.y + 125
                    image.layoutParams = vp
                    image.setImageResource(getDrawableImage(dragData.toString())) // get imageID
                    myLayout.addView(image) // add image to view

                    val tableImage = getDrawableImageFromDBWithDescription(dragData.toString())
                    var table = Table(0, restaurantId,null,0,"booth", "inside", image.x, image.y, tableImage!!.imageMetadataId,1)
                    var result = tableDomain.createTable(table) // insert table to database for restId

                    // set onclick for each individual table
                    if(table.tableImageId == 1 || table.tableImageId == 2 || table.tableImageId == 3) {
                        image.setOnClickListener {
                            tableId = result.toInt()
                            val numSeatingText = findViewById<TextView>(R.id.numSeatingText)
                            val editNumSeating = findViewById<EditText>(R.id.editNumSeating)
                            val editNumSeatingButton = findViewById<Button>(R.id.seatingSaveButton)
                            val deleteTableButton = findViewById<Button>(R.id.seatingDeleteButton)

                            val table = tableDomain.getTableFromTableId(tableId)
                            if (table != null) {
                                editNumSeating.setText(table.seatsAmt!!.toString())
                            }
                            numSeatingText.visibility = View.VISIBLE
                            editNumSeating.visibility = View.VISIBLE
                            editNumSeatingButton.visibility = View.VISIBLE
                            deleteTableButton.visibility = View.VISIBLE

                        }
                    } else {
                        image.setOnClickListener {
                            tableId = result.toInt()
                            val deleteTableButton = findViewById<Button>(R.id.seatingDeleteButton)
                            deleteTableButton.visibility = View.VISIBLE
                        }
                    }

                    // Displays a message containing the dragged data.
                    Toast.makeText(context, "Dragged data is " + dragData, Toast.LENGTH_LONG).show()

                    // Turns off any color tints
                    (v as? ImageView)?.clearColorFilter()

                    // Invalidates the view to force a redraw
                    v.invalidate()

                    // Returns true. DragEvent.getResult() will return true.
                    true
                }

                DragEvent.ACTION_DRAG_ENDED -> {
                    // Turns off any color tinting
                    (v as? ImageView)?.clearColorFilter()

                    // Invalidates the view to force a redraw
                    v.invalidate()

                    // returns true; the value is ignored.
                    true
                }
                else -> {
                    // An unknown action type was received.
                    Log.e("DragDrop Example", "Unknown action type received by OnDragListener.")
                    false
                }
            }
        }
        view.setOnDragListener(onDragListener)
    }

    private class MyDragShadowBuilder(v: View) : View.DragShadowBuilder(v) {
        private val shadow = view
        // Defines a callback that draws the drag shadow in a Canvas that the system constructs
        // from the dimensions passed in onProvideShadowMetrics().
        override fun onDrawShadow(canvas: Canvas) {
            // Draws the ColorDrawable in the Canvas passed in from the system.
            shadow.draw(canvas)
        }
    }

    private fun setTableLayout(restaurantId : Int) {

        val myLayout = findViewById<ConstraintLayout>(R.id.restaurantTableLayout)
        val tableList = tableDomain.getAllTableFromRestaurantId(restaurantId)
        for(table in tableList){
            try{
                var result = tableDomain.getTableMetadata()
                if(table.tableImageId!! > result.size){
                    throw Exception("Table image does not exist in metadata table!!")
                }
            }catch(e : Exception){
                print(e)
            }

            var image = ImageView(this)
            var vp = ConstraintLayout.LayoutParams(150,150)
            image.x = table.tableXLocation
            image.y = table.tableYLocation
            image.layoutParams = vp
            val tableImage = getDrawableImageFromDB(table.tableImageId!!)
            image.setImageResource(getDrawableImage(tableImage!!.imageDescription))
            myLayout.addView(image)

            if(table.tableImageId == 1 || table.tableImageId == 2 || table.tableImageId == 3) {
                image.setOnClickListener {
                    tableId = table.id
                    val numSeatingText = findViewById<TextView>(R.id.numSeatingText)
                    val editNumSeating = findViewById<EditText>(R.id.editNumSeating)
                    val editNumSeatingButton = findViewById<Button>(R.id.seatingSaveButton)
                    val deleteTableButton = findViewById<Button>(R.id.seatingDeleteButton)

                    val table = tableDomain.getTableFromTableId(tableId)
                    if (table != null) {
                        editNumSeating.setText(table.seatsAmt!!.toString())
                    }

                    numSeatingText.visibility = View.VISIBLE
                    editNumSeating.visibility = View.VISIBLE
                    editNumSeatingButton.visibility = View.VISIBLE
                    deleteTableButton.visibility = View.VISIBLE
                }
            } else {
                image.setOnClickListener {
                    tableId = table.id
                    val deleteTableButton = findViewById<Button>(R.id.seatingDeleteButton)
                    deleteTableButton.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getDrawableImageFromDB(imageId : Int): ImageMetadataDto? {

        return tableDomain.getTableMetadataDescriptionFromId(imageId)
    }

    private fun getDrawableImageFromDBWithDescription(imageDescription : String): ImageMetadataDto? {
        return tableDomain.getTableMetadataIdFromDescription(imageDescription)
    }

    private fun getImageToDelete(layout : ConstraintLayout, tableId : Int) {
        val tableInfo = tableDomain.getTableFromTableId(tableId)
        val imageCount = layout.childCount
        for (i in 0 until imageCount){
            var image = layout.getChildAt(i)
            if(image is ImageView) {
                if (tableInfo!!.tableXLocation == image.x && tableInfo!!.tableYLocation == image.y) {
                    layout.removeView(image)
                }
            }
        }
    }

    private fun getDrawableImage(imageName: String): Int {
        return when(imageName){
            "circle" -> R.drawable.circle
            "rectangle" -> R.drawable.rectangle
            "door" -> R.drawable.door
            "window" -> R.drawable.window
            "square" -> R.drawable.square
            else ->{
                0
            }
        }
    }

    private fun getRest() : List<RestaurantDto> {
        val restID = intent.getIntExtra("restaurantID", 0)
        return restaurantDomain.getRestaurantById(restID)
    }
}