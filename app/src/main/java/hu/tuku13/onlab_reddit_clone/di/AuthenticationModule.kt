package hu.tuku13.onlab_reddit_clone.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.tuku13.onlab_reddit_clone.service.api.AuthenticationApi
import hu.tuku13.onlab_reddit_clone.service.authentication.AuthenticationService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthenticationModule {
    @Singleton
    @Provides
    fun provideAuthenticationService(api: AuthenticationApi) = AuthenticationService(api)
}