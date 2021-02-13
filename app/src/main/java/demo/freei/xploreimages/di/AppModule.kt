package demo.freei.xploreimages.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import demo.freei.xploreimages.BuildConfig
import demo.freei.xploreimages.api.ImagesApi
import demo.freei.xploreimages.util.RequestConfig.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/*
https://developer.android.com/training/dependency-injection/hilt-android#kotlin
*/

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(createHttpClient())
        .build()

    private fun createHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(
            if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        )

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun provideImagesApi(retrofit: Retrofit): ImagesApi =
        retrofit.create(ImagesApi::class.java)

}