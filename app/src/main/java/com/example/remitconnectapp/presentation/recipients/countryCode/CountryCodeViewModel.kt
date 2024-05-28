package com.example.remitconnectapp.presentation.recipients.countryCode

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remitconnectapp.domain.model.Country
import com.example.remitconnectapp.domain.model.RemittanceParams
import com.example.remitconnectapp.domain.usecases.GetCountriesUseCase
import com.example.remitconnectapp.domain.usecases.GetRemittanceParamsUseCase
import com.example.remitconnectapp.domain.usecases.UpdateRemittanceParamsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryCodeViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val updateRemittanceParamsUseCase: UpdateRemittanceParamsUseCase,
    private val getRemittanceParamsUseCase: GetRemittanceParamsUseCase
) : ViewModel() {
    var uiState by mutableStateOf(CountryCodeScreenState())
        private set

    init {
        uiState = uiState.copy(countries = getCountriesUseCase())
    }

    fun handleCountrySearch(countryName: String) {
        val researchedCountries =
            uiState.countries.filter {
                it.name.contains(countryName, ignoreCase = true)
            }
        uiState = uiState.copy(researchedCountries = researchedCountries)
    }


    fun handleCountrySelected(country: Country) {
        viewModelScope.launch {
            uiState = uiState.copy(selectedCountryCode = country.code)
            updateRemittanceParamsUseCase(
                getRemittanceParamsUseCase().copy(selectedCountry = country)
            )
        }
    }
}