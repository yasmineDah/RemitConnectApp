package com.example.remitconnectapp.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.remitconnectapp.presentation.home.HomeScreen
import com.example.remitconnectapp.presentation.home.HomeViewModel

fun NavGraphBuilder.homeNavigation() {
    composable(Screens.Home.root) {

        val viewModel: HomeViewModel = hiltViewModel()
        HomeScreen(viewModel = viewModel)
    }
}