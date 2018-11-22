package com.example.nikita.mymoney.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.nikita.mymoney.R
import com.example.nikita.mymoney.database.DBHelper
import com.example.nikita.mymoney.database.manager.SimpleManager
import kotlinx.android.synthetic.main.activity_start_activiti.*
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.doAsync


class StartActivity : AppCompatActivity() {

    val Context.database: DBHelper
        get() = DBHelper.getInstance(applicationContext)

    var manager: SimpleManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_activiti)
        setSupportActionBar(toolbar)
        manager = SimpleManager(applicationContext)
        loadAndShowData()
        card.setOnClickListener { openCardMenu() }
        cash.setOnClickListener { openCashMenu() }
    }

    private fun openCardMenu() {
        startActivity(Intent(this@StartActivity, CardActivity::class.java))
    }

    private fun openCashMenu() {
        startActivity(Intent(this@StartActivity, CashActivity::class.java))
    }

    @SuppressLint("SetTextI18n")
    fun loadAndShowData() {
        doAsync {
            var value: String? = manager?.getBalance().toString()
            activityUiThread {
                balance.text = "BALANCE : $value"
            }
        }
    }

}
