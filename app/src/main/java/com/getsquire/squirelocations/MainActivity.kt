package com.getsquire.squirelocations

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.getsquire.squirelocations.presentation.FragmentLocationsList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var api: LocationsApi


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, FragmentLocationsList())
                .commit()
        }


        // Dagger injection
        (application as App).daggerComponent.inject(this)

        // For Toothpic injection
//        (application as App).scope.inject(this)

        // Test output
        api.loadHome()
            .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    //todo добавить данные в адаптер туm
                    Timber.d(it.brand?.shops.toString())
                }
    }
}