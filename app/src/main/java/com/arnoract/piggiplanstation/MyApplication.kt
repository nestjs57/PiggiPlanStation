package com.arnoract.piggiplanstation

import android.app.Application
import com.arnoract.piggiplanstation.di.AppComponent
import com.arnoract.piggiplanstation.domain.DomainComponent
import com.arnoract.piggiplanstation.ui.di.UiComponent
import com.google.android.libraries.places.api.Places
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.*

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
//        Places.initialize(applicationContext, getString(R.string.google_maps_key), Locale.US)
        startKoin {
            androidContext(this@MyApplication)
        }
        AppComponent.init()
        UiComponent.init()
        DomainComponent.init()
    }
}