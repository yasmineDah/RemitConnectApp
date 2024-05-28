package com.example.remitconnectapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.example.remitconnectapp.data.RemittanceParamsService
import com.example.remitconnectapp.data.mapper.CountryDomainMapper
import com.example.remitconnectapp.data.mapper.CountryMapper
import com.example.remitconnectapp.data.mapper.RecipientMapper
import com.example.remitconnectapp.data.mapper.WalletMapper
import com.example.remitconnectapp.data.repository.CountryRepositoryImpl
import com.example.remitconnectapp.data.repository.RecipientsRepositoryImpl
import com.example.remitconnectapp.data.repository.WalletRepositoryImpl
import com.example.remitconnectapp.data.repository.recipientsDataStore
import com.example.remitconnectapp.domain.model.Recipient
import com.example.remitconnectapp.domain.repository.CountryRepository
import com.example.remitconnectapp.domain.repository.RecipientRepository
import com.example.remitconnectapp.domain.repository.WalletRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRecipientsRepo(
        api: RemittanceParamsService,
        dataStore: DataStore<List<Recipient>>,
        recipientMapper: RecipientMapper
    ): RecipientRepository {
        return RecipientsRepositoryImpl(
            dataStore = dataStore,
            service = api,
            recipientMapper = recipientMapper
        )
    }

    @Provides
    @Singleton
    fun provideWalletsRepo(
        api: RemittanceParamsService,
        walletMapper: WalletMapper
    ): WalletRepository {
        return WalletRepositoryImpl(
            service = api,
            walletMapper = walletMapper
        )
    }


    @Provides
    @Singleton
    fun provideRecipientsDataStore(@ApplicationContext context: Context): DataStore<List<Recipient>> =
        context.recipientsDataStore


    @Provides
    @Singleton
    fun provideCountryRepository(
        api: RemittanceParamsService,
        @ApplicationContext context: Context,
        countryMapper: CountryMapper,
        countryDomainMapper: CountryDomainMapper,
    ): CountryRepository {
        return CountryRepositoryImpl(
            service = api,
            resources = context.resources,
            countryMapper = countryMapper,
            countryDomainMapper = countryDomainMapper
        )
    }

}

