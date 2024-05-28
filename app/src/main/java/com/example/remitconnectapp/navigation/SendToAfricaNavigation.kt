package com.example.remitconnectapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.remitconnectapp.presentation.SendToAfricaScreen


fun NavGraphBuilder.sendToAfricaScreen(
    onItemSelected: () -> Unit,
    onCloseClicked : () -> Unit,
) {
    composable(Screens.SendToAfrica.root) {
        SendToAfricaScreen(
            onItemSelected = onItemSelected,
            onCloseClicked = onCloseClicked
        )
    }
}