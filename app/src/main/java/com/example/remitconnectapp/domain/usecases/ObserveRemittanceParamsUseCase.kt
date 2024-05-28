package com.example.remitconnectapp.domain.usecases

import com.example.remitconnectapp.domain.stateHolder.RemittanceStateHolder
import javax.inject.Inject

class ObserveRemittanceParamsUseCase @Inject constructor(
    private val remittanceStateHolder: RemittanceStateHolder
) {
    operator fun invoke() = remittanceStateHolder.remittanceParamsFlow
}