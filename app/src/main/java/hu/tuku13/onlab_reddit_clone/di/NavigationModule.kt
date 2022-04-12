package hu.tuku13.onlab_reddit_clone.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.tuku13.onlab_reddit_clone.domain.service.NavigationService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {

    @Singleton
    @Provides
    fun provideNavigationService() = NavigationService()
}