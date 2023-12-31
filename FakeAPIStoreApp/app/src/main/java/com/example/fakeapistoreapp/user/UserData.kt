package com.example.fakeapistoreapp.user

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("id")
    var id: Int,

    @SerializedName("email")
    var email: String,

    @SerializedName("username")
    var username: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("name")
    var name: name

)
