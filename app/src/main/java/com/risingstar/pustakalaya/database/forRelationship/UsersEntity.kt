package com.risingstar.pustakalaya.database.forRelationship

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UsersEntity(
    @NonNull @PrimaryKey val user_id : String,
    val userName : String?
)