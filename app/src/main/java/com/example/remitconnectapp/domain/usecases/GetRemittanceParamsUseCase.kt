package com.example.remitconnectapp.domain.usecases

import com.example.remitconnectapp.domain.stateHolder.RemittanceStateHolder

class GetRemittanceParamsUseCase(
    private val remittanceStateHolder: RemittanceStateHolder,
) {
    operator fun invoke() = remittanceStateHolder.getRemittanceParams()
}