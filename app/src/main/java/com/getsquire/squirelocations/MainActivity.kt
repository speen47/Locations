package com.getsquire.squirelocations

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import toothpick.ktp.KTP
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var api: LocationsApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Dagger injection
        (application as App).daggerComponent.inject(this)

        // For Toothpic injection
//        (application as App).scope.inject(this)

        // Test output
        api.loadHome()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Timber.d(it.brand?.shops.toString())
                }
    }
}