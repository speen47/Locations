package com.getsquire.squirelocations

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class LocationsApiProvider @Inject constructor(
    private val retrofit: Retrofit
) : Provider<LocationsApi> {
    override fun get(): LocationsApi {
        return retrofit
            .create(LocationsApi::class.java)
    }
}

class RetrofitProvider @Inject constructor(
    private val okHttpClient: OkHttpClient
) : Provider<Retrofit> {

    override fun get(): Retrofit {
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
}

class HttpClientProvider @Inject constructor(
) : Provider<OkHttpClient> {

    override fun get(): OkHttpClient {
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