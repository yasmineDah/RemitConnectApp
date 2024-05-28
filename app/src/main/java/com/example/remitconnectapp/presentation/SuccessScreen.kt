package com.example.remitconnectapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.remitconnectapp.R
import com.example.remitconnectapp.presentation.recipients.ProceedButton
import com.example.remitconnectapp.util.firstTitleStyle
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SuccessScreen(
    onClick : () -> Unit,
    modifier: Modifier = Modifier
) {
    SetStatusBarColor(colorResource(id = R.color.green3)){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.green3))
                .padding(horizontal = 32.dp)
        ) {
            Image(
                modifier = Modifier.size(204.dp),
                painter = painterResource(id = R.drawable.happy), contentDescription = ""
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = "Your money is on the way. Get excited! ",
                style = firstTitleStyle,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.size(56.dp))

            ProceedButton(
                isEnabled = true, labelRes = R.string.got_it_btn, modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onClick()
                }
            )
        }
    }
}

@Composable
fun SetStatusBarColor(color: Color, content : @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(color)
    }
    content()
}


@Preview
@Composable
fun SuccessScreenPreview() {
    SuccessScreen(
        onClick = {},
        modifier = Modifier.fillMaxSize()
    )
}