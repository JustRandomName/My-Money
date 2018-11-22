package com.example.nikita.mymoney.database.manager

import android.content.Context
import com.example.nikita.mymoney.database.DBHelper
import com.example.nikita.mymoney.database.model.Cash
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import java.util.stream.Collectors

open class SimpleManager {

    private var ctx: Context? = null

    constructor(_ctx: Context) {
        ctx = _ctx
        database = DBHelper.getInstance(ctx!!)
    }

    val database: DBHelper

    fun add() {}
    fun delete() {}
    fun save() {}
    fun update() {}
    fun getCash() {}

    fun getBalance(): String {
        val r: List<Cash>? =
                database.use {
                    select(Cash.TABLENAME).exec {
                        parseList<Cash>(classParser())
                    }
                }
        return if (r != null && r.isNotEmpty()) {
            r.stream().map {
                it.balance
            }.collect(Collectors.toList()).reduce { acc, i -> acc + i }.toString()
        } else {
            "0"
        }
    }
}