package com.example.remitconnectapp.domain.usecases

import com.example.remitconnectapp.domain.repository.WalletRepository

class GetWalletsUseCase(
    private val walletsRepository: WalletRepository
) {
    suspend operator fun invoke () = walletsRepository.geWallets()
}