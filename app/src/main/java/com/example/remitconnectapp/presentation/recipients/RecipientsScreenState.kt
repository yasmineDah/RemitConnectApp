package com.example.remitconnectapp.presentation.recipients

import com.example.remitconnectapp.domain.model.Country
import com.example.remitconnectapp.domain.model.Recipient

data class RecipientsScreenState (
    val recipients : List<Recipient> = emptyList(),
    val researchedRecipients : List<Recipient> = emptyList(),
    val selectedCountry : Country? = null
)