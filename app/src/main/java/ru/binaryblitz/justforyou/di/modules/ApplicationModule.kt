package ru.binaryblitz.justforyou.di.modules

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.binaryblitz.justforyou.BuildConfig
import ru.binaryblitz.justforyou.data.cart.ProgramsStorage
import ru.binaryblitz.justforyou.data.user.UserStorageImpl
import ru.binaryblitz.justforyou.network.ApiService
import ru.binaryblitz.justforyou.network.MapService
import ru.binaryblitz.justforyou.network.MapsApiService
import ru.binaryblitz.justforyou.network.NetworkService
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit.NANOSECONDS
import javax.inject.Singleton

private const val DEFAULT_CONNECT_TIMEOUT = 60000L
private val HTTP_LOG_LEVEL = if (BuildConfig.DEBUG) BODY else BASIC
private val FITMOST_BASE_URL =
    if (BuildConfig.DEBUG) "https://justforyou-staging.herokuapp.com/" else "https://justforyou-production.herokuapp.com"
private val GOOGLE_API_BASE_URL = "https://maps.googleapis.com/maps/api/"

private fun createRetrofit(httpClient: OkHttpClient, moshi: Moshi, baseUrl: String): Retrofit {
  return Builder()
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .client(httpClient)
      .baseUrl(baseUrl)
      .build()
}

@Module
class ApplicationModule {
  @Provides
  @Singleton
  fun provideNetworkService(httpClient: OkHttpClient,
      moshi: Moshi): NetworkService {
    val retrofit = createRetrofit(httpClient, moshi, FITMOST_BASE_URL)
    val fitmostApi = retrofit.create(ApiService::class.java)

    return NetworkService(fitmostApi)
  }

  @Provides
  @Singleton
  fun provideMapService(httpClient: OkHttpClient,
      moshi: Moshi): MapService {
    val retrofit = createRetrofit(httpClient, moshi, GOOGLE_API_BASE_URL)
    val mapApi = retrofit.create(MapsApiService::class.java)

    return MapService(mapApi)
  }

  @Provides
  @Singleton
  fun provideProfileStorage(): UserStorageImpl {
    return UserStorageImpl()
  }

  @Provides
  @Singleton
  fun provideCartProgramsStorage(): ProgramsStorage {
    return ProgramsStorage()
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(DEFAULT_CONNECT_TIMEOUT, MILLISECONDS)
        .retryOnConnectionFailure(true)
        .readTimeout(DEFAULT_CONNECT_TIMEOUT, MILLISECONDS)
        .connectionPool(ConnectionPool(0, 1, NANOSECONDS))
        .writeTimeout(DEFAULT_CONNECT_TIMEOUT, MILLISECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply { level = HTTP_LOG_LEVEL })
        .build()
  }

  @Provides
  fun provideDefaultMoshi(): Moshi {
    return Moshi.Builder()
        .build()
  }
}