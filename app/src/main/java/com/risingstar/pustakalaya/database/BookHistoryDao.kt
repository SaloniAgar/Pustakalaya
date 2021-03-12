package com.risingstar.pustakalaya.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookHistoryDao {

    @Insert
    fun insertBookHis(bookHistoryEntity: BookHistoryEntity)

    @Query("SELECT * FROM booksHis")
    fun getAllBooksHis(/*userId : String?*/):List<BookHistoryEntity>

    @Query("SELECT * FROM booksHis WHERE book_name=:bookName")
    fun getBookByNameHis(bookName : String):BookHistoryEntity

    @Query("DELETE FROM booksHis")
    fun clearHistory()
}