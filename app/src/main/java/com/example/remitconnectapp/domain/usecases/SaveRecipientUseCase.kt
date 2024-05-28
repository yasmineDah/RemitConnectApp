package com.example.remitconnectapp.domain.usecases

import com.example.remitconnectapp.domain.model.Recipient
import com.example.remitconnectapp.domain.repository.RecipientRepository

class SaveRecipientUseCase(
    private val recipientsRepository: RecipientRepository
) {

    suspend operator fun invoke(recipient: Recipient) =
        recipientsRepository.saveRecipient(recipient)
}