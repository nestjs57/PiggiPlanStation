package com.arnoract.piggiplanstation.core

import android.content.Context
import android.content.SharedPreferences
import com.arnoract.piggiplanstation.core.KoinConst

class SharedPreferencesBuilder(val context: Context) {
    fun buildDefault(): SharedPreferences {
        return context.applicationContext.getSharedPreferences(
            KoinConst.SharedPreference.DEFAULT,
            Context.MODE_PRIVATE,
        )
    }
}
