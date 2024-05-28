package com.example.remitconnectapp.presentation.sendMoney

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.remitconnectapp.domain.repository.WalletRepository
import com.example.remitconnectapp.domain.usecases.GetRemittanceParamsUseCase
import com.example.remitconnectapp.domain.usecases.UpdateRemittanceParamsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SendMoneyViewModel @Inject constructor(
    private val walletRepository: WalletRepository,
    private val getRemittanceParamsUseCase: GetRemittanceParamsUseCase,
    private val updateRemittanceParamsUseCase: UpdateRemittanceParamsUseCase,
) : ViewModel() {

    var uiState by mutableStateOf(SendMoneyScreenState())
        private set

    private val currentBalance = walletRepository.getBalance()

    init {
        uiState =
            uiState.copy(currentBalance = walletRepository.getBalance())
    }

    fun getRemittanceParams() =
        getRemittanceParamsUseCase().copy(
            remittance = uiState.amountToSendConverted,
            newBalance = uiState.currentBalance
        )


    fun handleAmountConversion(amount: String) {
        try {
            val amountFormatted = amount.toInt()
            val currentBalance = currentBalance - amountFormatted

            val transactionState =
                if (currentBalance > 0)
                    TransactionState.Success
                else TransactionState.Error


            uiState = uiState.copy(
                amountToSend = amountFormatted,
                currentBalance = currentBalance,
                transactionState = transactionState,
                amountToSendConverted = amount.toFloat() * uiState.conversionRate
            )
        } catch (e: NumberFormatException) {

        }


    }

    fun handleContinueClicked() {
        updateRemittanceParamsUseCase(
            getRemittanceParamsUseCase().copy(
                remittance = uiState.amountToSendConverted,
                newBalance = uiState.currentBalance
            )
        )
    }

    fun updateWalletBalance(newBalance: Float) {
        walletRepository.updateBalance(newBalance)
    }

    fun handleEmptyState() {
        uiState = uiState.copy(
            currentBalance = currentBalance,
            transactionState = TransactionState.Idle,
            amountToSend = 0,
            amountToSendConverted = 0f
        )
    }


}