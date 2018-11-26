package com.example.nikita.mymoney.database.manager

import android.content.Context
import com.example.nikita.mymoney.database.model.Balance
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.select

class BalanceManager(_ctx: Context) : SimpleManager(_ctx) {
    fun getBalance(): Double {
        return database.use {
            select(Balance.TABLE_NAME, Balance.BALANCE).exec {
                parseSingle(object : MapRowParser<Double> {
                    override fun parseRow(columns: Map<String, Any?>): Double {
                        return columns.getValue(Balance.BALANCE) as Double
                    }
                })
            }
        }
    }
}