package com.example.remitconnectapp.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.remitconnectapp.presentation.mobileWallets.MobileWalletsScreen
import com.example.remitconnectapp.presentation.mobileWallets.MobileWalletsViewModel


fun NavGraphBuilder.mobileWalletsScreen(
    onWalletSelected : () -> Unit,
    onBackPressed : () -> Unit,
) {
    composable(Screens.MobileWallets.root) {
        val viewModel: MobileWalletsViewModel = hiltViewModel()
        MobileWalletsScreen(
            onWalletSelected = {
                viewModel.handleWalletSelected(it)
                onWalletSelected()
            },
            viewModel = viewModel,
            onCloseClicked = onBackPressed
        )
    }
}