package com.risingstar.pustakalaya.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.risingstar.pustakalaya.database.forRelationship.FavEntity
import com.risingstar.pustakalaya.database.forRelationship.FavolistBookCrossRef
import com.risingstar.pustakalaya.database.forRelationship.UsersEntity

@Database(entities = [BookEntity ::class
                     /*,
                      UsersEntity::class,
                      FavEntity::class,
                      FavolistBookCrossRef::class*/
                    ],
          version = 1,
          exportSchema = false)
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDao():BookDao


}