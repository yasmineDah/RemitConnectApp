package com.example.remitconnectapp.domain.usecases

import com.example.remitconnectapp.domain.model.Country
import com.example.remitconnectapp.domain.repository.CountryRepository

class GetCountriesUseCase(
    private val countryRepository: CountryRepository,
) {
    operator fun invoke(): List<Country> = countryRepository.getCountries()
}