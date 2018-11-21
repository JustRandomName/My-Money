package com.example.nikita.mymoney.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.nikita.mymoney.database.model.Card
import com.example.nikita.mymoney.database.model.Cash
import org.jetbrains.anko.db.*
import kotlin.jvm.Synchronized
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper

class DBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MyMoney", null, 1) {
    companion object {
        private var instance: DBHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DBHelper {
            if (instance == null) {
                instance = DBHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(Card.TABLENAME, true,
                "balance" to REAL)
        db.createTable(Cash.TABLENAME, true,
                "balance" to REAL)
        val values = ContentValues()
        values.put("balance", 5)
        db.insert(Cash.TABLENAME, null, values)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable("User", true)
    }
}