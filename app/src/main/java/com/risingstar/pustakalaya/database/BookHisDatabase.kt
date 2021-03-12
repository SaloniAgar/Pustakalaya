package com.risingstar.pustakalaya.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookHistoryEntity::class],version = 1)
abstract class BookHisDatabase : RoomDatabase() {

    abstract fun bookHisDao():BookHistoryDao
}