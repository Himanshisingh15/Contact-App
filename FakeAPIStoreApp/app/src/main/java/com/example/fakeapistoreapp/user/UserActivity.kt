package com.example.fakeapistoreapp.user

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakeapistoreapp.product.ApiViewModel
import com.example.fakeapistoreapp.R
import com.example.fakeapistoreapp.product.RecyclerViewApadter
import com.example.fakeapistoreapp.databinding.ActivityUserBinding
import com.example.fakeapistoreapp.product.onItemRecyclerClickListener

class UserActivity: AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private lateinit var viewModel: ApiViewModel
    private lateinit var productAdapter: RecyclerViewApadter
    lateinit var progressBar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_product)
        viewModel = ViewModelProvider(this)[ApiViewModel::class.java]

        progressBar = ProgressDialog(this)
        progressBar.setTitle("Loading")
        progressBar.setMessage("Loading, Please wait...")
        progressBar.setCancelable(false)
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressBar.show()

        viewModel.isLoading.observe(this, Observer {
            if (it){
                progressBar.show()
            }else{
                progressBar.dismiss()
            }
        })

        viewModel.getProducts().observe(this) {

//            productAdapter = RecyclerViewApadter(this, it, object : onItemRecyclerClickListener {
//
//            })
            binding.rvUserData.layoutManager = LinearLayoutManager(this)
            binding.rvUserData.adapter = productAdapter
            productAdapter.notifyDataSetChanged()

        }

    }
}