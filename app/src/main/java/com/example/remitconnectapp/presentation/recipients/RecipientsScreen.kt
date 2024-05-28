package com.example.remitconnectapp.presentation.recipients

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.remitconnectapp.R
import com.example.remitconnectapp.domain.model.Recipient
import com.example.remitconnectapp.presentation.BackIcon
import com.example.remitconnectapp.presentation.recipients.countryCode.CountryCodeBottomSheet
import com.example.remitconnectapp.util.contactsStyle
import com.example.remitconnectapp.util.countryStyle
import com.example.remitconnectapp.util.firstTitleStyle
import com.example.remitconnectapp.util.phoneNumberStyle
import com.example.remitconnectapp.util.recipientStyle
import com.example.remitconnectapp.util.recipientsStyle
import com.murgupluoglu.flagkit.FlagKit


const val DEFAULT_COUNTRY_CODE = "DZ"


@Composable
fun RecipientsScreen(
    viewModel: RecipientsViewModel,
    onRecipientSelected: (Recipient) -> Unit,
    onCloseClicked: () -> Unit,
    onContinueClicked: (Recipient) -> Unit,
) {
    RecipientsScreenContent(
        state = viewModel.uiState,
        onRecipientSelected = onRecipientSelected,
        onCloseClicked = onCloseClicked,
        onSearch = viewModel::handleRecipientSearch,
        onContinueClicked = onContinueClicked,
    )
}

@Composable
fun RecipientsScreenContent(
    onCloseClicked: () -> Unit,
    onRecipientSelected: (Recipient) -> Unit,
    state: RecipientsScreenState,
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    onContinueClicked: (Recipient) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        var isPreviousRecipientsSelected by remember { mutableStateOf(true) }

        var isUserSearchingForContacts by remember { mutableStateOf(false) }

        var researched by remember { mutableStateOf("") }

        Header(
            onCloseClicked = onCloseClicked,
            modifier = modifier
        )




        RecipientsSwitcher(
            isPreviousRecipientsSelected = isPreviousRecipientsSelected,
            onClick = { isPreviousRecipientsSelected = !isPreviousRecipientsSelected }
        )


        ResearchBar(
            modifier = modifier,
            searching = { researchedWord ->
                isUserSearchingForContacts = researchedWord.isNotEmpty()
                researched = researchedWord
                onSearch(researchedWord)
            }
        )

        when {
            state.researchedRecipients.isEmpty() && isUserSearchingForContacts -> EmptySearchScreen()
            isUserSearchingForContacts -> RecipientsList(
                onRecipientSelected = onRecipientSelected,
                state = state,
                isSearching = true
            )

            isPreviousRecipientsSelected -> RecipientsList(
                onRecipientSelected = onRecipientSelected,
                state = state,
                isSearching = false
            )

            else -> NewRecipientScreen(
                state = state,
                onContinueClicked = onContinueClicked,
            )
        }

    }

}


@Composable
private fun ResearchBar(
    searching: (String) -> Unit,
    modifier: Modifier,
) {
    Row(modifier = modifier.padding(start = 26.dp, end = 26.dp, bottom = 32.dp)) {
        Row(
            modifier =
            modifier
                .background(
                    colorResource(id = R.color.grey),
                    shape = RoundedCornerShape(12.dp),
                )
                .fillMaxWidth()
                .height(40.dp)
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            var text by remember { mutableStateOf("") }

            SearchIcon(modifier)

            CustomHintTextField(
                value = text,
                onValueChanged = {
                    text = it
                    searching(it)
                },
                hintText = "Search...",
                modifier =
                Modifier
                    .fillMaxWidth()
                    .background(Color.Unspecified)
                    .padding(6.dp),
            )
        }
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
            .padding(end = 24.dp, top = 8.dp, bottom = 8.dp, start = 24.dp)
    ) {
        BackIcon(onCloseClicked = onCloseClicked)
    }
    Spacer(modifier = modifier.size(8.dp))

    Text(
        text = "Who are you sending to?", style = firstTitleStyle, modifier = modifier
            .padding(start = 24.dp)
    )
}

