package com.cute.connection.di


import com.cute.connection.BuildConfig
import com.cute.connection.api.ServerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


        @Provides
        @Singleton
        fun provideOkHttp():OkHttpClient {
            return OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(60,TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY })
                //.addNetworkInterceptor(networkInterceptor)
                .build()
        }

        @Provides
        @Singleton
        fun retrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL) // Hide base url in local properties
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun appServices(retrofit: Retrofit): ServerApi = retrofit.create(ServerApi::class.java)


}

