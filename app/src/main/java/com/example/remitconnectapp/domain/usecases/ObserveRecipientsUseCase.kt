package com.example.remitconnectapp.domain.usecases

import com.example.remitconnectapp.domain.repository.RecipientRepository

class ObserveRecipientsUseCase(
    private val recipientsRepository: RecipientRepository
) {
    suspend operator fun invoke() = recipientsRepository.observeRecipients()
}