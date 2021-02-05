package com.getsquire.squirelocations

import com.getsquire.squirelocations.presentation.FragmentLocationsList
import dagger.Component

@Component(modules = [DaggerModule::class])
interface AppComponent {
    fun inject(app: MainActivity)
    fun inject(fragmentLocationsList: FragmentLocationsList)

}