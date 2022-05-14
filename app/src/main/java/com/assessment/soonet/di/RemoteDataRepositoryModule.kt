package com.assessment.soonet.di

import com.assessment.soonet.data.api.ApiService
import com.assessment.soonet.data.repository.RemoteDataRepository
import com.assessment.soonet.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataRepositoryModule {

    @Singleton
    @Provides
    fun provideRemoteDataRepository(
        api: ApiService
    ): Repository.RemoteData {
        return RemoteDataRepository(api)
    }
}
