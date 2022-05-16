package hu.tuku13.onlab_reddit_clone.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.tuku13.onlab_reddit_clone.service.file.FileService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Singleton
    @Provides
    fun provideFileService(@ApplicationContext context: Context) = FileService(context)

}