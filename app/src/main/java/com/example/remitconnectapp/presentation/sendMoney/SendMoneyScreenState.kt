package com.example.remitconnectapp.presentation.sendMoney

data class SendMoneyScreenState(
    val currentBalance: Float = 0f,
    val amountToSend: Int = 0,
    val amountToSendConverted: Float = 0f,
    val transactionState: TransactionState = TransactionState.Idle,
    val conversionRate : Float = 655.94f,

)


sealed class TransactionState() {
    data object Idle : TransactionState()

    data object Success : TransactionState()

    data object Error : TransactionState()
}