@Composable
fun RecipientsList(
    state: RecipientsScreenState,
    onRecipientSelected: (Recipient) -> Unit,
    isSearching: Boolean = false,
) {
    Text(
        text = "Contacts on your phone",
        style = contactsStyle,
        modifier = Modifier.padding(start = 26.dp, bottom = 16.dp)
    )
    HorizontalDivider()
    LazyColumn {
        val list = if (isSearching) state.researchedRecipients else state.recipients
        items(list) { recipient ->
            RecipientItem(
                onRecipientSelected = onRecipientSelected,
                recipient = recipient
            )
            HorizontalDivider()
        }
    }

}

@Composable
fun RecipientItem(
    onRecipientSelected: (Recipient) -> Unit,
    recipient: Recipient
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(72.dp)
            .padding(start = 24.dp, end = 24.dp)
            .clickable { onRecipientSelected(recipient) }
    ) {
        RecipientImage(R.drawable.img1)

        Column {
            Text(
                text = recipient.firstName + " " + recipient.lastName,
                style = recipientStyle,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(Modifier.size(4.dp))
            Text(
                color = colorResource(id = R.color.grey2),
                text = recipient.phoneNumber,
                style = phoneNumberStyle,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

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
fun RecipientImage(
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
fun ProceedButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean,
    @StringRes labelRes: Int,
    radius: Dp = 8.dp,
    onClick: () -> Unit = {},
    isVisible: Boolean = true,
) {
    if (isVisible) {
        Button(
            onClick = onClick,
            modifier =
            modifier
                .height(48.dp),
            colors =
            ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.action_color),
                contentColor = Color.White,
                disabledContainerColor = colorResource(id = R.color.greyscale_100),
                disabledContentColor = colorResource(id = R.color.black),
            ),
            shape = RoundedCornerShape(radius),
            enabled = isEnabled,
        ) {
            Text(
                text = stringResource(id = labelRes),
                style = recipientsStyle,
            )
        }
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .height(48.dp)
                .padding(end = 16.dp)
        ) {
            Spacer(Modifier.weight(1f))
            Text(
                text = "New recipient",
                style = recipientsStyle,
                color = colorResource(id = R.color.recipient_color)
            )
        }
    }
}

@Composable
private fun SearchIcon(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_search_icon),
        contentDescription = "Search icon",
    )
}

@Composable
fun CustomHintTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    hintText: String = "",
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    BasicTextField(
        keyboardOptions = keyboardOptions,
        value = value,
        onValueChange = onValueChanged,
        textStyle = textStyle,
        decorationBox = { innerTextField ->
            Box(contentAlignment = Alignment.CenterStart) {
                if (value.isEmpty()) {
                    Text(text = hintText, color = colorResource(id = R.color.sent_to_color))
                }
                innerTextField()
            }
        },
        modifier = modifier,
    )
}


@Composable
fun NewRecipientScreen(
    state: RecipientsScreenState,
    onContinueClicked: (Recipient) -> Unit = {},
    modifier: Modifier = Modifier
) {

    var isCountryBottomSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.padding(bottom = 2.dp, start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        var phoneNumber by rememberSaveable {
            mutableStateOf("")
        }

        var firstName by rememberSaveable {
            mutableStateOf("")
        }

        var lastName by rememberSaveable {
            mutableStateOf("")
        }

        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .weight(1f, false)
        ) {
            LazyColumn(
                modifier = modifier.height(450.dp)
            ) {
                item {
                    Text(
                        text = "Country",
                        style = countryStyle,
                        modifier = modifier.padding(vertical = 8.dp)
                    )

                    PhoneNumberComponent(state = state,
                        onCountryClicked = {
                            isCountryBottomSheetOpen = true
                        }
                    )

                    Spacer(Modifier.size(24.dp))

                    ChooseContactComponent()

                    Spacer(modifier = modifier.size(24.dp))

                    AddManuallyDivider(modifier)

                    Spacer(modifier = modifier.size(24.dp))

                    PhoneNumber(modifier, onEditing = {
                        phoneNumber = it
                    }, phoneNumber = phoneNumber)

                    Spacer(modifier = modifier.size(24.dp))

                    FirstNameComponent(modifier, firstName, onEditing = { firstName = it })

                    Spacer(modifier = modifier.size(24.dp))


                    LastNameComponent(modifier, lastName, onEditing = {lastName = it})

                    Spacer(modifier = modifier.size(30.dp))

                }
            }

            if (isCountryBottomSheetOpen) {
                CountryCodeBottomSheet {
                    isCountryBottomSheetOpen = false
                }
            }
        }


        ProceedButton(
            isEnabled = firstName.isNotEmpty() && phoneNumber.isNotEmpty(),
            labelRes = R.string.continue_button,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onContinueClicked(
                    Recipient(
                        firstName = firstName,
                        lastName = lastName,
                        phoneNumber = phoneNumber
                    )
                )
            }
        )
    }


}

