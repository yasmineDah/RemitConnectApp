package com.example.remitconnectapp.domain.model


data class RemittanceParams (
    val selectedCountry : Country? = null,
    val recipient: Recipient? = null,
    val wallet : Wallet? = null,
    val remittance : Float? = null,
    val newBalance : Float? = null,
)