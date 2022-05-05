package hu.tuku13.onlab_reddit_clone.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.tuku13.onlab_reddit_clone.Constants
import hu.tuku13.onlab_reddit_clone.domain.service.AuthenticationService
import hu.tuku13.onlab_reddit_clone.domain.service.FileService
import hu.tuku13.onlab_reddit_clone.network.service.RedditCloneApi
import hu.tuku13.onlab_reddit_clone.repository.GroupRepository
import hu.tuku13.onlab_reddit_clone.repository.MessageRepository
import hu.tuku13.onlab_reddit_clone.repository.PostRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideRedditCloneApi(moshi: Moshi): RedditCloneApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(RedditCloneApi::class.java)
    }

    @Singleton
    @Provides
    fun providePostRepository(
        api: RedditCloneApi,
        authenticationService: AuthenticationService
    ) = PostRepository(api, authenticationService)

    @Singleton
    @Provides
    fun provideGroupRepository(
        api: RedditCloneApi,
        authenticationService: AuthenticationService,
        fileService: FileService
    ) = GroupRepository(api, authenticationService, fileService)

    @Singleton
    @Provides
    fun provideMessageRepository(api: RedditCloneApi) = MessageRepository(api)
}