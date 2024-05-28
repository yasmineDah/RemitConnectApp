package com.example.remitconnectapp.domain.repository

import com.example.remitconnectapp.domain.model.DataState
import com.example.remitconnectapp.domain.model.RecipientDomain
import com.example.remitconnectapp.domain.model.Recipient
import kotlinx.coroutines.flow.Flow

interface RecipientRepository {

    suspend fun getRecipients () : Flow<DataState<List<RecipientDomain>>>


    suspend fun saveRecipient(recipient: Recipient)


    suspend fun observeRecipients() : Flow<List<Recipient>>
}