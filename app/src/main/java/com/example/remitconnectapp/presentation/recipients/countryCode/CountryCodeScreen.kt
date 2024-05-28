package com.example.remitconnectapp.presentation.recipients.countryCode

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.remitconnectapp.R
import com.example.remitconnectapp.domain.model.Country
import com.example.remitconnectapp.presentation.recipients.CustomHintTextField
import com.example.remitconnectapp.util.countryStyle
import com.example.remitconnectapp.util.firstTitleStyle
import com.murgupluoglu.flagkit.FlagKit


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryCodeBottomSheet(
    onCloseClicked: () -> Unit,
) {
    val viewModel: CountryCodeViewModel = hiltViewModel()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onCloseClicked()
        },
    ) {
        CountryCodeScreenContent(
            state = viewModel.uiState,
            onCountrySearch = viewModel::handleCountrySearch,
            onCountrySelected = viewModel::handleCountrySelected,
            onCloseClicked = { onCloseClicked() }
        )
    }

}


@Composable
fun CountryCodeScreenContent(
    modifier: Modifier = Modifier,
    state: CountryCodeScreenState,
    onCountrySearch: (String) -> Unit,
    onCountrySelected: (Country) -> Unit = {},
    onCloseClicked: () -> Unit,
) {
    Column(
        modifier =
        modifier
            .wrapContentHeight()
            .padding(start = 12.dp, end = 12.dp),
    ) {
        Text(
            text = "Country Code",
            style = firstTitleStyle,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        Spacer(modifier = Modifier.size(24.dp))

        var isUserSearchingForCountries by remember { mutableStateOf(false) }
        var text by remember { mutableStateOf("") }

        CustomHintTextField(
            value = text,
            onValueChanged = {
                text = it
                onCountrySearch(it)
                isUserSearchingForCountries = it.isNotEmpty()
            },
            hintText = "Search...",
            modifier =
            Modifier
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.greyscale_300),
                    shape = RoundedCornerShape(8.dp),
                )
                .fillMaxWidth()
                .height(48.dp)
                .background(colorResource(id = R.color.transparent_color))
                .padding(start = 14.dp),
        )

        Spacer(modifier = Modifier.size(8.dp))

        CountriesList(
            state = state,
            modifier = modifier.height(250.dp),
            onCountrySelected = onCountrySelected,
            onCloseClicked = onCloseClicked,
            isUserSearching = isUserSearchingForCountries
        )
    }
}

@Composable
fun CountriesList(
    state: CountryCodeScreenState,
    modifier: Modifier,
    selectedCountryCode: String? = null,
    onCountrySelected: (Country) -> Unit,
    onCloseClicked: () -> Unit,
    isUserSearching: Boolean = false
) {
    val list = if (isUserSearching) state.researchedCountries else state.countries
    LazyColumn(modifier = modifier) {
        items(list) {
            CountryItem(
                modifier = Modifier.clickable {
                    onCountrySelected(it)
                    onCloseClicked()
                },
                it,
                it.code == selectedCountryCode,
            )
            Spacer(modifier = Modifier.size(2.dp))
        }
    }
}

@Composable
fun CountryItem(
    modifier: Modifier = Modifier,
    it: Country,
    isSelected: Boolean,
) {
    val countryFragCode = FlagKit.getResId(LocalContext.current, it.code)
    if (countryFragCode == 0) return
    Box(
        modifier =
        modifier
            .wrapContentSize()
            .background(
                color =
                colorResource(
                    id = if (isSelected) R.color.greyscale_300 else R.color.transparent_color,
                ),
                shape = RoundedCornerShape(12.dp),
            )
            .padding(16.dp),
    ) {
        if (isSelected) {
            CheckMark()
        }
        Row(
            modifier =
            Modifier
                .background(colorResource(id = R.color.transparent_color))
                .fillMaxWidth()
                .height(40.dp)
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Card(
                shape = RoundedCornerShape(4.dp),
                modifier =
                Modifier
                    .width(30.dp)
                    .height(20.dp),
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = countryFragCode),
                    contentDescription = "Country flag",
                )
            }

            Column {
                Text(
                    text = it.name,
                    style = countryStyle,
                    modifier = Modifier.padding(start = 6.dp),
                )

                Spacer(modifier = Modifier.size(6.dp))

                Text(
                    text = it.dialCode,
                    style = countryStyle,
                    modifier = Modifier.padding(start = 6.dp),
                )
            }
        }
    }
}

@Composable
fun BoxScope.CheckMark() {
    Image(
        modifier =
        Modifier
            .align(Alignment.CenterEnd)
            .size(24.dp),
        painter = painterResource(id = R.drawable.ic_check),
        contentDescription = "",
    )
}


@Preview
@Composable
internal fun CountryCodeScreenContentPreview() {
    val country1 =
        Country(name = "Algeria", dialCode = "+213", code = "DZ", flag = "")

    val country2 =
        Country(name = "Tunisia", dialCode = "+212", code = "TN", flag = "")
    val state =
        CountryCodeScreenState(
            countries = listOf(country1, country2),
            selectedCountryCode = country1.code,
        )
    CountryCodeScreenContent(
        modifier =
        Modifier
            .background(color = Color.White)
            .fillMaxSize(),
        state = state,
        onCountrySearch = {},
        onCountrySelected = {},
        onCloseClicked = {}
    )
}
