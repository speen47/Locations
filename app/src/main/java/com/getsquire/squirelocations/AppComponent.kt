package com.getsquire.squirelocations

import dagger.Component

@Component(modules = [DaggerModule::class])
interface AppComponent {
    fun inject(app: MainActivity)
}