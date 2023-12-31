package com.example.fakeapistoreapp.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.fakeapistoreapp.R
import com.example.fakeapistoreapp.databinding.ActivityMainBinding
import com.example.fakeapistoreapp.user.UserActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#146775")))

        binding.btnProducts.setOnClickListener(this)


    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.btn_products ->{
                var intent = Intent(this@MainActivity, ProductActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_carts ->{
                Toast.makeText(this, "Carts", Toast.LENGTH_SHORT).show()
            }

            R.id.btn_users ->{
                var userIntent = Intent(this@MainActivity, UserActivity::class.java)
                startActivity(userIntent)
            }
        }

    }
}