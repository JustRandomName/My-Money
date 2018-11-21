package com.example.nikita.mymoney.views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.nikita.mymoney.R

import kotlinx.android.synthetic.main.activity_start_activiti.*

class StartActiviti : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_activiti)
        setSupportActionBar(toolbar)
        card.setOnClickListener { openCardMenu() }
        cash.setOnClickListener {  }
    }

    private fun openCardMenu() {
        startActivity(Intent(this@StartActiviti, Card :: class.java))
    }

    private fun openCashMenu() {
        startActivity(Intent(this@StartActiviti, Cash :: class.java))
    }

}
