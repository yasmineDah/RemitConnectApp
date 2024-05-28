package com.example.remitconnectapp.di


import com.example.remitconnectapp.domain.repository.CountryRepository
import com.example.remitconnectapp.domain.repository.RecipientRepository
import com.example.remitconnectapp.domain.repository.WalletRepository
import com.example.remitconnectapp.domain.stateHolder.RemittanceStateHolder
import com.example.remitconnectapp.domain.usecases.GetCountriesUseCase
import com.example.remitconnectapp.domain.usecases.GetRemittanceParamsUseCase
import com.example.remitconnectapp.domain.usecases.GetWalletsUseCase
import com.example.remitconnectapp.domain.usecases.ObserveRecipientsUseCase
import com.example.remitconnectapp.domain.usecases.ObserveRemittanceParamsUseCase
import com.example.remitconnectapp.domain.usecases.SaveRecipientUseCase
import com.example.remitconnectapp.domain.usecases.UpdateRemittanceParamsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetCountriesUseCase(countryRepository: CountryRepository): GetCountriesUseCase {
        return GetCountriesUseCase(countryRepository)
    }

    @Provides
    @Singleton
    fun provideRemittanceStateHolder(): RemittanceStateHolder {
        return RemittanceStateHolder()
    }


    @Provides
    @Singleton
    fun provideGetRemittanceParamsUseCase(remittanceStateHolder: RemittanceStateHolder): GetRemittanceParamsUseCase {
        return GetRemittanceParamsUseCase(remittanceStateHolder)
    }

    @Provides
    @Singleton
    fun provideObserveRemittanceUseCase(remittanceStateHolder: RemittanceStateHolder): ObserveRemittanceParamsUseCase {
        return ObserveRemittanceParamsUseCase(remittanceStateHolder)
    }


    @Provides
    @Singleton
    fun provideUpdateRemittanceParamsUseCase(remittanceStateHolder: RemittanceStateHolder): UpdateRemittanceParamsUseCase {
        return UpdateRemittanceParamsUseCase(remittanceStateHolder)
    }


    @Provides
    @Singleton
    fun provideGetWalletsUseCase(walletsRepository: WalletRepository): GetWalletsUseCase {
        return GetWalletsUseCase(walletsRepository)
    }


    @Provides
    @Singleton
    fun provideObserveRecipientsUseCase(recipientsRepository: RecipientRepository): ObserveRecipientsUseCase {
        return ObserveRecipientsUseCase(recipientsRepository)
    }

    @Provides
    @Singleton
    fun provideSaveRecipientUseCase(recipientsRepository: RecipientRepository): SaveRecipientUseCase {
        return SaveRecipientUseCase(recipientsRepository)
    }
}