package com.example.remitconnectapp.data.model

import com.google.gson.annotations.SerializedName

data class CountryDTO (
    val id : String? = null,
    val name : String? = null,
    @SerializedName("currency_code")
    val currencyCode : String? = null,
)