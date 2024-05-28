package com.example.remitconnectapp.presentation.mobileWallets

import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.isLiveLiteralsEnabled
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remitconnectapp.data.model.WalletDTO
import com.example.remitconnectapp.domain.model.DataState
import com.example.remitconnectapp.domain.model.Wallet
import com.example.remitconnectapp.domain.repository.WalletRepository
import com.example.remitconnectapp.domain.usecases.GetRemittanceParamsUseCase
import com.example.remitconnectapp.domain.usecases.GetWalletsUseCase
import com.example.remitconnectapp.domain.usecases.UpdateRemittanceParamsUseCase
import com.example.remitconnectapp.presentation.recipients.RecipientsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MobileWalletsViewModel @Inject constructor(
    private val getWalletsUseCase: GetWalletsUseCase,
    private val updateRemittanceParamsUseCase: UpdateRemittanceParamsUseCase,
    private val getRemittanceParamsUseCase: GetRemittanceParamsUseCase
) : ViewModel() {
    var uiState by mutableStateOf(WalletsScreenState())
        private set

    init {
        getMobileWallets()
    }

    private fun getMobileWallets() {
        viewModelScope.launch {
            getWalletsUseCase().collect { dataState ->
                when (dataState) {
                    DataState.Loading -> {
                        uiState = uiState.copy(isLoading = true)
                    }

                    is DataState.Success -> {
                        uiState = uiState.copy(isLoading = false, items = dataState.data)
                    }

                    else -> {

                    }
                }
            }
        }
    }

    fun handleWalletSelected(wallet: Wallet) {
        updateRemittanceParamsUseCase(
            getRemittanceParamsUseCase().copy(wallet = wallet)
        )
    }


}