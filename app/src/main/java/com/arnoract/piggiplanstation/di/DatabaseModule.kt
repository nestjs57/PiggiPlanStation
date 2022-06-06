package com.arnoract.piggiplanstation.di

import com.arnoract.piggiplanstation.core.db.DatabaseBuilder
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { DatabaseBuilder(androidApplication()).buildRoomDatabaseStorage() }
}