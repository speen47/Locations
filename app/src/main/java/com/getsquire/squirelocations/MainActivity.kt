package com.getsquire.squirelocations

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.getsquire.squirelocations.presentation.LocationsAdapter
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

        val recycler = findViewById<RecyclerView>(R.id.locationList)
        val adapter = LocationsAdapter()
        recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        // Dagger injection
        (application as App).daggerComponent.inject(this)

        // For Toothpic injection
//        (application as App).scope.inject(this)

        // Test output
        api.loadHome()
            .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    //todo добавить данные в адаптер тут
                    adapter.locations = it.brand?.shops ?: emptyList()
                    recycler.adapter = adapter
                    Timber.d(it.brand?.shops.toString())
                }
    }
}