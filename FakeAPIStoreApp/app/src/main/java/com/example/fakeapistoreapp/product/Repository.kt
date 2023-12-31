package com.example.fakeapistoreapp.product

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    var productLiveData: MutableLiveData<ProductList> = MutableLiveData()
    var isLoading : MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = true
    }

    fun getProducts(): MutableLiveData<ProductList> {

        var call = RetrofitProducts.apiCall.getProducts()
        call.enqueue(object : Callback<ProductList> {
            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                if (response.isSuccessful) {
                    isLoading.value = false

                    productLiveData.value = response.body()!!
                }
            }

            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                isLoading.value = false
                Log.d("Failure", "onFailure: ${t.message}")
            }
        })

        return productLiveData
    }
}