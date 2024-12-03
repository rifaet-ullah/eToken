package com.apollovisa.etoken.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import com.apollovisa.etoken.data.repository.SharedPreferenceSimCardRepository
import com.apollovisa.etoken.domain.repository.SimCardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppRepositories {
    @Provides
    @Singleton
    fun providesSimCardRepository(application: Application): SimCardRepository {
        return SharedPreferenceSimCardRepository(
            sharedPreferences = application.getSharedPreferences(
                "SIM_CARD",
                MODE_PRIVATE
            )
        )
    }
}
