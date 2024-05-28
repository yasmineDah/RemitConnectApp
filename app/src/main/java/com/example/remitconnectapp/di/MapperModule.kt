package com.example.remitconnectapp.di

import com.example.remitconnectapp.data.mapper.CountryDomainMapper
import com.example.remitconnectapp.data.mapper.CountryMapper
import com.example.remitconnectapp.data.mapper.DomainMapper
import com.example.remitconnectapp.data.mapper.RecipientMapper
import com.example.remitconnectapp.data.mapper.WalletMapper
import com.example.remitconnectapp.data.model.CountryDTO
import com.example.remitconnectapp.data.model.CountryResponse
import com.example.remitconnectapp.data.model.RecipientDTO
import com.example.remitconnectapp.data.model.WalletDTO
import com.example.remitconnectapp.domain.model.Country
import com.example.remitconnectapp.domain.model.CountryDomain
import com.example.remitconnectapp.domain.model.RecipientDomain
import com.example.remitconnectapp.domain.model.Wallet
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class MapperModule {

    @Provides
    fun provideCountryMapper () : DomainMapper<CountryResponse, Country>{
        return CountryMapper()
    }


    @Provides
    fun provideCountryDomainMapper () : DomainMapper<CountryDTO, CountryDomain>{
        return CountryDomainMapper()
    }

    @Provides
    fun provideRecipientMapper () : DomainMapper<RecipientDTO, RecipientDomain>{
        return RecipientMapper()
    }


    @Provides
    fun provideWalletMapper () : DomainMapper<WalletDTO, Wallet>{
        return WalletMapper()
    }
}