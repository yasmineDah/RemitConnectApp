package com.example.remitconnectapp.data.mapper

import com.example.remitconnectapp.data.model.WalletDTO
import com.example.remitconnectapp.domain.model.Wallet
import javax.inject.Inject

class WalletMapper @Inject constructor() : DomainMapper<WalletDTO, Wallet> {
    override fun mapToDomain(entity: WalletDTO): Wallet {
        return Wallet(
            id = entity.id ?: "",
            name = entity.name ?: ""
        )
    }
}