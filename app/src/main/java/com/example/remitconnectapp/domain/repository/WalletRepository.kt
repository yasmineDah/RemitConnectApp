package com.example.remitconnectapp.domain.repository

import com.example.remitconnectapp.domain.model.DataState
import com.example.remitconnectapp.domain.model.Wallet
import kotlinx.coroutines.flow.Flow

interface WalletRepository {

    suspend fun geWallets () : Flow<DataState<List<Wallet>>>


    fun getBalance() : Float

    fun updateBalance(balance : Float)
}