package com.risingstar.pustakalaya.fragments

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.risingstar.pustakalaya.R
import com.risingstar.pustakalaya.adapters.ReadHisAdapter
import com.risingstar.pustakalaya.database.BookHisDatabase
import com.risingstar.pustakalaya.database.BookHistoryEntity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [ReadHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReadHistoryFragment : Fragment() {

    lateinit var recyclerHis : RecyclerView
    lateinit var recyclerHisAdapter: ReadHisAdapter
    lateinit var rlNoHis: RelativeLayout
    lateinit var btnClearHis : Button
    lateinit var layoutManager: GridLayoutManager
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout
    lateinit var dbBookHisList : List<BookHistoryEntity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_read_history, container, false)

       // val userId = arguments?.getString("User Id")

        recyclerHis = view.findViewById(R.id.recycler_his)
        rlNoHis = view.findViewById(R.id.rlNoHis)
        btnClearHis = view.findViewById(R.id.btnClearHis)
        progressBar = view.findViewById(R.id.progressBar)
        progressLayout = view.findViewById(R.id.progressLayout)

        progressLayout.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE
        rlNoHis.visibility = View.GONE
        btnClearHis.visibility = View.INVISIBLE

        layoutManager = GridLayoutManager(activity,2)

        dbBookHisList = RetrieveHistoryDBAsync(activity as Context/*,userId*/).execute().get()

        if (dbBookHisList.isNotEmpty() && activity!=null){
            progressLayout.visibility = View.GONE
            progressBar.visibility = View.GONE
            recyclerHisAdapter = ReadHisAdapter(activity as Context,dbBookHisList)
            recyclerHis.adapter = recyclerHisAdapter
            recyclerHis.layoutManager = layoutManager
            btnClearHisVisiblity()
        }
        else{
            progressLayout.visibility = View.GONE
            progressBar.visibility = View.GONE
            rlNoHis.visibility = View.VISIBLE
        }

        return view
    }

    private fun btnClearHisVisiblity(){
        btnClearHis.visibility = View.VISIBLE
        btnClearHis.setOnClickListener {
            val clearHisDBAsync = ClearHisDBAsync(activity as Context).execute().get()
            dbBookHisList.isEmpty()
            Toast.makeText(activity as Context,"History Cleared",Toast.LENGTH_SHORT).show()
            btnClearHis.visibility = View.GONE
            recyclerHis.visibility = View.GONE
            rlNoHis.visibility = View.VISIBLE
        }
    }

}

class RetrieveHistoryDBAsync(val context: Context/*,val userid : String?*/):AsyncTask<Void,Void,List<BookHistoryEntity>>(){

    val db = Room.databaseBuilder(context, BookHisDatabase::class.java,"book-his-db").build()

    override fun doInBackground(vararg params: Void?): List<BookHistoryEntity> {
        return db.bookHisDao().getAllBooksHis(/*userid*/)
    }

}

class ClearHisDBAsync(val context: Context):AsyncTask<Void,Void,Boolean>(){

    val db = Room.databaseBuilder(context,BookHisDatabase::class.java,"book-his-db").build()
    override fun doInBackground(vararg params: Void?): Boolean {
        db.bookHisDao().clearHistory()
        db.close()
        return true
    }

}