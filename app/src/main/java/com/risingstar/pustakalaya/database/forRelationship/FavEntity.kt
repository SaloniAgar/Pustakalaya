package com.risingstar.pustakalaya.database.forRelationship

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavEntity(
    @NonNull @PrimaryKey val favListId : String,
    val userCreatorId : String
)