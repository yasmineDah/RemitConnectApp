package com.example.remitconnectapp.presentation.mobileWallets

import com.example.remitconnectapp.domain.model.Wallet

data class WalletsScreenState (
     val items : List<Wallet> = emptyList(),
     val isLoading : Boolean = true,
)