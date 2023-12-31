package com.example.fakeapistoreapp.product

import retrofit2.Call
import retrofit2.http.GET

interface ApiMethods {

    @GET("/products")
    fun getProducts(): retrofit2.Call<ProductList>

//    @GET("/users")
//    fun getUsers(): Call<>
}