package com.example.remitconnectapp.presentation.sendMoney

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.remitconnectapp.R
import com.example.remitconnectapp.domain.model.Recipient
import com.example.remitconnectapp.domain.model.RemittanceParams
import com.example.remitconnectapp.presentation.recipients.ProceedButton
import com.example.remitconnectapp.util.firstTitleStyle


val tileStyle = TextStyle(
    fontSize = 14.sp,
    lineHeight = 21.sp,
    fontWeight = FontWeight.Normal,
)

val remittanceStyle = TextStyle(
    fontSize = 18.sp,
    lineHeight = 27.sp,
    fontWeight = FontWeight.SemiBold,
)

val infoStyle = TextStyle(
    fontSize = 16.sp,
    lineHeight = 24.sp,
    fontWeight = FontWeight.Medium,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationBottomSheet(
    remittanceParams: RemittanceParams,
    modifier: Modifier = Modifier,
    onConfirmClicked : (Float) -> Unit,
    onClose : () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                onClose()
            },
        ) {
            ConfirmationBottomSheetContent(
                onConfirmClicked = {
                    onConfirmClicked(it)
                    onClose()
                },
                remittanceParams = remittanceParams,
                modifier = modifier,
            )
        }

}

@Composable
fun ConfirmationBottomSheetContent(
    remittanceParams: RemittanceParams,
    modifier: Modifier = Modifier,
    onConfirmClicked : (Float) -> Unit
){
    Column(modifier = modifier.padding(24.dp)) {
        Text(text = "Confirm transfer", style = firstTitleStyle)

        Spacer(modifier = Modifier.size(32.dp))

        Text(
            text = "You're sending",
            style = tileStyle,
            color = colorResource(id = R.color.grey2)
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(text = remittanceParams.remittance.toString() + " CAF", style = remittanceStyle)

        Spacer(modifier = Modifier.size(32.dp))

        Text(text = "To", style = tileStyle, color = colorResource(id = R.color.grey2))

        Spacer(modifier = Modifier.size(8.dp))

        val firstName = remittanceParams.recipient?.firstName ?: ""
        val lastName = remittanceParams.recipient?.lastName ?: ""

        Text(text = "$firstName $lastName", style = infoStyle)

        Spacer(modifier = Modifier.size(32.dp))

        Text(text = "Via", style = tileStyle, color = colorResource(id = R.color.grey2))

        Spacer(modifier = Modifier.size(8.dp))

        val walletName = remittanceParams.wallet?.name ?: ""
        val phoneNumber = remittanceParams.recipient?.phoneNumber ?: ""

        Text(text = "$walletName : $phoneNumber", style = infoStyle)

        Spacer(modifier = Modifier.size(40.dp))


        ProceedButton(
            isEnabled = true,
            labelRes = R.string.confirm_button,
            modifier = Modifier.fillMaxWidth(),
            onClick = { onConfirmClicked(remittanceParams.newBalance ?: 0f) }
        )


    }
}


@Preview
@Composable
fun ConfirmationBottomSheetPreview() {
    val remitParams = RemittanceParams(
        recipient = Recipient(
            firstName = "Yasmina",
            lastName = "Dahmane",
            phoneNumber = "0541931091"
        )
    )
    ConfirmationBottomSheet(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        remittanceParams = remitParams,
        onConfirmClicked = {},
        onClose = {}
    )
}