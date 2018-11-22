package com.example.nikita.mymoney.views

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import com.example.nikita.mymoney.R

import kotlinx.android.synthetic.main.activity_cash.*

class CashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash)
        setSupportActionBar(toolbar)
    }

}
