package com.apollovisa.etoken.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import com.apollovisa.etoken.data.service.DefaultSMSMessageService
import com.apollovisa.etoken.data.service.DefaultUserService
import com.apollovisa.etoken.domain.service.SMSMessageService
import com.apollovisa.etoken.domain.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppServices {
    @Provides
    @Singleton
    fun providesSMSMessageService(application: Application): SMSMessageService {
        return DefaultSMSMessageService(context = application)
    }

    @Provides
    @Singleton
    fun providesUserService(application: Application): UserService {
        return DefaultUserService(
            sharedPreferences = application.getSharedPreferences(
                "USER",
                MODE_PRIVATE
            )
        )
    }
}
