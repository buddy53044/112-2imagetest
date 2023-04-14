package com.example.myapplication

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alexvasilkov.gestures.views.GestureImageView
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageView: GestureImageView = binding.imageView



        binding.run {


            imageView.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.test3))

            // Applying custom settings (note, that all settings can be also set in XML)


            imageView.setOnClickListener(View.OnClickListener { view: View? ->
                Toast.makeText(this@MainActivity, "oneclick", Toast.LENGTH_SHORT).show()
            })

            imageView.setOnLongClickListener(OnLongClickListener { view: View? ->
                Toast.makeText(this@MainActivity, "longclick", Toast.LENGTH_SHORT).show()

                true
            })
        }



    }
}









