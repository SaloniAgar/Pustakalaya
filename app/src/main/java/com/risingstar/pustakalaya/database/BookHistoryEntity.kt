package com.risingstar.pustakalaya.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "booksHis")
data class BookHistoryEntity(
    @PrimaryKey val book_name:String,
    @ColumnInfo(name = "book_author") val bookAuthor:String,
    @ColumnInfo(name = "book_img")val bookImg:String,
    @ColumnInfo(name = "book_publisher")val bookPublisher : String,
    @ColumnInfo(name = "book_publish_date")val bookPublishDate : String,
    @ColumnInfo(name = "book_description")val bookDesc : String
)