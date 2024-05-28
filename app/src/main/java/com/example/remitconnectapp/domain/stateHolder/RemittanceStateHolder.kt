package com.example.remitconnectapp.domain.stateHolder

import com.example.remitconnectapp.domain.model.RemittanceParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RemittanceStateHolder {

    private val _remittanceParamsFlow =
        MutableStateFlow(RemittanceParams())
    val remittanceParamsFlow = _remittanceParamsFlow.asStateFlow()


    fun setRemittanceParams(remittanceParams: RemittanceParams) {
        _remittanceParamsFlow.value = remittanceParams
    }


    fun getRemittanceParams() = remittanceParamsFlow.value
}