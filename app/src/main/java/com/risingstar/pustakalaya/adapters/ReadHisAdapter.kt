package com.risingstar.pustakalaya.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.risingstar.pustakalaya.R
import com.risingstar.pustakalaya.database.BookHistoryEntity
import com.squareup.picasso.Picasso

class ReadHisAdapter(val context: Context , val itemList : List<BookHistoryEntity>):RecyclerView.Adapter<ReadHisAdapter.ReadHisViewHolder>() {
    class ReadHisViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val txtRecyclerBookNameHis : TextView = view.findViewById(R.id.txtRecyclerBookNameHis)
        val txtRecyclerBookAuthorHis : TextView = view.findViewById(R.id.txtRecyclerBookAuthorHis)
        val imgBookHis : ImageView = view.findViewById(R.id.imgBookHis)
        val rlHis : RelativeLayout = view.findViewById(R.id.rlHis)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadHisViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_history_single_row,parent,false)
        return ReadHisViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ReadHisViewHolder, position: Int) {
        val books : BookHistoryEntity = itemList[position]

        holder.txtRecyclerBookNameHis.text = books.book_name
        holder.txtRecyclerBookAuthorHis.text = books.bookAuthor
        Picasso.get().load(books.bookImg).error(R.drawable.ic_book).into(holder.imgBookHis)
    }
}