package com.example.fakeapistoreapp.product

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProducts {

    private val retrofitProducts: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
    }

    val apiCall: ApiMethods by lazy {
        retrofitProducts.build().create(ApiMethods::class.java)
    }

}


