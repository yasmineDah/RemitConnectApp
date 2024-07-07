package com.example.remitconnectapp.data

import com.example.remitconnectapp.data.model.CountryDTO
import com.example.remitconnectapp.data.model.RecipientDTO
import com.example.remitconnectapp.data.model.WalletDTO
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface RemittanceParamsService {

    @GET("wallets")

    suspend fun getWallets(): ApiResponse<List<WalletDTO>>

    @GET("countries")
    suspend fun getCountries(): ApiResponse<List<CountryDTO>>

    @GET("recipients")
    suspend fun getRecipients(): ApiResponse<List<RecipientDTO>>
}


