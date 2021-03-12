package com.risingstar.pustakalaya.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.risingstar.pustakalaya.R
import com.risingstar.pustakalaya.adapters.HomeAdapter
import com.risingstar.pustakalaya.database.BookDatabase
import com.risingstar.pustakalaya.database.forRelationship.FavEntity
import com.risingstar.pustakalaya.database.forRelationship.UsersEntity
import com.risingstar.pustakalaya.models.Books
import com.risingstar.pustakalaya.utility.ConnectionManager
import org.json.JSONException

class HomeFragment : Fragment() {

    lateinit var recyclerHome : RecyclerView
    lateinit var recyclerHomeAdapter: HomeAdapter
    lateinit var layoutManager: LinearLayoutManager
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout

    var bookList = arrayListOf<Books>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home,container,false)
        recyclerHome = view.findViewById(R.id.recycler_home)
        progressBar = view.findViewById(R.id.progressBar)
        progressLayout = view.findViewById(R.id.progressLayout)

        progressLayout.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE

        layoutManager = LinearLayoutManager(activity)

        val userId  = arguments!!.getString("User Id")!!
        val userName = arguments?.getString("User name")

        //DBAsyncUser(activity as Context,userId,userName).execute()


        val queue = Volley.newRequestQueue(activity as Context)
        val url = "https://rapidapi.p.rapidapi.com/volumes?key=AIzaSyAOsteuaW5ifVvA_RkLXh0mYs6GLAD6ykc"

        if(ConnectionManager().checkConnectivity(activity as Context)){
            val jsonObject = object : JsonObjectRequest(Request.Method.GET,url,null,Response.Listener {

                try {
                    progressLayout.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    val items = it.getJSONArray("items")
                    for(i in 0 until items.length()){
                        val itemNumber = items.getJSONObject(i)
                        val booksList = Books(
                            itemNumber.getString("id"),
                        itemNumber.getJSONObject("volumeInfo").getString("title"),
                        itemNumber.getJSONObject("volumeInfo").getJSONArray("authors").getString(0),
                        itemNumber.getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("smallThumbnail"),
                        itemNumber.getJSONObject("volumeInfo").getString("publisher"),
                        itemNumber.getJSONObject("volumeInfo").getString("publishedDate"),
                        itemNumber.getJSONObject("volumeInfo").getString("description")
                        )

                        bookList.add(booksList)
                        recyclerHomeAdapter =
                            HomeAdapter(
                                activity as Context,
                                bookList
                                /*,userId*/
                            )
                        recyclerHome.adapter = recyclerHomeAdapter
                        recyclerHome.layoutManager = layoutManager
                    }
                }
                catch(e:JSONException){

                }
            },Response.ErrorListener {

            }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String,String>()
                    headers["x-rapidapi-key"] = "19369008c7mshc5093f96c09d354p1851bbjsnc788d558011a"
                    headers["x-rapidapi-host"] = "google-books.p.rapidapi.com"
                    return headers
                }
            }
            queue.add(jsonObject)

        }
        else{
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection Not Found")
            dialog.setPositiveButton("Open Settings"){text,listener ->
                val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                activity?.finish()

            }
            dialog.setNegativeButton("Exit"){text,listener ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }





        return view
    }

}

/*class DBAsyncUser(val context: Context,val userId: String,val userName : String?):AsyncTask<Void,Void,Boolean>(){
    override fun doInBackground(vararg params: Void?): Boolean {
        val userEntity =
            UsersEntity(
                userId,
                userName
            )
        val favEntity =
            FavEntity(
                "F$userId",
                userId
            )
        val db = Room.databaseBuilder(
            context,
            BookDatabase::class.java,
            "book-db").build()
        val userIsThere: UsersEntity? = db.bookDao().getUser(userId)
        if(userIsThere==null) {
            db.bookDao().insertUser(userEntity)
            db.bookDao().insertFavList(favEntity)
        }
        db.close()

        return true
    }

}*/
