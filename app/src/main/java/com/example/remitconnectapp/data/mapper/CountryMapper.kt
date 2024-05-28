package com.example.remitconnectapp.data.mapper

import com.example.remitconnectapp.data.model.CountryResponse
import com.example.remitconnectapp.domain.model.Country
import javax.inject.Inject


class CountryMapper @Inject constructor(): DomainMapper<CountryResponse, Country> {
    override fun mapToDomain(entity: CountryResponse) =
        Country(
            name = entity.name ?: "",
            dialCode = entity.dialCode ?: "",
            code = entity.code ?: "",
            flag = entity.flag ?: "",
        )
}