package hu.tuku13.onlab_reddit_clone.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.tuku13.onlab_reddit_clone.domain.service.AuthenticationService
import hu.tuku13.onlab_reddit_clone.network.service.RedditCloneApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthenticationModule {

    @Singleton
    @Provides
    fun provideAuthenticationService(api: RedditCloneApi) = AuthenticationService(api)
}