package com.risingstar.pustakalaya.database.forRelationship

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["favListId","book_id"])
data class FavolistBookCrossRef(
    @NonNull val favListId:String,
    @ColumnInfo(index = true) @NonNull val book_id : String
)