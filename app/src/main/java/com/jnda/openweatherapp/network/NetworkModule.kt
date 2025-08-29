package com.jnda.openweatherapp.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

// Define Qualifiers for named CoroutineDispatchers
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

@Singleton
class HttpClientFactory {
    // The lazy OkHttpClient instance can remain if desired,
    // ensuring the same base client is used for all builders created by this factory.
    private val baseOkHttpClient by lazy { OkHttpClient() }

    fun createBuilder(): OkHttpClient.Builder = baseOkHttpClient.newBuilder()
}


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    @IoDispatcher // Apply the qualifier
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    @MainDispatcher // Apply the qualifier
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @Singleton
    @DefaultDispatcher // Apply the qualifier
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Singleton
    internal fun provideHttpClientFactory(): HttpClientFactory {
        // If HttpClientFactory has dependencies, Hilt will inject them if it's annotated with @Inject constructor()
        // Or you can instantiate it manually if it's simple:
        return HttpClientFactory()
    }

    @Provides
    @Singleton
    internal fun provideOkHttpBuilder(
        httpClientFactory: HttpClientFactory
    ): OkHttpClient.Builder = httpClientFactory.createBuilder()

    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        clientBuilder: OkHttpClient.Builder,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        clientBuilder
            .readTimeout(90L, TimeUnit.SECONDS)
            .connectTimeout(90L, TimeUnit.SECONDS)
            .writeTimeout(90L, TimeUnit.SECONDS)

        // Use your application's BuildConfig
//        if (BuildConfig.DEBUG) {
//            clientBuilder.addInterceptor(loggingInterceptor)
//        }
        clientBuilder.addInterceptor(loggingInterceptor)

        return clientBuilder.build()
    }

    @Provides
    @Singleton // Gson can also be a singleton if used consistently
    internal fun provideGson(): Gson { // Made internal visible and a provider
        return GsonBuilder().setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson // Inject Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson)) // Use injected Gson
            .build()
    }

}