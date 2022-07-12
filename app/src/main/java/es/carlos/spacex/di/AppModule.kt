package es.carlos.spacex.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.carlos.spacex.common.Constants
import es.carlos.spacex.data.remote.SpaceApi
import es.carlos.spacex.data.repository.SpaceRepositoryImpl
import es.carlos.spacex.domain.repository.SpaceRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSpaceApi(): SpaceApi {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(SpaceApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSpaceRepository(spaceApi: SpaceApi): SpaceRepository =
        SpaceRepositoryImpl(spaceApi)
}