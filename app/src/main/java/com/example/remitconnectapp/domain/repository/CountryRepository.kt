package com.example.remitconnectapp.domain.repository

import com.example.remitconnectapp.data.model.CountryDTO
import com.example.remitconnectapp.domain.model.Country
import com.example.remitconnectapp.domain.model.CountryDomain
import com.example.remitconnectapp.domain.model.DataState
import kotlinx.coroutines.flow.Flow

interface CountryRepository {

    suspend fun getRemoteCountries () : Flow<DataState<List<CountryDomain>>>

    fun getCountries(): List<Country>
}