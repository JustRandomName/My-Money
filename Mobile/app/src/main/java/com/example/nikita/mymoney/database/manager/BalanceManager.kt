package com.example.nikita.mymoney.database.manager

import android.content.Context
import com.example.nikita.mymoney.database.model.Card
import com.example.nikita.mymoney.database.model.Cash
import org.jetbrains.anko.db.DoubleParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select

class BalanceManager(_ctx: Context) : SimpleManager(_ctx) {

    fun getBalance(): Double {
        return getBalance(Cash.TABLE_NAME) + getBalance(Card.TABLE_NAME)
    }

    private fun getBalance(tableName: String): Double {
        return database.use {
            select(tableName, "cost")
                    .whereArgs("strftime('%Y %m %d', _date) >= strftime('%Y %m %d', 'now', 'start of month')")
                    .exec {
                        parseList(DoubleParser)
                    }.sum()
        }
    }
}