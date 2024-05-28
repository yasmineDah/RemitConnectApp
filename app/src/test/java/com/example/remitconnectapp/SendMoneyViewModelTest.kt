package com.example.remitconnectapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.remitconnectapp.domain.model.Country
import com.example.remitconnectapp.domain.model.Recipient
import com.example.remitconnectapp.domain.model.RemittanceParams
import com.example.remitconnectapp.domain.model.Wallet
import com.example.remitconnectapp.domain.repository.WalletRepository
import com.example.remitconnectapp.domain.usecases.GetRemittanceParamsUseCase
import com.example.remitconnectapp.domain.usecases.UpdateRemittanceParamsUseCase
import com.example.remitconnectapp.presentation.sendMoney.SendMoneyViewModel
import com.example.remitconnectapp.presentation.sendMoney.TransactionState
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class SendMoneyViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()



    private lateinit var viewModel: SendMoneyViewModel

    private val walletRepo = mock<WalletRepository>()
    private val updateRemittanceParamsUseCase = mock<UpdateRemittanceParamsUseCase>()
    private val getRemittanceParamsUseCase = mock<GetRemittanceParamsUseCase>()

    private val balance = 230f

    private val remitParams = RemittanceParams(
        selectedCountry = Country(name ="Algeria", dialCode = "+213", code = "DZ", flag = ""),
        recipient = Recipient(firstName = "Yasmina", lastName = "Dahmane", phoneNumber = ""),
        wallet = Wallet(id = "1", name = "Orange Monkey"),
        remittance = 2345f,
        newBalance = 100f
    )

    @Before
    fun setup() {
        whenever(walletRepo.getBalance()).thenReturn(balance)
        whenever(getRemittanceParamsUseCase.invoke()).thenReturn(remitParams)

        viewModel = SendMoneyViewModel(
            walletRepository = walletRepo,
            getRemittanceParamsUseCase,
            updateRemittanceParamsUseCase
        )
    }

    @Test
    fun `when viewModel is created then update the uiState according`() {
        assertEquals(viewModel.uiState.currentBalance, balance)
    }

    @Test
    fun `when getRemittanceParams is called then return correct data`() {
        //When
        val params = viewModel.getRemittanceParams()

        //Then
        verify(
            getRemittanceParamsUseCase,
            Mockito.times(1),
        ).invoke()

        assertEquals(params, remitParams.copy(remittance = 0f, newBalance = balance))

    }



    @Test
    fun `when handleAmountConversion is called then uiState is updated according`() {
        //When
        val params = viewModel.handleAmountConversion("100")

        //Then
        assertEquals(viewModel.uiState.amountToSend, 100)
        assertEquals(viewModel.uiState.currentBalance, 130f)
        assertEquals(viewModel.uiState.transactionState, TransactionState.Success)
        assertEquals(viewModel.uiState.amountToSendConverted, 65594f)

    }

    @Test
    fun `when handleContinueClicked is called then update remittance state accordingly`() {
        //When
        viewModel.handleContinueClicked()

        //Then
        verify(
            getRemittanceParamsUseCase,
            Mockito.times(1),
        ).invoke()

        verify(
            updateRemittanceParamsUseCase,
            Mockito.times(1),
        ).invoke(remitParams.copy(remittance = 0f, newBalance = balance))

    }

    @Test
    fun `when updateWalletBalance is called then update user balance`() {
        //When
        viewModel.updateWalletBalance(100f)

        //Then
        verify(
            walletRepo,
            Mockito.times(1),
        ).updateBalance(100f)

    }

    @Test
    fun `when handleEmptyState is called then update uiState accordingly`() {
        //When
        viewModel.handleEmptyState()

        //Then
        assertEquals(viewModel.uiState.currentBalance, balance)
        assertEquals(viewModel.uiState.transactionState, TransactionState.Idle)
        assertEquals(viewModel.uiState.amountToSend, 0)
        assertEquals(viewModel.uiState.amountToSendConverted, 0f)

    }
}
