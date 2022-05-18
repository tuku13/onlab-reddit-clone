package hu.tuku13.onlab_reddit_clone.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hilt_aggregated_deps._hu_tuku13_onlab_reddit_clone_di_AuthenticationModule
import hu.tuku13.onlab_reddit_clone.Constants
import hu.tuku13.onlab_reddit_clone.service.file.FileService
import hu.tuku13.onlab_reddit_clone.service.api.RedditCloneApi
import hu.tuku13.onlab_reddit_clone.repository.GroupRepository
import hu.tuku13.onlab_reddit_clone.repository.MessageRepository
import hu.tuku13.onlab_reddit_clone.repository.PostRepository
import hu.tuku13.onlab_reddit_clone.service.api.AuthenticationApi
import hu.tuku13.onlab_reddit_clone.service.authentication.AuthInterceptor
import hu.tuku13.onlab_reddit_clone.service.authentication.AuthenticationService
import okhttp3.OkHttpClient
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
    fun provideRedditCloneApi(
        moshi: Moshi,
        authenticationService: AuthenticationService
    ): RedditCloneApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(authenticationService))
            .build()

        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(Constants.BASE_URL)
            .client(client)
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