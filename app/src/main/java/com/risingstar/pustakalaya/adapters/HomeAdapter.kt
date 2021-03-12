package com.risingstar.pustakalaya.adapters

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.risingstar.pustakalaya.activities.BookDescriptionActivity
import com.risingstar.pustakalaya.R
import com.risingstar.pustakalaya.database.*
import com.risingstar.pustakalaya.database.forRelationship.FavolistBookCrossRef
import com.risingstar.pustakalaya.models.Books
import com.squareup.picasso.Picasso
import java.util.ArrayList

class HomeAdapter(val context : Context , val itemList: ArrayList<Books>/*,val userId : String*/):RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    class HomeViewHolder(view:View):RecyclerView.ViewHolder(view){
        val txtRecyclerBookName : TextView = view.findViewById(R.id.txtRecyclerBookName)
        val txtRecyclerBookAuthor : TextView = view.findViewById(R.id.txtRecyclerBookAuthor)
        val imgBookHome : ImageView = view.findViewById(R.id.imgBookHome)
        //val llRecyclerBookHome : LinearLayout = view.findViewById(R.id.llRecyclerBookHome)
        val imgHomeFavIcon : ImageView = view.findViewById(R.id.imgHomeFavIcon)
        val btnReadHome : Button = view.findViewById(R.id.btnReadHome)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_book_single_row,parent,false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val books = itemList[position]
        holder.txtRecyclerBookName.text = books.bookName
        holder.txtRecyclerBookAuthor.text = books.bookAuthor
        Picasso.get().load(books.bookImg).error(R.drawable.ic_book).into(holder.imgBookHome)

        /*holder.llRecyclerBookHome.setOnClickListener {
            val intent = Intent(context,
                BookDescriptionActivity::class.java)
            intent.putExtra("book name",books.bookName)
            intent.putExtra("book author",books.bookAuthor)
            intent.putExtra("book publisher",books.bookPublisher)
            intent.putExtra("book publish date",books.bookPublishDate)
            intent.putExtra("book image",books.bookImg)
            intent.putExtra("book desc",books.bookDescription)
            context.startActivity(intent)
        }*/


        val bookEntity = BookEntity(
            book_id = books.bookId,
            bookName = holder.txtRecyclerBookName.text.toString(),
            bookAuthor = holder.txtRecyclerBookAuthor.text.toString(),
            bookImg = holder.imgBookHome.toString(),
            bookPublisher = books.bookPublisher,
            bookPublishDate = books.bookPublishDate,
            bookDesc = books.bookDescription
        )

        val isFav =DBAsyncTaskFav(context,bookEntity/*,userId*/,1).execute().get()

        if (isFav){
            holder.imgHomeFavIcon.setImageResource(R.drawable.ic_fav_fill)
        }
        else{
            holder.imgHomeFavIcon.setImageResource(R.drawable.ic_fav_outline)
        }

        holder.imgHomeFavIcon.setOnClickListener {
            if(!(DBAsyncTaskFav(context.applicationContext,bookEntity/*,userId*/,1).execute().get())){
                holder.imgHomeFavIcon.setImageResource(R.drawable.ic_fav_fill)
                val result = DBAsyncTaskFav(context,bookEntity/*,userId*/,2).execute().get()
                if (result){
                    Toast.makeText(context,"Book Added to Favorites", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(context,"Some problem occured", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                holder.imgHomeFavIcon.setImageResource(R.drawable.ic_fav_outline)
                val result = DBAsyncTaskFav(context,bookEntity/*,userId*/,3).execute().get()
                if (result){
                    Toast.makeText(context,"Book Removed from Favorites", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(context,"Some problem occured", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val bookHisEntity = BookHistoryEntity(
            book_name = holder.txtRecyclerBookName.text.toString(),
            bookAuthor = holder.txtRecyclerBookAuthor.text.toString(),
            bookImg = holder.imgBookHome.toString(),
            bookPublisher = books.bookPublisher,
            bookPublishDate = books.bookPublishDate,
            bookDesc = books.bookDescription
        )
        val isRead = DBAsyncTaskHistory(context,bookHisEntity,1).execute().get()
        if(isRead){
            val readColor = ContextCompat.getColor(context,R.color.colorRead)
            holder.btnReadHome.setBackgroundColor(readColor)
        }
        else{
            val notReadColor = ContextCompat.getColor(context,R.color.colorNotRead)
            holder.btnReadHome.setBackgroundColor(notReadColor)
        }

        holder.btnReadHome.setOnClickListener {
            val intent = Intent(context, BookDescriptionActivity::class.java)
            intent.putExtra("book name",books.bookName)
            intent.putExtra("book author",books.bookAuthor)
            intent.putExtra("book publisher",books.bookPublisher)
            intent.putExtra("book publish date",books.bookPublishDate)
            intent.putExtra("book image",books.bookImg)
            intent.putExtra("book desc",books.bookDescription)

            if(!(DBAsyncTaskHistory(context,bookHisEntity,1).execute().get())){
                val result = DBAsyncTaskHistory(context,bookHisEntity,2).execute().get()
                if(result) {
                    val readColor = ContextCompat.getColor(context, R.color.colorRead)
                    holder.btnReadHome.setBackgroundColor(readColor)
                }
                else{
                    Toast.makeText(context,"Some Problem occurred!!",Toast.LENGTH_SHORT).show()
                }
            }
            context.startActivity(intent)
        }


    }

}

class DBAsyncTaskFav(val context: Context, private val booksEntity : BookEntity,
                     //private val uId : String,
                     private val mode : Int) : AsyncTask<Int,Void,Boolean>() {

    val db = Room.databaseBuilder(context,BookDatabase::class.java,"book-db").build()
    override fun doInBackground(vararg params: Int?): Boolean? {

        /*val crossref =
            FavolistBookCrossRef(
                "F$uId",
                booksEntity.book_id
            )*/
        when(mode){

            1-> {
                //val buk: FavolistBookCrossRef? = db.bookDao().getCheckFavWithBook(booksEntity.book_id,"F$uId")
                val buk : BookEntity? = db.bookDao().getBookById(booksEntity.book_id)
                db.close()
                return buk!= null
            }

            2->{
                //val isBookThere:BookEntity? = db.bookDao().getBook(booksEntity.book_id)
                //if (isBookThere==null) {
                    db.bookDao().insertBook(booksEntity)
                //}
                //db.bookDao().insertFavolistBookCrossRef(crossref)
                db.close()
                return true
            }

            3->{
                db.bookDao().deleteBook(booksEntity)
                //db.bookDao().deleteFavolistBookCrossRef(crossref)
                db.close()
                return true
            }
        }
        return false
    }
}

class DBAsyncTaskHistory(val context: Context, private val booksHisEntity: BookHistoryEntity, val mode:Int) : AsyncTask<Void,Void,Boolean>(){

    val db = Room.databaseBuilder(context,BookHisDatabase::class.java,"book-his-db").build()
    override fun doInBackground(vararg params: Void?): Boolean {

        when(mode){

            1-> {
                val bukHis:BookHistoryEntity? = db.bookHisDao().getBookByNameHis(booksHisEntity.book_name)
                db.close()
                return bukHis!= null
            }

            2->{
                db.bookHisDao().insertBookHis(booksHisEntity)
                db.close()
                return true
            }

            /*3->{
                db.bookHisDao().clearHistory()
                db.close()
                return true
            }*/
        }
        return false
    }

}

