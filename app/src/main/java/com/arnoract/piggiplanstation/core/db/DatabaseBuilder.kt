package com.arnoract.piggiplanstation.core.db

import android.content.Context
import androidx.room.Room

class DatabaseBuilder(private val context: Context) {
    fun buildRoomDatabaseStorage(): RoomDatabaseStorage {
        return Room.databaseBuilder(
            context,
            RoomDatabaseStorage::class.java,
            "room-database-storage"
        )
            .build()
    }
}