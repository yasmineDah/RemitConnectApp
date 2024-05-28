package com.example.remitconnectapp.data.repository

import android.content.res.Resources
import com.example.remitconnectapp.R
import com.example.remitconnectapp.data.RemittanceParamsService
import com.example.remitconnectapp.data.mapper.CountryDomainMapper
import com.example.remitconnectapp.data.mapper.CountryMapper
import com.example.remitconnectapp.data.model.CountryResponse
import com.example.remitconnectapp.domain.model.Country
import com.example.remitconnectapp.domain.model.CountryDomain
import com.example.remitconnectapp.domain.model.DataState
import com.example.remitconnectapp.domain.repository.CountryRepository
import com.google.gson.Gson
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(
    private val countryMapper: CountryMapper,
    private val resources: Resources,
    private val countryDomainMapper: CountryDomainMapper,
    private val service: RemittanceParamsService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : CountryRepository {

    override suspend fun getRemoteCountries(): Flow<DataState<List<CountryDomain>>> = flow {

        service.getCountries().suspendOnSuccess {
            emit(DataState.Success(this.data.map {
                countryDomainMapper.mapToDomain(it)
            }))
        }.suspendOnError {
            emit(DataState.Error())
        }.suspendOnException {
            emit(DataState.Error(Exception(exception)))
        }
    }
        .onStart { emit(DataState.Loading) }
        .flowOn(ioDispatcher)


    override fun getCountries(): List<Country> {
        val jsonToString =
            resources.openRawResource(R.raw.countries).bufferedReader().use { it.readText() }

        return ArrayList(
            Gson().fromJson(jsonToString, Array<CountryResponse>::class.java).toList().map {
                countryMapper.mapToDomain(it)
            },
        )
    }
}