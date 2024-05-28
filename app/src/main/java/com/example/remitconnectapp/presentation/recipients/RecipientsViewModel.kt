package com.example.remitconnectapp.presentation.recipients

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remitconnectapp.domain.model.Recipient
import com.example.remitconnectapp.domain.usecases.GetRemittanceParamsUseCase
import com.example.remitconnectapp.domain.usecases.ObserveRecipientsUseCase
import com.example.remitconnectapp.domain.usecases.ObserveRemittanceParamsUseCase
import com.example.remitconnectapp.domain.usecases.SaveRecipientUseCase
import com.example.remitconnectapp.domain.usecases.UpdateRemittanceParamsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipientsViewModel @Inject constructor(
    private val observeRemittanceUseCase: ObserveRemittanceParamsUseCase,
    private val updateRemittanceParamsUseCase: UpdateRemittanceParamsUseCase,
    private val getRemittanceParamsUseCase: GetRemittanceParamsUseCase,
    private val saveRecipientUseCase: SaveRecipientUseCase,
    private val observeRecipientsUseCase: ObserveRecipientsUseCase,
    ) : ViewModel() {

    var uiState by mutableStateOf(RecipientsScreenState())
        private set


    init {
        observeRemittanceParams()
        observeRecipients()
    }

    private fun observeRecipients() {
        viewModelScope.launch {
            observeRecipientsUseCase().collect {
                uiState = uiState.copy(recipients = it)
            }
        }

    }

    private fun observeRemittanceParams() {
        viewModelScope.launch {
            observeRemittanceUseCase().collect { remittanceParams ->
                uiState = uiState.copy(selectedCountry = remittanceParams.selectedCountry)

            }
        }
    }

    fun handleRecipientSelected(recipient: Recipient) {
        updateRemittanceParamsUseCase(
            getRemittanceParamsUseCase().copy(recipient = recipient)
        )
    }

    fun handleContinueClicked(recipient: Recipient) {
        var phoneNumber = recipient.phoneNumber
        if (phoneNumber.first() == '0') {
            phoneNumber = phoneNumber.substring(1, phoneNumber.length)
        }

        val recipientUpdated =
            recipient.copy(phoneNumber = "${uiState.selectedCountry?.dialCode} $phoneNumber")

        viewModelScope.launch {
            updateRemittanceParamsUseCase(
                getRemittanceParamsUseCase().copy(recipient = recipientUpdated)
            )

            saveRecipientUseCase(recipientUpdated)
        }
    }

    fun handleRecipientSearch(nameOrPhoneNum: String) {
        val filteredList = uiState.recipients.filter { recipient ->
            val fullName = "${recipient.firstName} ${recipient.lastName}"
            fullName.contains(nameOrPhoneNum, ignoreCase = true) || recipient.phoneNumber.contains(
                nameOrPhoneNum
            )
        }

        uiState = uiState.copy(researchedRecipients = filteredList)
    }
}