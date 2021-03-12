package com.risingstar.pustakalaya.fragments

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.risingstar.pustakalaya.R
import com.risingstar.pustakalaya.adapters.FavoriteAdapter
import com.risingstar.pustakalaya.database.BookDatabase
import com.risingstar.pustakalaya.database.BookEntity
import com.risingstar.pustakalaya.database.forRelationship.UserWithFavoriteAndBooks

class FavoritesFragment:Fragment() {

    lateinit var recyclerFav : RecyclerView
    lateinit var recyclerFavAdapter: FavoriteAdapter
    lateinit var rlNoFav:RelativeLayout
    lateinit var layoutManager: LinearLayoutManager
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout
    lateinit var dbBookList : List<BookEntity>
    //lateinit var dbBookList : List<UserWithFavoriteAndBooks>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_favorites,container,false)

        //val userid = arguments!!.getString("User Id")!!

        recyclerFav = view.findViewById(R.id.recycler_fav)
        rlNoFav = view.findViewById(R.id.rlNoFav)
        progressBar = view.findViewById(R.id.progressBar)
        progressLayout = view.findViewById(R.id.progressLayout)

        progressLayout.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE
        rlNoFav.visibility = View.GONE

        layoutManager = LinearLayoutManager(activity)

        /*dbBookList = RetrieveFavorites(activity as Context,userid).execute().get()

        if (dbBookList.isNotEmpty() && activity!=null){
            progressLayout.visibility = View.GONE
            progressBar.visibility = View.GONE
            recyclerFavAdapter = FavoriteAdapter(activity as Context,
                dbBookList as MutableList<UserWithFavoriteAndBooks>,userid)
            recyclerFav.adapter = recyclerFavAdapter
            recyclerFav.layoutManager = layoutManager
        }
        else{
            progressLayout.visibility = View.GONE
            progressBar.visibility = View.GONE
            rlNoFav.visibility = View.VISIBLE
        }*/

        dbBookList = RetrieveFavorites(activity as Context).execute().get()

        if (dbBookList.isNotEmpty() && activity!=null){
            progressLayout.visibility = View.GONE
            progressBar.visibility = View.GONE
            recyclerFavAdapter = FavoriteAdapter(activity as Context, dbBookList)
            recyclerFav.adapter = recyclerFavAdapter
            recyclerFav.layoutManager = layoutManager
        }
        else{
            progressLayout.visibility = View.GONE
            progressBar.visibility = View.GONE
            rlNoFav.visibility = View.VISIBLE
        }


        return view
    }

}

/*class RetrieveFavorites(val context: Context,val uid : String?):AsyncTask<Void,Void,List<UserWithFavoriteAndBooks>>(){
    override fun doInBackground(vararg params: Void?): List<UserWithFavoriteAndBooks> {
        val db = Room.databaseBuilder(context,BookDatabase::class.java,"book-db").build()
        return db.bookDao().getUserWithFavoriteAndBooks(uid)
    }

}*/
class RetrieveFavorites(val context: Context):AsyncTask<Void,Void,List<BookEntity>>(){
    override fun doInBackground(vararg params: Void?): List<BookEntity> {
        val db = Room.databaseBuilder(context,BookDatabase::class.java,"book-db").build()
        return db.bookDao().getAllBooks()
    }

}

