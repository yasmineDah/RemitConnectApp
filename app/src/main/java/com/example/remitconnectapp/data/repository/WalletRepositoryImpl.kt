package com.example.remitconnectapp.data.repository

import com.example.remitconnectapp.data.RemittanceParamsService
import com.example.remitconnectapp.data.mapper.WalletMapper
import com.example.remitconnectapp.domain.model.DataState
import com.example.remitconnectapp.domain.model.Wallet
import com.example.remitconnectapp.domain.repository.WalletRepository
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class WalletRepositoryImpl(
    private val service: RemittanceParamsService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val walletMapper: WalletMapper,
) : WalletRepository {
    private var balance = 320f

    override suspend fun geWallets(): Flow<DataState<List<Wallet>>> = flow {

        service.getWallets().suspendOnSuccess {
            emit(DataState.Success(this.data.map {
                walletMapper.mapToDomain(it)
            }))
        }.suspendOnError {
            emit(DataState.Error())
        }.suspendOnException {
            emit(DataState.Error(Exception(exception)))
        }
    }
        .onStart { emit(DataState.Loading) }
        .flowOn(ioDispatcher)


    // in real world scenario, we should get this from backend
    override fun getBalance(): Float = balance


    override fun updateBalance(balance: Float) {
        this.balance = balance
    }
}