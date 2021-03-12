package com.risingstar.pustakalaya.database.forRelationship

import androidx.room.Embedded
import androidx.room.Relation
import com.risingstar.pustakalaya.database.forRelationship.FavEntity
import com.risingstar.pustakalaya.database.forRelationship.FavlistWithBooks
import com.risingstar.pustakalaya.database.forRelationship.UsersEntity

data class UserWithFavoriteAndBooks(
    @Embedded val user: UsersEntity,
    @Relation (
        entity = FavEntity::class ,
        parentColumn = "user_id",
        entityColumn = "userCreatorId"
    )
    val favList : FavlistWithBooks
)