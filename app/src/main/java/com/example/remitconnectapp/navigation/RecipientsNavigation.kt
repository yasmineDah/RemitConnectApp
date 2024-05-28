package com.example.remitconnectapp.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.remitconnectapp.presentation.recipients.RecipientsScreen
import com.example.remitconnectapp.presentation.recipients.RecipientsViewModel


fun NavGraphBuilder.recipientsScreen(
    onBackPressed: () -> Unit,
    onContinueClicked: () -> Unit,
) {

    composable(Screens.Recipients.root) {

        val viewModel: RecipientsViewModel = hiltViewModel()
        RecipientsScreen(
            onRecipientSelected = {
                viewModel.handleRecipientSelected(recipient = it)
                onContinueClicked()
            },
            viewModel = viewModel,
            onCloseClicked = onBackPressed,
            onContinueClicked = {
                viewModel.handleContinueClicked(it)
                onContinueClicked()
            },

        )
    }
}