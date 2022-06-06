package com.arnoract.piggiplanstation.di

import com.arnoract.piggiplanstation.core.MyGsonBuilder
import com.arnoract.piggiplanstation.common.time.DefaultTimeProvider
import com.arnoract.piggiplanstation.common.time.TimeProvider
import com.arnoract.piggiplanstation.core.CoroutinesDispatcherProvider
import com.arnoract.piggiplanstation.core.OkHttpBuilder
import com.arnoract.piggiplanstation.core.RetrofitBuilder
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val coreModule = module {
    single { MyGsonBuilder().build() }
    single { OkHttpBuilder(androidApplication()).build() }
    single { RetrofitBuilder(get(), get()).build() }
    single { CoroutinesDispatcherProvider() }
    single<TimeProvider> { DefaultTimeProvider() }
}