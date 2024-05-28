package com.example.remitconnectapp.data.model

import com.google.gson.annotations.SerializedName



data class RecipientDTO(
    @SerializedName("id")
    val id : String? = null,
    @SerializedName("name")
    val name : String? = null,
    @SerializedName("country")
    val country : String? = null,
    @SerializedName("mobile_wallet")
    val mobileWallet : String? = null,
)
