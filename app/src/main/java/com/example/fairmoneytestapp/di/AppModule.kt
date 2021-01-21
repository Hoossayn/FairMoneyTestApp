package com.example.fairmoneytestapp.di

import android.content.Context
import com.example.fairmoneytestapp.BuildConfig
import com.example.fairmoneytestapp.BuildConfig.BASE_URL
import com.example.fairmoneytestapp.data.sources.local.DummyDatabase
import com.example.fairmoneytestapp.data.sources.local.DummyDatabaseImpl
import com.example.fairmoneytestapp.data.sources.local.DummyLocalDatabase
import com.example.fairmoneytestapp.data.sources.local.dao.UserDao
import com.example.fairmoneytestapp.data.sources.remote.DummyRemoteDataSource
import com.example.fairmoneytestapp.data.sources.remote.DummyRemoteDataSourceImpl
import com.example.fairmoneytestapp.data.sources.remote.DummyService
import com.example.fairmoneytestapp.data.sources.repository.Repository
import com.example.fairmoneytestapp.data.sources.repository.RepositoryImpl
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun providesUserDao(@ApplicationContext context: Context):UserDao{
        return DummyDatabase.getDatabase(context).userDao()
    }

    @Provides
    @Singleton
    fun providesRepository(
        remoteSource: DummyRemoteDataSource,
        localSource: DummyLocalDatabase
    ): Repository {
        return RepositoryImpl(remoteSource, localSource)
    }

    @Provides
    @Singleton
    fun providesRemoteDataSource(service: DummyService): DummyRemoteDataSource {
        return DummyRemoteDataSourceImpl(service)
    }

    @Provides
    @Singleton
    fun providesDummyService(retrofit: Retrofit): DummyService {
        return retrofit.create(DummyService::class.java)
    }

    @Singleton
    @Provides
    fun providesLocalDataSource(
        userDao: UserDao,
    ): DummyLocalDatabase {
        return DummyDatabaseImpl(userDao)
    }


    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(interceptor)
        }

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .client(builder.build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build()
    }

}