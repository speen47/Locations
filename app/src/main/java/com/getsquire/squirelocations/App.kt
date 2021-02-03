package com.getsquire.squirelocations

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber

class App : Application() {

    // DI roots
    lateinit var daggerComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        daggerComponent = DaggerAppComponent.builder()
            .daggerModule(DaggerModule(this))
            .build()

        Timber.plant(Timber.DebugTree())
    }
}