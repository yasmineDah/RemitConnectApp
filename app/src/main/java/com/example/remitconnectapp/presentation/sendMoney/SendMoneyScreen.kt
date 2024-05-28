package com.example.remitconnectapp.presentation.sendMoney

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.remitconnectapp.R
import com.example.remitconnectapp.domain.model.RemittanceParams
import com.example.remitconnectapp.presentation.BackIcon
import com.example.remitconnectapp.presentation.recipients.CustomHintTextField
import com.example.remitconnectapp.presentation.recipients.ProceedButton
import com.example.remitconnectapp.util.contactsStyle
import com.example.remitconnectapp.util.currentBalanceStyle
import com.example.remitconnectapp.util.firstTitleStyle
import com.example.remitconnectapp.util.grayTextStyle
import com.example.remitconnectapp.util.moneySetStyle
import com.example.remitconnectapp.util.realBalanceStyle


@Composable
fun SendMoneyScreen(
    viewModel: SendMoneyViewModel,
    onConfirmClicked: (Float) -> Unit,
    onCloseClicked: () -> Unit,
) {
    SendMoneyScreenContent(
        state = viewModel.uiState,
        onUserTyping = viewModel::handleAmountConversion,
        onContinueClicked = viewModel::handleContinueClicked,
        remittanceParams = viewModel.getRemittanceParams(),
        onConfirmClicked = onConfirmClicked,
        onCloseClicked = onCloseClicked,
        onEmpty = viewModel::handleEmptyState
    )
}

@Composable
fun SendMoneyScreenContent(
    remittanceParams: RemittanceParams,
    onUserTyping: (String) -> Unit,
    state: SendMoneyScreenState,
    modifier: Modifier = Modifier,
    onContinueClicked: () -> Unit,
    onConfirmClicked: (Float) -> Unit,
    onCloseClicked: () -> Unit,
    onEmpty: () -> Unit,
) {

    var isConfirmationBSOpen by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {

        Header(
            onCloseClicked = onCloseClicked,
            modifier = modifier
        )

        Spacer(modifier = Modifier.size(24.dp))
        
        Text(text = "How much are you sending ?", style = contactsStyle)

        Spacer(modifier = Modifier.size(8.dp))


        MoneyCalculatorComponent(state = state, onUserTyping = onUserTyping, onEmpty = onEmpty)

        Spacer(modifier = Modifier.size(32.dp))

        Text(text = "Yearly free remittances", style = contactsStyle)

        Spacer(modifier = Modifier.size(6.dp))

        InfosTexts()

        Spacer(modifier = Modifier.size(16.dp))

        Row {

            Text(
                text = "Moneco fees",
                style = grayTextStyle,
                color = colorResource(id = R.color.grey2)
            )

            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "0.0 EUR",
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        TransferFees()

        Spacer(modifier = Modifier.size(16.dp))

        ConversionRate(state)

        Spacer(modifier = Modifier.size(16.dp))

        Total(state)

        Spacer(modifier = Modifier.size(16.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.size(16.dp))

        Remittance(state)

        Spacer(modifier = Modifier.size(50.dp))

        ProceedButton(
            modifier = Modifier.fillMaxWidth(),
            isEnabled = when (state.transactionState) {
                TransactionState.Success -> true
                else -> false
            }, labelRes = R.string.continue_button,
            onClick = {
                onContinueClicked()
                isConfirmationBSOpen = true
            }
        )

        if (isConfirmationBSOpen) {
            ConfirmationBottomSheet(
                remittanceParams = remittanceParams,
                onConfirmClicked = onConfirmClicked,
                onClose = {isConfirmationBSOpen = false},

            )
        }

    }

}

@Composable
private fun Remittance(state: SendMoneyScreenState) {
    Row {

        Text(
            text = "Recipient gets",
            style = grayTextStyle,
            color = colorResource(id = R.color.grey2)
        )

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = state.amountToSendConverted.toString() + " CAF",
            style = moneySetStyle
        )
    }
}

@Composable
private fun Total(state: SendMoneyScreenState) {
    Row {

        Text(
            text = "You’ll spend in total",
            style = grayTextStyle,
            color = colorResource(id = R.color.grey2)
        )

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = state.amountToSend.toString() + " EUR",
        )
    }
}

