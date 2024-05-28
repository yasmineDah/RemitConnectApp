package com.example.remitconnectapp.presentation.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.remitconnectapp.R
import com.example.remitconnectapp.presentation.SetStatusBarColor
import com.example.remitconnectapp.util.actionStyle
import com.example.remitconnectapp.util.balanceStyle
import com.example.remitconnectapp.util.currencyStyle
import com.example.remitconnectapp.util.firstTitleStyle
import com.example.remitconnectapp.util.moneySentStyle
import com.example.remitconnectapp.util.moneyStyle
import com.example.remitconnectapp.util.recipientStyle
import com.example.remitconnectapp.util.sentToStyle
import com.example.remitconnectapp.util.transactionsStyle


@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    HomeScreenContent(remittance = viewModel.getUserBalance())

}

@Composable
fun HomeScreenContent(
    remittance: Float,
    modifier: Modifier = Modifier,
) {
    SetStatusBarColor(Color.White){
        Column(
            modifier = modifier
                .background(color = colorResource(id = R.color.screen_color))
                .fillMaxSize()
                .padding(start = 24.dp, end = 24.dp, top = 56.dp)
        ) {

            Header()

            Spacer(modifier = modifier.size(24.dp))

            Wallet(remittance)

            Spacer(modifier = modifier.size(24.dp))

            ActionItems()

            Spacer(modifier = Modifier.size(40.dp))
            Text(text = "Transactions", style = transactionsStyle)

            Spacer(modifier = Modifier.size(16.dp))


            LazyColumn()
            {
                item {

                    TransactionsComponent()
                }
            }

        }
    }
}

@Composable
private fun ActionItems() {
    Row {
        ActionItem(
            actionName = "Top up balance",
            actionIcon = R.drawable.empty_wallet_add
        )
        Spacer(Modifier.weight(1f))

        ActionItem(actionName = "Withdraw money", actionIcon = R.drawable.wallet_minus)

    }

    Row(modifier = Modifier.padding(top = 16.dp)) {

        ActionItem(actionName = "Get IBAN", actionIcon = R.drawable.card)
        Spacer(Modifier.weight(1f))
        ActionItem(
            actionName = "View analytics",
            actionIcon = R.drawable.percentage_square
        )
    }
}

@Composable
private fun Header() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Hey, John Doe", style = firstTitleStyle)
        Spacer(Modifier.weight(1f))
        BellIcon()
    }
}

@Composable
fun TransactionsComponent() {
    Column(
        modifier = Modifier
            .height(336.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier

                .padding(16.dp)
        ) {
            ArrowIcon()
            Column {
                Text(
                    text = "Sent to",
                    style = sentToStyle,
                    color = colorResource(id = R.color.sent_to_color),
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = "Ralph Edwards",
                    style = recipientStyle,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(Modifier.weight(1f))

            Text(text = "€ 150", style = moneySentStyle)
        }

        Spacer(modifier = Modifier.size(8.dp))

        HorizontalDivider()
    }
}

@Composable
fun ArrowIcon() {
    Box(
        modifier =
        Modifier
            .height(40.dp)
            .width(40.dp)
            .background(
                color =
                colorResource(
                    id = R.color.lighter_bleu
                ),
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.vector),
            modifier = Modifier.align(Alignment.Center),
            contentDescription = "",
        )
    }

}


@Composable
fun ActionItem(
    actionName: String,
    @DrawableRes actionIcon: Int
) {
    Row(
        modifier =
        Modifier
            .width(156.dp)
            .height(101.dp)
            .background(
                color =
                colorResource(
                    id = R.color.white,
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
    ) {
        Column {
            ActionIcon(actionIcon)
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                color = colorResource(id = R.color.action_color),
                text = actionName,
                style = actionStyle
            )
        }


    }
}

@Composable
fun Wallet(balance: Float?) {

    val gradientColors = listOf(
        colorResource(id = R.color.green1),
        colorResource(id = R.color.green2)
    )
    Row(
        modifier =
        Modifier
            .background(
                brush = GradientBackgroundBrush(
                    isVerticalGradient = false,
                    colors = gradientColors
                ),
                shape = RoundedCornerShape(16.dp),
            )
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column {
            Text(
                text = "Your balance",
                style = balanceStyle,
                modifier = Modifier.padding(bottom = 35.dp)
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = balance?.toString() ?: "",
                style = moneyStyle,
            )

            Spacer(modifier = Modifier.size(6.dp))

            Text(
                text = "US Dollars",
                style = currencyStyle,
            )
        }

        Spacer(Modifier.weight(1f))


        WalletIcon()
    }
}

@Composable
fun WalletIcon() {
    Box(
        modifier =
        Modifier
            .size(56.dp)
            .background(
                color =
                colorResource(
                    id = R.color.lighter_green,
                ),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.moneys),
            modifier = Modifier.align(Alignment.Center),
            contentDescription = "",
        )
    }
}

@Composable
fun ActionIcon(
    @DrawableRes actionIcon: Int
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
            painter = painterResource(id = actionIcon),
            modifier = Modifier.align(Alignment.Center),
            contentDescription = "",
        )
    }
}


@Composable
private fun BellIcon(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
        modifier
            .size(40.dp)
            .background(
                color = colorResource(id = R.color.greyscale_100),
                shape = RoundedCornerShape(12.dp),
            ),
    ) {
        Image(
            painter = painterResource(id = R.drawable.bell_f),
            modifier = modifier.align(Alignment.Center),
            contentDescription = "",
        )
    }
}


@Composable
private fun GradientBackgroundBrush(
    isVerticalGradient: Boolean,
    colors: List<Color>
): Brush {

    val endOffset = if (isVerticalGradient) Offset(
        0F,
        Float.POSITIVE_INFINITY
    ) else Offset(Float.POSITIVE_INFINITY, 0F)


    return Brush.linearGradient(
        colors = colors,
        start = Offset.Zero,
        end = endOffset
    )
}

@Preview
@Composable
internal fun HomeScreenContentPreview() {
    HomeScreenContent(
        remittance = 320f,
        modifier = Modifier.background(color = Color.White),
    )
}