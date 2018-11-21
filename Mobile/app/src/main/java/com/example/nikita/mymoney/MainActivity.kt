package com.example.nikita.mymoney

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.nikita.mymoney.database.DBHelper
import com.example.nikita.mymoney.database.DBService
import com.example.nikita.mymoney.database.model.Cash
import org.jetbrains.anko.activityUiThread
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {
    private val Context.database: DBHelper
        get() = DBHelper.getInstance(applicationContext)

    private var service:DBService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        service = DBService(DBHelper.getInstance(applicationContext))
        loadAndShowData()
        /*loadBudget(R.layout.activity_main)*/
    }


    fun toastMe(view: View) {
        val myToast = Toast.makeText(this, "dd".toString(), Toast.LENGTH_SHORT)
        myToast.show()
    }


    @SuppressLint("SetTextI18n")
    fun increaseBudget(view: View) {
        val r:Double = database.use {
            select(Cash.TABLENAME).exec {
                parseList<Cash>(classParser()).last().balance
                /*parseSingle<Double>(classParser()) }*/
            }
        }
        val myToast = Toast.makeText(this, r.toString(), Toast.LENGTH_SHORT)
        myToast.show()
    }

    fun loadAndShowData() {
        doAsync {
            var r: Cash? = service?.helper?.use {
                select(Cash.TABLENAME).exec {
                    parseSingle(classParser())
                }
            }
            activityUiThread {
                toast("lol" + r?.balance.toString())
            }
        }

    }

}

