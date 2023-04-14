package com.example.myapplication

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.myapplication.databinding.MenuComfirmBinding
import org.w3c.dom.Text

class MyDialogFragment : DialogFragment() {

    private var _binding: MenuComfirmBinding? = null
    private val binding get() = _binding!!

    fun setDialogText(text: String) {
        binding.tv.setText(text)
        binding.tv.isEnabled = false // 设置为false，不允许输入
        binding.add.setOnClickListener {
            Log.d("add", binding.tv.text.toString())
            binding.tv.isEnabled = true // 设置为true，允许输入
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = MenuComfirmBinding.inflate(layoutInflater)
        val view = binding.root

        val builder = AlertDialog.Builder(activity)
        builder.setView(view)
        setStyle(STYLE_NO_TITLE, 0) //把Layout中的標題設為不顯示

        // 设置 Dialog 的大小和背景颜色
        val dialog = builder.create()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setGravity(Gravity.CENTER)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        val myText = arguments?.getString("myText")
        setDialogText(myText!!) // 呼叫 setDialogText 方法，將傳入的參數設定到 binding.tv 中

//        binding.tv.text=myText
//        binding.tv.setText(myText)

        binding.add.setOnClickListener{
//            binding.tv.isEnabled = false // 啟用EditText

            Toast.makeText(requireContext(),"add", Toast.LENGTH_SHORT).show()
        }
        binding.motify.setOnClickListener{
//            binding.tv.isEnabled = true // 啟用EditText
            binding.tv.requestFocus() // 讓EditText取得焦點，並顯示輸入法
            Toast.makeText(requireContext(),"motify", Toast.LENGTH_SHORT).show()
        }
        binding.delete.setOnClickListener{
            Toast.makeText(requireContext(),"delete", Toast.LENGTH_SHORT).show()
        }


        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MenuComfirmBinding.inflate(inflater, container, false)
        val view = binding.root
        view.setBackgroundColor(Color.parseColor("#88000000"))

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
