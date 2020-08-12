package com.getsquire.squirelocations

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.lang.reflect.Type
import javax.inject.Singleton

@Module
class DaggerModule(private var context: Context) {

    @Provides
    @Singleton
    fun provideAppContext(): Context? {
        return context
    }

    @Provides
    fun provideLocationApi(retrofit: Retrofit): LocationsApi {
        return retrofit.create(LocationsApi::class.java)
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .registerTypeAdapter(Location::class.java, LocationDeserializer())
            .create()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Provides
    fun provideOkHttp(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(
                HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        Timber.v(message)
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY)
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        }
        return builder.build()
    }
}

class LocationDeserializer : JsonDeserializer<Location> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Location? {
        val jsonObject = json?.asJsonObject ?: return null
        val coords = jsonObject["coordinates"].asJsonArray

        return Location(
            coords[0].asDouble,
            coords[1].asDouble
        )
    }
}