package com.sina.efood.di

import android.app.Application
import coil.ImageLoader
import com.sina.efood.data.Repository
import com.sina.efood.data.local.LocalDataSource
import com.sina.efood.di.qualifier.ApiKey
import com.sina.efood.di.qualifier.BaseUrl
import com.sina.efood.data.remote.FoodService
import com.sina.efood.data.remote.RemoteDataSource
import com.sina.efood.utils.API_KEY
import com.sina.efood.utils.API_KEY_VALUE
import com.sina.efood.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideImageLoader(application: Application) = ImageLoader(application)


    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(): String = API_KEY

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): String = BASE_URL

    @Provides
    @Singleton
    fun provideInterceptor(@ApiKey apiKey: String): Interceptor = Interceptor { chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter(API_KEY_VALUE, apiKey)
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun provideOkHttpLog(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): FoodService = provideApi<FoodService>(retrofit)


    @Provides
    fun provideRemoteDataSource(foodService: FoodService): RemoteDataSource =
        RemoteDataSource(foodService)


    @Provides
    fun provideRepository(
        remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): Repository =
        Repository(remoteDataSource, localDataSource, ioDispatcher)


}

inline fun <reified T> provideApi(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}