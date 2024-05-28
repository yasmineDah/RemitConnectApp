package com.example.remitconnectapp.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.remitconnectapp.R
import com.example.remitconnectapp.navigation.Screens
import com.example.remitconnectapp.navigation.homeNavigation
import com.example.remitconnectapp.navigation.mobileWalletsScreen
import com.example.remitconnectapp.navigation.recipientsScreen
import com.example.remitconnectapp.navigation.sendMoneyScreen
import com.example.remitconnectapp.navigation.sendMoneyOptionsScreen
import com.example.remitconnectapp.navigation.sendToAfricaScreen
import com.example.remitconnectapp.navigation.successScreen
import com.example.remitconnectapp.ui.theme.Green

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: Painter,
)

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
) {
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    var isBottomBarVisible by remember { mutableStateOf(true) }

    navController.addOnDestinationChangedListener { _, destination, _ ->
        isBottomBarVisible = destination.route == Screens.Home.root
    }

    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = painterResource(id = R.drawable.home),
        ),
        BottomNavigationItem(
            title = "Cards",
            selectedIcon = painterResource(id = R.drawable.cards),
        ),
        BottomNavigationItem(
            title = "Tonines",
            selectedIcon = painterResource(id = R.drawable.tontines),
        ),

        BottomNavigationItem(
            title = "Settings",
            selectedIcon = painterResource(id = R.drawable.settings),
        ),
    )

    val colors = NavigationBarItemColors(
        selectedIconColor = Green,
        selectedTextColor = Green,
        selectedIndicatorColor = Color.Unspecified,
        unselectedIconColor = Color.Unspecified,
        unselectedTextColor = colorResource(id = R.color.sent_to_color),
        disabledIconColor = Color.Unspecified,
        disabledTextColor = Color.Unspecified
    )
    Scaffold(
        bottomBar = {
            if (isBottomBarVisible) {
                NavigationBar(
                    modifier = Modifier.shadow(10.dp, RoundedCornerShape(8.dp)),
                    containerColor = Color.White,
                ) {
                    items.forEachIndexed { index, item ->
                        if (index == 2) {
                            SendButton(navController)
                        }
                        NavigationBarItem(
                            colors = colors,
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                                if (selectedItemIndex == 0)
                                    navController.navigate(Screens.Home.root)
                            },
                            label = {
                                Text(text = item.title)
                            },
                            icon = {
                                Icon(
                                    painter = item.selectedIcon,
                                    contentDescription = item.title
                                )
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        RemitConnectNavHost(navController, modifier = Modifier.padding(paddingValues))
    }
}

@Composable
private fun SendButton(navController: NavHostController) {
    FloatingActionButton(
        modifier = Modifier.size(56.dp),
        shape = RoundedCornerShape(100),
        containerColor = colorResource(id = R.color.yellow),
        onClick = {
            navController.navigate(Screens.SendMoneyOptions.root)
        }) {
        Icon(
            painter = painterResource(id = R.drawable.send),
            contentDescription = "",
            tint = colorResource(id = R.color.send_icon_color)
        )
    }
}


@Composable
fun RemitConnectNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.root,
        modifier = modifier,
    ) {
        homeNavigation()

        sendMoneyOptionsScreen(
            onClick = { navController.navigate(Screens.SendToAfrica.root) },
            onBackPressed = { navController.navigateUp() }
        )

        sendToAfricaScreen(
            onItemSelected = { navController.navigate(Screens.Recipients.root) },
            onCloseClicked = { navController.navigateUp() }
        )

        recipientsScreen(
            onContinueClicked = { navController.navigate(Screens.MobileWallets.root) },
            onBackPressed = { navController.navigateUp() }
        )

        mobileWalletsScreen(onWalletSelected = {
            navController.navigate(Screens.SendMoney.root)
        }, onBackPressed = { navController.navigateUp() })

        sendMoneyScreen (onConfirmClicked = {
            navController.navigate(Screens.Success.root)
        }, onCloseClicked = { navController.navigateUp() })

        successScreen {
            navController.navigate(Screens.Home.root)
        }

    }
}

@Preview
@Composable
internal fun BottomNavigationBarPreview() {
    val navController = rememberNavController()
    BottomNavigationBar(
        navController = navController
    )
}