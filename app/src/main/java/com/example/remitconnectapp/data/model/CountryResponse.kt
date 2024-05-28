package com.example.remitconnectapp.data.model

import com.google.gson.annotations.SerializedName

data class CountryResponse (
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("dial_code")
    val dialCode: String? = null,
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("flag")
    val flag: String? = null,
)