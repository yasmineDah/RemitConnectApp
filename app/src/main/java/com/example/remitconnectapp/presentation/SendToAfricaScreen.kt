package com.example.remitconnectapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.remitconnectapp.R
import com.example.remitconnectapp.util.firstTitleStyle

@Composable
fun SendToAfricaScreen(
    onItemSelected: () -> Unit,
    onCloseClicked: () -> Unit,
) {
    SendToAfricaScreenContent(onItemSelected = onItemSelected, onCloseClicked = onCloseClicked)
}

@Composable
fun SendToAfricaScreenContent(
    modifier: Modifier = Modifier,
    onItemSelected: () -> Unit,
    onCloseClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Header(modifier, onCloseClicked)

        HorizontalDivider()

        SendingMoneyMethod(R.drawable.go_back_filled, "Mobile wallets", onItemSelected)

        HorizontalDivider()

        SendingMoneyMethod(R.drawable.go_back_filled, "Bank transfer", onItemSelected)
        HorizontalDivider()


    }

}

@Composable
private fun Header(modifier: Modifier, onCloseClicked: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(end = 24.dp, top = 8.dp, bottom = 8.dp, start = 24.dp)
    ) {
        BackIcon(onCloseClicked = onCloseClicked)
    }
    Spacer(modifier = modifier.size(8.dp))

    Text(
        text = "Send To Africa", style = firstTitleStyle, modifier = modifier
            .padding(start = 24.dp, bottom = 24.dp)
    )
}


@Composable
fun BackIcon(
    onCloseClicked: () -> Unit,
) {
    Box(
        modifier =
        Modifier
            .size(32.dp)
            .background(
                color = colorResource(id = R.color.greyscale_100),
                shape = RoundedCornerShape(12.dp),
            )
            .clickable { onCloseClicked() },
    ) {
        Image(
            painter = painterResource(id = R.drawable.go_back),
            modifier = Modifier.align(Alignment.Center),
            contentDescription = "",
        )
    }
}


@Preview
@Composable
internal fun SendToAfricaScreenContentPreview() {
    SendToAfricaScreenContent(
        modifier = Modifier.background(color = Color.White),
        {}, {}
    )
}
