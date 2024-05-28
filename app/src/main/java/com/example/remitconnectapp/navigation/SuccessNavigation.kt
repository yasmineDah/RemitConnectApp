package com.example.remitconnectapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.remitconnectapp.presentation.SuccessScreen


fun NavGraphBuilder.successScreen(
    onContinueClicked: () -> Unit
) {
    composable(Screens.Success.root) {

        SuccessScreen(
            onClick = onContinueClicked
        )
    }
}