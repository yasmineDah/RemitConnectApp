package com.example.remitconnectapp.data.repository

import androidx.datastore.core.DataStore
import com.example.remitconnectapp.data.RemittanceParamsService
import com.example.remitconnectapp.data.mapper.RecipientMapper
import com.example.remitconnectapp.domain.model.DataState
import com.example.remitconnectapp.domain.model.Recipient
import com.example.remitconnectapp.domain.model.RecipientDomain
import com.example.remitconnectapp.domain.repository.RecipientRepository
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class RecipientsRepositoryImpl(
    private val service: RemittanceParamsService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dataStore: DataStore<List<Recipient>>,
    private val recipientMapper: RecipientMapper
) : RecipientRepository {

    override suspend fun getRecipients(): Flow<DataState<List<RecipientDomain>>> = flow {

        service.getRecipients().suspendOnSuccess {
            emit(DataState.Success(this.data.map {
                recipientMapper.mapToDomain(it)
            }))
        }.suspendOnError {
            emit(DataState.Error())
        }.suspendOnException {
            emit(DataState.Error(Exception(exception)))
        }
    }
        .onStart { emit(DataState.Loading) }
        .flowOn(ioDispatcher)

    override suspend fun saveRecipient(recipient: Recipient) {
        dataStore.updateData { listOfRecipients ->
            val newListOfRecipients = listOfRecipients.toMutableList()
            newListOfRecipients.add(recipient)
            newListOfRecipients
        }

    }

    override suspend fun observeRecipients(): Flow<List<Recipient>> =
        dataStore.data
}


