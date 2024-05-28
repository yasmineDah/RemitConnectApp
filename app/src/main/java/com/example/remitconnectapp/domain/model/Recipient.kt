package com.example.remitconnectapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Recipient (
    val firstName : String = "",
    val lastName : String = "",
    val phoneNumber : String = ""
){
    companion object {
        val default = Recipient("", "", "")
    }
}
