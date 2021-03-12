package com.risingstar.pustakalaya.database.forRelationship

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.risingstar.pustakalaya.database.BookEntity

data class FavlistWithBooks(
    @Embedded val favlist: FavEntity,
    @Relation(
        parentColumn = "favListId",
        entityColumn = "book_id",
        associateBy = Junction(FavolistBookCrossRef::class)
    ) val books : List<BookEntity>
)