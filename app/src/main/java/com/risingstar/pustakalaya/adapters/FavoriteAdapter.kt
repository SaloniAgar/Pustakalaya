package com.risingstar.pustakalaya.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.risingstar.pustakalaya.R
import com.risingstar.pustakalaya.activities.BookDescriptionActivity
import com.risingstar.pustakalaya.database.BookEntity
import com.risingstar.pustakalaya.database.BookHistoryEntity
import com.risingstar.pustakalaya.database.forRelationship.UserWithFavoriteAndBooks
import com.squareup.picasso.Picasso

class FavoriteAdapter(val context: Context,
                      val itemList : List<BookEntity>
    /*var itemList:MutableList<UserWithFavoriteAndBooks>, val userId : String*/
):RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view){

        //val llRecyclerBookFav : LinearLayout = view.findViewById(R.id.llRecyclerBookFav)
        val txtRecyclerBookNameFav : TextView = view.findViewById(R.id.txtRecyclerBookNameFav)
        val txtRecyclerBookAuthorFav : TextView = view.findViewById(R.id.txtRecyclerBookAuthorFav)
        val imgBookFav : ImageView = view.findViewById(R.id.imgBookFav)
        val imgFavIcon : ImageView = view.findViewById(R.id.imgFavIcon)
        val btnReadFav : Button = view.findViewById(R.id.btnReadFav)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_fav_single_row,parent,false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        //val boks  = itemList[position].favList//.books[position]
        //val books = itemList.single().favList.books[position]
        val books = itemList[position]
        holder.txtRecyclerBookNameFav.text = books.bookName
        holder.txtRecyclerBookAuthorFav.text = books.bookAuthor
        Picasso.get().load(books.bookImg).error(R.drawable.ic_book).into(holder.imgBookFav)

        val bookEntity = BookEntity(
            book_id = books.book_id,
            bookName = holder.txtRecyclerBookNameFav.text.toString(),
            bookAuthor = holder.txtRecyclerBookAuthorFav.text.toString(),
            bookImg = holder.imgBookFav.toString(),
            bookPublisher = books.bookPublisher,
            bookPublishDate = books.bookPublishDate,
            bookDesc = books.bookDesc
        )

        val isFav =DBAsyncTaskFav(context,bookEntity/*,userId*/,1).execute().get()

        if (isFav){
            holder.imgFavIcon.setImageResource(R.drawable.ic_fav_fill)
        }
        else{
            holder.imgFavIcon.setImageResource(R.drawable.ic_fav_outline)
        }

        holder.imgFavIcon.setOnClickListener {
            if(!(DBAsyncTaskFav(context.applicationContext,bookEntity/*,userId*/,1).execute().get())){
                holder.imgFavIcon.setImageResource(R.drawable.ic_fav_fill)
                val result = DBAsyncTaskFav(context,bookEntity/*,userId*/,2).execute().get()
                if (result){
                    Toast.makeText(context,"Book Added to Favorites",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(context,"Some problem occured",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                holder.imgFavIcon.setImageResource(R.drawable.ic_fav_outline)
                val result = DBAsyncTaskFav(context,bookEntity/*,userId*/,3).execute().get()
                if (result){
                    Toast.makeText(context,"Book Removed from Favorites",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(context,"Some problem occured",Toast.LENGTH_SHORT).show()
                }
            }
        }

        /*holder.llRecyclerBookFav.setOnClickListener {
            val intent = Intent(context,BookDescriptionActivity::class.java)
            intent.putExtra("book name",books.book_name)
            intent.putExtra("book author",books.bookAuthor)
            intent.putExtra("book publisher",books.bookPublisher)
            intent.putExtra("book publish date",books.bookPublishDate)
            intent.putExtra("book image",books.bookImg)
            intent.putExtra("book desc",books.bookDesc)
            context.startActivity(intent)
        }*/

        val bookHisEntity = BookHistoryEntity(
            book_name = holder.txtRecyclerBookNameFav.text.toString(),
            bookAuthor = holder.txtRecyclerBookAuthorFav.text.toString(),
            bookImg = holder.imgBookFav.toString(),
            bookPublisher = books.bookPublisher,
            bookPublishDate = books.bookPublishDate,
            bookDesc = books.bookDesc
        )
        val isRead = DBAsyncTaskHistory(context,bookHisEntity,1).execute().get()
        if(isRead){
            val readColor = ContextCompat.getColor(context,R.color.colorRead)
            holder.btnReadFav.setBackgroundColor(readColor)
        }
        else{
            val notReadColor = ContextCompat.getColor(context,R.color.colorNotRead)
            holder.btnReadFav.setBackgroundColor(notReadColor)
        }

        holder.btnReadFav.setOnClickListener {
            val intent = Intent(context, BookDescriptionActivity::class.java)
            intent.putExtra("book name",books.bookName)
            intent.putExtra("book author",books.bookAuthor)
            intent.putExtra("book publisher",books.bookPublisher)
            intent.putExtra("book publish date",books.bookPublishDate)
            intent.putExtra("book image",books.bookImg)
            intent.putExtra("book desc",books.bookDesc)

            if(!(DBAsyncTaskHistory(context,bookHisEntity,1).execute().get())){
                val result = DBAsyncTaskHistory(context,bookHisEntity,2).execute().get()
                if(result) {
                    val readColor = ContextCompat.getColor(context, R.color.colorRead)
                    holder.btnReadFav.setBackgroundColor(readColor)
                }
                else{
                    Toast.makeText(context,"Some Problem occurred!!",Toast.LENGTH_SHORT).show()
                }
            }
            context.startActivity(intent)
        }
    }
}