@Composable
private fun ConversionRate(state: SendMoneyScreenState) {
    Row {

        Text(
            text = "Conversion rate",
            style = grayTextStyle,
            color = colorResource(id = R.color.grey2)
        )

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = state.conversionRate.toString() + " CFA",
        )
    }
}

@Composable
private fun TransferFees() {
    Row {

        Text(
            text = "Transfer fees",
            style = grayTextStyle,
            color = colorResource(id = R.color.grey2)
        )

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "0.0 EUR",
        )
    }
}

@Composable
private fun InfosTexts() {
    Text(
        text = "Remittances are free with Moneco until you reach your limit, which resets every year.",
        style = grayTextStyle,
        color = colorResource(id = R.color.grey2)
    )

    Spacer(modifier = Modifier.size(6.dp))

    Text(
        text = "Check number of free remittance remaining",
        style = contactsStyle,
        color = colorResource(id = R.color.bleu_color)
    )

    Spacer(modifier = Modifier.size(32.dp))

    Text(
        text = "Fees breakdown",
        style = contactsStyle,
    )
}

@Composable
fun MoneyCalculatorComponent(
    onUserTyping: (String) -> Unit,
    state: SendMoneyScreenState,
    onEmpty: () -> Unit
) {
    Column(

        modifier = Modifier
            .border(
                width = 1.dp,
                color = when (state.transactionState) {
                    is TransactionState.Idle -> colorResource(id = R.color.greyscale_300)
                    is TransactionState.Success -> colorResource(id = R.color.action_color)
                    is TransactionState.Error -> Color.Red
                },
                shape = RoundedCornerShape(8.dp),
            )
            .height(93.dp)

            .fillMaxWidth()

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(59.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        )
        {

            var moneyToSend by rememberSaveable {
                mutableStateOf("")
            }

            CustomHintTextField(
                keyboardOptions =
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                ),
                textStyle = moneySetStyle,
                value = moneyToSend,
                onValueChanged = {
                    moneyToSend = it
                    if (it.isNotEmpty()) {
                        onUserTyping(it)
                    } else {
                        onEmpty()
                    }


                },
                hintText = "0",
                modifier =
                Modifier
                    .height(56.dp)
                    .background(colorResource(id = R.color.transparent_color)),
            )

            Spacer(modifier = Modifier.weight(1f))
            Text(text = "EUR", style = moneySetStyle, color = Color.Gray)


        }
        HorizontalDivider(
            color = when (state.transactionState) {
                is TransactionState.Idle -> colorResource(id = R.color.greyscale_300)
                is TransactionState.Success -> colorResource(id = R.color.action_color)
                is TransactionState.Error -> Color.Red
            }
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(34.dp)
                .background(
                    color = when (state.transactionState) {
                        is TransactionState.Idle -> colorResource(id = R.color.grey)
                        is TransactionState.Success -> colorResource(id = R.color.lighter_green2)
                        is TransactionState.Error -> colorResource(id = R.color.light_red)
                    },
                    shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp),
                )
                .padding(start = 8.dp)
        )

        {
            Text(
                text = "Your current balance is ", style = realBalanceStyle,
                color = when (state.transactionState) {
                    is TransactionState.Idle -> Color.Black
                    is TransactionState.Success -> colorResource(id = R.color.action_color)
                    is TransactionState.Error -> Color.Red
                }
            )

            Text(
                text = state.currentBalance.toString() + " EUR", style = currentBalanceStyle,
                color = when (state.transactionState) {
                    is TransactionState.Idle -> Color.Black
                    is TransactionState.Success -> colorResource(id = R.color.action_color)
                    is TransactionState.Error -> Color.Red
                }
            )

        }


    }
}


@Composable
private fun Header(onCloseClicked: () -> Unit, modifier: Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        BackIcon(onCloseClicked = onCloseClicked)
    }
    Spacer(modifier = modifier.size(8.dp))

    Text(
        text = "Send Money", style = firstTitleStyle, modifier = modifier
    )
}


@Preview
@Composable
internal fun SendMoneyScreenContentPreview() {
    val remitParams = RemittanceParams()
    val state = SendMoneyScreenState()
    SendMoneyScreenContent(
        state = state,
        modifier = Modifier.background(color = Color.White),
        onUserTyping = {},
        onContinueClicked = {},
        remittanceParams = remitParams,
        onConfirmClicked = {},
        onCloseClicked = {},
        onEmpty = {}
    )

}