@Composable
private fun LastNameComponent(modifier: Modifier, lastName: String, onEditing: (String) -> Unit) {
    Text(
        text = "Last Name",
        style = countryStyle,
    )

    Spacer(modifier = modifier.size(12.dp))

    CustomHintTextField(
        textStyle = countryStyle,
        value = lastName,
        onValueChanged = {
            onEditing(it)
        },
        hintText = "Edwards",
        modifier =
        modifier
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.greyscale_300),
                shape = RoundedCornerShape(8.dp),
            )
            .fillMaxWidth()
            .height(56.dp)
            .background(colorResource(id = R.color.transparent_color))
            .padding(start = 14.dp),
    )
}

@Composable
private fun FirstNameComponent(
    modifier: Modifier,
    firstName: String,
    onEditing: (String) -> Unit,
) {
    Text(
        text = "First Name",
        style = countryStyle,
    )

    Spacer(modifier = modifier.size(12.dp))

    CustomHintTextField(
        textStyle = countryStyle,
        value = firstName,
        onValueChanged = {
            onEditing(it)
        },
        hintText = "Ralph",
        modifier =
        modifier
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.greyscale_300),
                shape = RoundedCornerShape(8.dp),
            )
            .fillMaxWidth()
            .height(56.dp)
            .background(colorResource(id = R.color.transparent_color))
            .padding(start = 14.dp),
    )
}

@Composable
private fun PhoneNumber(modifier: Modifier, onEditing: (String) -> Unit, phoneNumber: String) {
    Text(
        text = "Phone Number",
        style = countryStyle,
    )

    Spacer(modifier = modifier.size(12.dp))


    CustomHintTextField(
        keyboardOptions =
        KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
        ),
        textStyle = countryStyle,
        value = phoneNumber,
        onValueChanged = {
            onEditing(it)
        },
        hintText = "98 767 289",
        modifier =
        modifier
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.greyscale_300),
                shape = RoundedCornerShape(8.dp),
            )
            .fillMaxWidth()
            .height(56.dp)
            .background(colorResource(id = R.color.transparent_color))
            .padding(start = 14.dp),
    )
}

@Composable
private fun AddManuallyDivider(modifier: Modifier) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        HorizontalDivider(modifier = modifier.weight(1f))

        Text(
            color = colorResource(id = R.color.grey2),
            text = "OR ADD MANUALLY",
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier
                .weight(1.5f)
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
        HorizontalDivider(modifier = modifier.weight(1f))
    }
}

@Composable
private fun ChooseContactComponent() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier =
        Modifier
            .height(48.dp)
            .background(
                color = colorResource(id = R.color.lighter_green2),
                shape = RoundedCornerShape(8.dp),
            )
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Image(painter = painterResource(id = R.drawable.book), contentDescription = "")
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = "Choose a contact",
            style = recipientsStyle,
            color = colorResource(id = R.color.recipient_color)

        )
    }
}


