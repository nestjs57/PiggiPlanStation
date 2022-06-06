package com.arnoract.piggiplanstation.ui.di

import com.arnoract.piggiplanstation.mainModule
import org.koin.core.context.loadKoinModules

object UiComponent {
    fun init() = loadKoinModules(
        listOf(
            mainModule
        )
    )
}