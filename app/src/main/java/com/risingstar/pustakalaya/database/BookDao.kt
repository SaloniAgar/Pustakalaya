package com.risingstar.pustakalaya.database

import androidx.room.*
import com.risingstar.pustakalaya.database.forRelationship.FavEntity
import com.risingstar.pustakalaya.database.forRelationship.FavolistBookCrossRef
import com.risingstar.pustakalaya.database.forRelationship.UserWithFavoriteAndBooks
import com.risingstar.pustakalaya.database.forRelationship.UsersEntity

@Dao
interface BookDao {

    @Insert
    fun insertBook(bookEntity: BookEntity)

    @Delete
   fun deleteBook(bookEntity: BookEntity)

   @Query("SELECT * FROM books WHERE book_id=:bookId")
   fun getBookById(bookId: String) : BookEntity

    @Query("SELECT * FROM books")
    fun getAllBooks():List<BookEntity>

    /*
   @Query("SELECT * FROM books WHERE book_id=:bookId")
   fun getBook(bookId: String) : BookEntity

   @Insert
   fun insertUser(usersEntity: UsersEntity)

   @Query("SELECT * FROM users WHERE user_id=:userId")
   fun getUser(userId: String?): UsersEntity

   @Insert
   fun insertFavList(favEntity: FavEntity)

   @Insert
   fun insertFavolistBookCrossRef(crossRef: FavolistBookCrossRef)


   @Delete
   fun deleteUser(usersEntity: UsersEntity)

    @Delete
    fun deleteFavolistBookCrossRef(crossRef: FavolistBookCrossRef)

    @Transaction
    @Query("SELECT * FROM users WHERE user_id = :userId")
    fun getUserWithFavoriteAndBooks(userId : String?) : List<UserWithFavoriteAndBooks>

    @Transaction
    @Query("SELECT * FROM FavolistBookCrossRef WHERE book_id=:bookId AND favListId=:favId")
    fun getCheckFavWithBook(bookId : String, favId : String?): FavolistBookCrossRef

     */
}