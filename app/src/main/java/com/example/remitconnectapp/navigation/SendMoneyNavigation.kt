package com.example.remitconnectapp.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.remitconnectapp.presentation.sendMoney.SendMoneyScreen
import com.example.remitconnectapp.presentation.sendMoney.SendMoneyViewModel


fun NavGraphBuilder.sendMoneyScreen(
    onConfirmClicked: () -> Unit,
    onCloseClicked: () -> Unit
) {
    composable(Screens.SendMoney.root) {

        val viewModel: SendMoneyViewModel = hiltViewModel()
        SendMoneyScreen(
            viewModel = viewModel,
            onConfirmClicked = {
                viewModel.updateWalletBalance(it)
                onConfirmClicked()
            },
            onCloseClicked = onCloseClicked
        )
    }
}