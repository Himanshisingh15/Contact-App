package com.example.fakeapistoreapp.user

import com.google.gson.annotations.SerializedName

data class name(
    @SerializedName("firstname")
    var firstname: String,

    @SerializedName("lastname")
    var lastname: String,

    @SerializedName("price")
    var price: String,

)
