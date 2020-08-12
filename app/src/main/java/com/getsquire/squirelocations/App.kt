package com.getsquire.squirelocations

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber
import toothpick.Scope
import toothpick.Toothpick
import toothpick.ktp.KTP

class App : Application() {

    // DI roots
    lateinit var scope: Scope
    lateinit var daggerComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        scope = KTP.openScope(this).installModules(AppModule(this))

        daggerComponent = DaggerAppComponent.builder()
            .daggerModule(DaggerModule(this))
            .build()

        Timber.plant(Timber.DebugTree())
    }
}