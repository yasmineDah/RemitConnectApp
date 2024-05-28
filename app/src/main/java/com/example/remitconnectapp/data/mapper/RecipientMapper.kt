package com.example.remitconnectapp.data.mapper

import com.example.remitconnectapp.data.model.RecipientDTO
import com.example.remitconnectapp.domain.model.RecipientDomain
import javax.inject.Inject

class RecipientMapper @Inject constructor() : DomainMapper<RecipientDTO, RecipientDomain> {
    override fun mapToDomain(entity: RecipientDTO): RecipientDomain {
        return RecipientDomain(
            id = entity.id ?: "",
            name = entity.name ?: "",
            country = entity.country ?: "",
            mobileWallet = entity.country?: "",
        )
    }
}