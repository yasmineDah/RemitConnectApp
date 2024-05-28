package com.example.remitconnectapp.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.remitconnectapp.util.recipientStyle


data class OptionData(
    val title: String,
    @DrawableRes val iconId: Int
)

@Composable
fun SendMoneyOptionsScreen(
    onItemSelected: () -> Unit,
    onCloseClicked: () -> Unit
) {
    SendMoneyOptionsContent(
        onItemSelected = onItemSelected,
        onCloseClicked = onCloseClicked
    )
}

@Composable
fun SendMoneyOptionsContent(
    modifier: Modifier = Modifier,
    onItemSelected: () -> Unit,
    onCloseClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Header(modifier, onCloseClicked)

        HorizontalDivider()

        val items = listOf(
            OptionData("To Moneco balance", R.drawable.to_moneco_balance),
            OptionData("Bank transfer", R.drawable.bank),
            OptionData("Send to Africa", R.drawable.africa)
        )

        OptionsList(items, onItemSelected)
    }

}

@Composable
fun OptionsList(
    options: List<OptionData>,
    onItemSelected: () -> Unit,
) {
    LazyColumn {
        items(options) {
            SendingMoneyMethod(it.iconId, it.title, onItemSelected)
            HorizontalDivider()
        }
    }

}

@Composable
private fun Header(modifier: Modifier, onCloseClicked: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(end = 24.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Spacer(Modifier.weight(1f))
        CloseIcon(onCloseClicked = onCloseClicked)
    }
    Spacer(modifier = modifier.size(8.dp))

    Text(
        text = "Send Money", style = firstTitleStyle, modifier = modifier
            .padding(start = 24.dp, bottom = 24.dp)
    )
}

@Composable
fun SendingMoneyMethod(
    @DrawableRes iconId: Int, text: String,
    onItemSelected: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(72.dp)
            .padding(start = 24.dp, end = 24.dp)
            .clickable { onItemSelected() }
    ) {
        PaymentIcon(iconId = iconId)


        Text(text = text, style = recipientStyle, modifier = Modifier.padding(start = 16.dp))

        Spacer(Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.arrow_go),
            contentDescription = "",
            modifier = Modifier
                .height(16.dp)
                .width(16.dp)

        )

    }
}

@Composable
fun PaymentIcon(
    @DrawableRes iconId: Int
) {
    Box(
        modifier =
        Modifier
            .height(40.dp)
            .width(40.dp)
            .background(
                color =
                colorResource(
                    id = R.color.lighter_green2
                ),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Image(
            painter = painterResource(id = iconId),
            modifier = Modifier.align(Alignment.Center),
            contentDescription = "",
        )
    }
}

@Composable
fun CloseIcon(
    onCloseClicked: () -> Unit
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
            painter = painterResource(id = R.drawable.close),
            modifier = Modifier.align(Alignment.Center),
            contentDescription = "",
        )
    }
}


@Preview
@Composable
internal fun SendMoneyOptionsContentPreview() {
    SendMoneyOptionsContent(
        modifier = Modifier.background(color = Color.White),
        onItemSelected = {}
    )

}
