package com.example.remitconnectapp.presentation.recipients.countryCode

import com.example.remitconnectapp.domain.model.Country


data class CountryCodeScreenState(
    val countries: List<Country> = listOf(),
    val researchedCountries: List<Country> = listOf(),
    val selectedCountryCode: String? = "",
)

