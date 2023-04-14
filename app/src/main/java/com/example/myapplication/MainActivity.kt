package com.example.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.alexvasilkov.gestures.views.GestureImageView
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.chinese.ChineseTextRecognizerOptions


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var textPositions = mutableListOf<Pair<RectF, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val recognizer = TextRecognition.getClient(ChineseTextRecognizerOptions.Builder().build())
        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.test3)
        val image = InputImage.fromBitmap(bitmap, 0)

        val gestureDetector  = GestureDetector(this@MainActivity, MyGestureDetectorListener())

        val imageView: GestureImageView = binding.imageView

        recognizer.process(image)
            .addOnSuccessListener { visionText ->

                val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)

                val canvas = Canvas(mutableBitmap)

                val paint = Paint().apply {
                    color = Color.RED
                    textSize = 50f
                    style = Paint.Style.FILL
                }

                // 紀錄文字的位置和內容
//                val textPositions = mutableListOf<Pair<RectF, String>>()
                for (block in visionText.textBlocks) {
                    for (line in block.lines) {
                        val rect = RectF(
                            line.boundingBox!!.left.toFloat(),
                            line.boundingBox!!.top.toFloat(),
                            line.boundingBox!!.right.toFloat(),
                            line.boundingBox!!.bottom.toFloat()
                        )
                        val text = line.text
                        canvas.drawText(text, rect.left, rect.top, paint)
                        textPositions.add(Pair(rect, text))
                    }
                }

                binding.imageView.setImageBitmap(mutableBitmap)


                imageView.setOnClickListener { view ->
                    val x = MotionEvent.getX()
                    val y = event.getY()
                    // 在這裡使用 x 和 y 的值做點擊位置相關的處理
                }






            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
                Log.e("failure", e.message.toString())
            }





    }

    private var dialog: Dialog? = null

    fun showCenterDialog(
        activity: Activity,
        cancelable: Boolean,
        view: ViewBinding,
        keyboard: Boolean
    ): ViewBinding {
        cancelCenterDialog()
        dialog = AlertDialog.Builder(activity).create()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.show()

        if (keyboard) // 顯示鍵盤
            dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)

        dialog?.setContentView(view.root)
        dialog?.setCancelable(cancelable)

        return view
    }

    fun cancelCenterDialog() = dialog?.dismiss()


}









