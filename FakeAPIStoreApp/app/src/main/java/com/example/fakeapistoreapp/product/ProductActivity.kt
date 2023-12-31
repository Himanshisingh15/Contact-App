package com.example.fakeapistoreapp.product

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakeapistoreapp.R
import com.example.fakeapistoreapp.databinding.ActivityProductBinding

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    private lateinit var viewModel: ApiViewModel
    private lateinit var productAdapter: RecyclerViewApadter
    lateinit var progressBar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product)
        viewModel = ViewModelProvider(this)[ApiViewModel::class.java]

        progressBar = ProgressDialog(this)
        progressBar.setTitle("Loading")
        progressBar.setMessage("Loading, Please wait...")
        progressBar.setCancelable(false)
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressBar.show()

        viewModel.isLoading.observe(this, Observer {
            if (it) {
                progressBar.show()
            } else {
                progressBar.dismiss()
            }
        })

        viewModel.getProducts().observe(this) {

            productAdapter = RecyclerViewApadter(this, it, object : onItemRecyclerClickListener{
                override fun onItemClick(position: Int, productList: ProductList) {
                    productDetail(
                        productList[position].title,
                        productList[position].description,
                        productList[position].category,
                        productList[position].price,
                        productList[position].image,
                        productList[position].rating.rate,
                        productList[position].rating.count)


                }
            })
            binding.rvProductData.layoutManager = LinearLayoutManager(this)
            binding.rvProductData.adapter = productAdapter
            productAdapter.notifyDataSetChanged()

        }

    }

    fun productDetail(
        title: String,
        description: String,
        category: String,
        price: String,
        image: String,
        rate: Double,
        count: String
    ) {
        var intentDetails = Intent(this@ProductActivity, CardViewActivity::class.java)

        intentDetails.putExtra("title", title)
        intentDetails.putExtra("description", description)
        intentDetails.putExtra("category", category)
        intentDetails.putExtra("price", price)
        intentDetails.putExtra("image", image)
        intentDetails.putExtra("rating", rate)
        intentDetails.putExtra("ratingCount", count)
        startActivity(intentDetails)
    }

}