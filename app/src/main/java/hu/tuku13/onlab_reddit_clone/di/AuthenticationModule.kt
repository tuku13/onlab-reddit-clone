package hu.tuku13.onlab_reddit_clone.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.tuku13.onlab_reddit_clone.Constants
import hu.tuku13.onlab_reddit_clone.service.api.AuthenticationApi
import hu.tuku13.onlab_reddit_clone.service.authentication.AuthenticationService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthenticationModule {
    @Singleton
    @Provides
    fun provideAuthenticationService(api: AuthenticationApi) = AuthenticationService(api)

    @Singleton
    @Provides
    fun provideAuthenticationApi(
        moshi: Moshi
    ): AuthenticationApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(AuthenticationApi::class.java)
    }
}