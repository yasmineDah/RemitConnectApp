package com.example.remitconnectapp.navigation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.remitconnectapp.presentation.SendMoneyOptionsScreen


fun NavGraphBuilder.sendMoneyOptionsScreen(
    onClick: () -> Unit,
    onBackPressed : () -> Unit,
) {

    composable(Screens.SendMoneyOptions.root) {
        SendMoneyOptionsScreen(
            onItemSelected = onClick,
            onCloseClicked =  onBackPressed,

        )
    }
}