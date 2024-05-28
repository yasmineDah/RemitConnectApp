package com.example.remitconnectapp.presentation.mobileWallets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.remitconnectapp.R
import com.example.remitconnectapp.domain.model.Wallet
import com.example.remitconnectapp.presentation.BackIcon
import com.example.remitconnectapp.util.firstTitleStyle
import com.example.remitconnectapp.util.recipientStyle


@Composable
fun MobileWalletsScreen(
    viewModel: MobileWalletsViewModel,
    onWalletSelected: (Wallet) -> Unit,
    onCloseClicked: () -> Unit,
) {
    MobileWalletsScreenContent(
        state = viewModel.uiState,
        onWalletSelected = onWalletSelected,
        onCloseClicked = onCloseClicked
    )
}

@Composable
fun MobileWalletsScreenContent(
    state: WalletsScreenState,
    modifier: Modifier = Modifier,
    onWalletSelected: (Wallet) -> Unit,
    onCloseClicked: () -> Unit,
) {

    Column(modifier = modifier.padding(horizontal = 24.dp)) {
        Header(
            onCloseClicked = onCloseClicked,
            modifier = Modifier
        )

        Spacer(modifier = Modifier.size(24.dp))

        if (state.isLoading)
            ProgressIndicator(modifier = Modifier)
        else
            WalletsList(state = state, onWalletSelected = onWalletSelected)

    }

}

@Composable
fun WalletsList(
    state: WalletsScreenState,
    onWalletSelected: (Wallet) -> Unit
) {
    LazyColumn {
        items(state.items) {
            WalletItem(
                onWalletSelected = onWalletSelected,
                wallet = it
            )
            Spacer(modifier = Modifier.size(24.dp))
        }
    }
}

@Composable
fun WalletItem(
    onWalletSelected: (Wallet) -> Unit,
    wallet: Wallet
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.greyscale_300),
                shape = RoundedCornerShape(8.dp),
            )
            .height(72.dp)
            .padding(start = 16.dp)
            .fillMaxWidth()
            .clickable { onWalletSelected(wallet) }
    ) {


        WalletImage(iconId = R.drawable.mtn)

        Text(
            text = wallet.name ?: "Wave",
            style = recipientStyle,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}


@Composable
private fun Header(
    onCloseClicked: () -> Unit,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        BackIcon(onCloseClicked = onCloseClicked)
    }
    Spacer(modifier = modifier.size(8.dp))

    Text(
        text = "Choose a mobile wallet", style = firstTitleStyle, modifier = modifier
    )
}

@Composable
fun WalletImage(
    @DrawableRes iconId: Int
) {
    Card(
        modifier =
        Modifier
            .size(40.dp)
    ) {
        Image(
            modifier =
            Modifier
                .clip(RoundedCornerShape(12.dp)),
            painter = painterResource(id = iconId),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun ProgressIndicator(modifier: Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            modifier =
            modifier
                .size(32.dp)
                .padding(top = 20.dp),
            color = colorResource(R.color.green1),
        )
    }
}

@Preview
@Composable
fun MobileWalletsScreenContentPreview() {
    val state = WalletsScreenState()
    MobileWalletsScreenContent(
        onCloseClicked = {},
        state = state,
        onWalletSelected = {},
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    )
}
