package com.example.remitconnectapp.domain.usecases

import com.example.remitconnectapp.domain.model.RemittanceParams
import com.example.remitconnectapp.domain.stateHolder.RemittanceStateHolder

class UpdateRemittanceParamsUseCase(
    private val remittanceStateHolder: RemittanceStateHolder,
) {
    operator fun invoke(remittanceParams: RemittanceParams) =
        remittanceStateHolder.setRemittanceParams(remittanceParams)
}