@Composable
fun PhoneNumberComponent(
    onCountryClicked: () -> Unit,
    state: RecipientsScreenState,
) {
    Row(
        modifier =
        Modifier
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.greyscale_300),
                shape = RoundedCornerShape(8.dp),
            )
            .background(colorResource(id = R.color.transparent_color))
            .fillMaxWidth()
            .height(56.dp)
            .padding(start = 16.dp)
            .clickable { onCountryClicked() },
        verticalAlignment = Alignment.CenterVertically,
    ) {

        val countryFragCode = if (state.selectedCountry != null) {
            FlagKit.getResId(LocalContext.current, state.selectedCountry.code)
        } else FlagKit.getResId(LocalContext.current, DEFAULT_COUNTRY_CODE)
        Row {
            Card(
                modifier =
                Modifier
                    .size(24.dp)
            ) {
                Image(
                    modifier =
                    Modifier
                        .clip(CircleShape)
                        .size(24.dp),
                    painter = painterResource(id = countryFragCode),
                    contentDescription = "Country flag",
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = state.selectedCountry?.name ?: "Algeria",
                style = countryStyle,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(Modifier.weight(1f))

        Row(modifier = Modifier.padding(end = 16.dp)) {
            Text(
                color = colorResource(id = R.color.grey2),
                style = MaterialTheme.typography.bodySmall,
                text = state.selectedCountry?.dialCode ?: "+213",
                modifier = Modifier.padding(start = 6.dp),
            )
            Image(
                modifier =
                Modifier
                    .width(20.dp)
                    .height(12.dp)
                    .padding(start = 8.dp),
                painter = painterResource(id = R.drawable.arrow_down),
                contentDescription = "arrow down image",
            )
        }

    }
}


@Composable
fun EmptySearchScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = modifier.size(264.dp),
            painter = painterResource(id = R.drawable.empty), contentDescription = ""
        )

        Spacer(modifier = modifier.size(8.dp))

        Text(
            text = "No matching results found !",
            style = recipientStyle,
            color = colorResource(id = R.color.grey2)
        )
    }
}


@Composable
fun RecipientsSwitcher(
    isPreviousRecipientsSelected: Boolean = false,
    size: Dp = 48.dp,
    borderWidth: Dp = 1.dp,
    parentShape: Shape = RoundedCornerShape(8.dp),
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(24.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.lighter_green2),
                    shape = RoundedCornerShape(8.dp),
                )
                .height(size)
                .clip(shape = parentShape)
        ) {

            Row(
                modifier = Modifier
                    .border(
                        border = BorderStroke(
                            width = borderWidth,
                            color = colorResource(id = R.color.lighter_green2)
                        ),
                        shape = parentShape
                    )
            ) {
                Box(
                    modifier = Modifier
                        .size(size)
                        .weight(1f)
                        .padding(start = 2.dp)
                        .clickable { onClick() },
                    contentAlignment = Alignment.Center
                ) {
                    if (isPreviousRecipientsSelected)
                        ProceedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp),
                            isEnabled = true,
                            labelRes = R.string.previous_recipients,
                        )
                    else
                        Text(
                            text = stringResource(id = R.string.previous_recipients),
                            style = recipientsStyle,
                            color = colorResource(id = R.color.recipient_color)
                        )

                }
                
                Box(

                    modifier = Modifier
                        .size(size)
                        .weight(1f)
                        .padding(start = 2.dp)
                        .clickable { onClick() },
                    contentAlignment = Alignment.Center
                ) {
                    if (isPreviousRecipientsSelected) {
                        Text(
                            text = stringResource(id = R.string.new_recipient),
                            style = recipientsStyle,
                            color = colorResource(id = R.color.recipient_color)

                        )
                    } else ProceedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        isEnabled = true,
                        labelRes = R.string.new_recipient,
                    )
                }

            }
        }

    }

}

@Preview
@Composable
internal fun PreviousRecipientsScreenContentPreview() {
    val state = RecipientsScreenState()
    NewRecipientScreen(
        modifier = Modifier.background(color = Color.White),
        state = state
    )

//    PreviousRecipientsScreenContent(
//        modifier = Modifier.background(color = Color.White),
//    )

//    EmptySearchScreen(
//        modifier = Modifier.background(color = Color.White),
//    )
}
