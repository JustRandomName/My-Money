package com.example.nikita.mymoney.database.model

import android.content.ContentValues

data class Balance(val balance: Double) : Model() {
    override var tableName: String = TABLE_NAME

    companion object {
        var TABLE_NAME: String = "Balance"
        val BALANCE: String = "balance"
    }

    override val dbModel: ContentValues
        get() {
            val values = ContentValues()
            values.put(BALANCE, balance)
            return values
        }

}