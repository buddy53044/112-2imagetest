package com.example.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.res.Resources
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.alexvasilkov.gestures.views.GestureImageView
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.MenuComfirmBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.chinese.ChineseTextRecognizerOptions
import com.google.mlkit.vision.text.latin.TextRecognizerOptions


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recognizer = TextRecognition.getClient(ChineseTextRecognizerOptions.Builder().build())
        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.test3)
        val image = InputImage.fromBitmap(bitmap, 0)
        val imageView: GestureImageView = binding.imageView
        imageView.controller.settings.isZoomEnabled = true
        imageView.controller.settings.isRotationEnabled = true
        imageView.controller.settings.minZoom = 1f
        imageView.controller.settings.maxZoom = 10f


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
                val textPositions = mutableListOf<Pair<RectF, String>>()
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

//                binding.imageView.setImageBitmap(mutableBitmap)



                // 設定點擊事件
                binding.imageView.setOnTouchListener { v, event ->
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        // 取得反矩陣
                        val inverseMatrix = Matrix()
                        binding.imageView.imageMatrix.invert(inverseMatrix)

                        // 將點擊事件的座標轉換成 ImageView 中的座標
                        val point = floatArrayOf(event.x, event.y)
                        inverseMatrix.mapPoints(point)

                        // 搜尋點擊的位置是否在文字的範圍內
                        for (textPosition in textPositions) {
                            if (textPosition.first.contains(point[0], point[1])) {

//                                val dialogFragment = MyDialogFragment()
//                                val bundle = Bundle()
//                                bundle.putString("myText",textPosition.second)
//                                dialogFragment.arguments = bundle
//                                dialogFragment.show(supportFragmentManager, "my_dialog_fragment")

                                val mBinding = MenuComfirmBinding.inflate(layoutInflater)
                                showCenterDialog(this, true, mBinding, true)?.let {
                                    mBinding.run {
                                        add.setOnClickListener {
                                            Toast.makeText(this@MainActivity, "Add", Toast.LENGTH_SHORT).show()
                                        }
                                        motify.setOnClickListener {
                                            Toast.makeText(this@MainActivity, "motify", Toast.LENGTH_SHORT).show()
                                        }
                                        delete.setOnClickListener {
                                            Toast.makeText(this@MainActivity, "delete", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                                // 點擊的位置在文字的範圍內，顯示 Toast
                                Toast.makeText(this, textPosition.second, Toast.LENGTH_SHORT).show()
                                break
                            }
                        }
                    }
                    true
                }
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
                Log.e("failure", e.message.toString())
            }

    }

    private var dialog: Dialog? = null

    fun showCenterDialog(activity: Activity, cancelable: Boolean, view: ViewBinding, keyboard: Boolean): ViewBinding {
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









