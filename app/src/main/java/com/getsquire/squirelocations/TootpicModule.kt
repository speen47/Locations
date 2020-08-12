package com.getsquire.squirelocations

import android.app.Application
import android.location.LocationProvider
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import toothpick.smoothie.module.SmoothieApplicationModule
import java.lang.reflect.Type

class AppModule(application: Application) :
    SmoothieApplicationModule(application, "com.getsquire.squire_prefs") {

    init {
        /* Network */
        bind(OkHttpClient::class.java).toProvider(HttpClientProvider::class.java).singleton()
        bind(Retrofit::class.java).toProvider(RetrofitProvider::class.java).singleton()
        bind(LocationsApi::class.java).toProvider(LocationsApiProvider::class.java).singleton()
    }
}