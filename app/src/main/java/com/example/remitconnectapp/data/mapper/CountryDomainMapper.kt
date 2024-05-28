package com.example.remitconnectapp.data.mapper

import com.example.remitconnectapp.data.model.CountryDTO
import com.example.remitconnectapp.data.model.CountryResponse
import com.example.remitconnectapp.domain.model.Country
import com.example.remitconnectapp.domain.model.CountryDomain
import javax.inject.Inject

class CountryDomainMapper @Inject constructor() : DomainMapper<CountryDTO, CountryDomain> {
    override fun mapToDomain(entity: CountryDTO): CountryDomain {
        return CountryDomain(
            id = entity.id ?: "",
            name = entity.name ?: "",
            currencyCode = entity.currencyCode ?: ""
        )
    }

}