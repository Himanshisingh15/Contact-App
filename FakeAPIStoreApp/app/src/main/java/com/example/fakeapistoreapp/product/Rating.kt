package com.example.fakeapistoreapp.product

import com.google.gson.annotations.SerializedName

data class Rating(

    @SerializedName("rate")
    var rate: Double,

    @SerializedName("count")
    var count: String
)
