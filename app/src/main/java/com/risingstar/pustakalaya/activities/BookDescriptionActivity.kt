package com.risingstar.pustakalaya.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.risingstar.pustakalaya.R
import com.squareup.picasso.Picasso

class BookDescriptionActivity : AppCompatActivity() {

    lateinit var txtBookDescTitle : TextView
    lateinit var txtBookDescAuthor : TextView
    lateinit var txtBookDescPublisher : TextView
    lateinit var txtBookDescPublishDate : TextView
    lateinit var txtBookDesc : TextView
    lateinit var imgDescBook : ImageView


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_description)

        txtBookDescTitle = findViewById(R.id.txtRecyclerDescTitle)
        txtBookDescAuthor = findViewById(R.id.txtRecyclerDescAuthor)
        txtBookDescPublisher = findViewById(R.id.txtRecyclerDescPublisher)
        txtBookDescPublishDate = findViewById(R.id.txtRecyclerDescPublishDate)
        txtBookDesc = findViewById(R.id.txtBookDesc)
        imgDescBook = findViewById(R.id.imgBookDesc)

        txtBookDescTitle.text = intent.getStringExtra("book name")
        txtBookDescAuthor.text = "AUTHOR : ${intent.getStringExtra("book author")}"
        txtBookDescPublisher.text = "PUBLISHER : ${intent.getStringExtra("book publisher")}"
        txtBookDescPublishDate.text = "PUBLISH DATE : ${intent.getStringExtra("book publish date")}"
        txtBookDesc.text = intent.getStringExtra("book desc")

        Picasso.get().load(intent.getStringExtra("book image")).error(R.drawable.ic_book).into(imgDescBook)
    }
}
