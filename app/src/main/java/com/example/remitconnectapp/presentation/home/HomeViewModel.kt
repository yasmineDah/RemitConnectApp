package com.example.remitconnectapp.presentation.home

import androidx.lifecycle.ViewModel
import com.example.remitconnectapp.domain.repository.WalletRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val walletRepository: WalletRepository
) : ViewModel() {

    fun getUserBalance() : Float {
        return walletRepository.getBalance()
    }
}