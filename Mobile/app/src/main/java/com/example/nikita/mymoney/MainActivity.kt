package com.example.nikita.mymoney

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.nikita.mymoney.database.DBHelper
import com.example.nikita.mymoney.views.StartActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val Context.database: DBHelper
        get() = DBHelper.getInstance(applicationContext)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        start.setOnClickListener {
            startActivity(Intent(this@MainActivity, StartActivity::class.java))
        }
    }
}

