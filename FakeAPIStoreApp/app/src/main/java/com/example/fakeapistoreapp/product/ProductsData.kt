package com.example.fakeapistoreapp.product

import com.google.gson.annotations.SerializedName

data class ProductsData(

    @SerializedName("id")
    var id: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("price")
    var price: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("category")
    var category: String,

    @SerializedName("image")
    var image: String,

    @SerializedName("rating")
    var rating: Rating
)
