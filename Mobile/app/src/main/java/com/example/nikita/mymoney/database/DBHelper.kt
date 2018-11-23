package com.example.nikita.mymoney.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.nikita.mymoney.database.model.*
import org.jetbrains.anko.db.*
import kotlin.jvm.Synchronized
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper

class DBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MyMoney", null, 7) {
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
        db.dropTable(Card.TABLENAME, true)
        db.dropTable(Cash.TABLENAME, true)

        db.createTable(Balance.TABLENAME, true,
                "balance" to REAL)
        db.createTable(Card.TABLENAME, true,
                "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "categoryId" to INTEGER,
                "cost" to REAL,
                "date" to TEXT,
                FOREIGN_KEY("categoryId", Category.TABLENAME, "id"))

        db.createTable(Cash.TABLENAME, true,
                "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "categoryId" to INTEGER,
                "cost" to REAL,
                "date" to TEXT,
                FOREIGN_KEY("categoryId", Category.TABLENAME, "id"))

        db.createTable(Category.TABLENAME, true,
                "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT + NOT_NULL,
                "name" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        if(oldVersion < 7) {
            db.dropTable(Card.TABLENAME, true)
            db.dropTable(Cash.TABLENAME, true)

            db.createTable(Balance.TABLENAME, true,
                    "balance" to REAL)
            db.createTable(Card.TABLENAME, true,
                    "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                    "categoryId" to INTEGER,
                    "cost" to REAL,
                    "date" to TEXT,
                    FOREIGN_KEY("categoryId", Category.TABLENAME, "id"))

            db.createTable(Cash.TABLENAME, true,
                    "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                    "categoryId" to INTEGER,
                    "cost" to REAL,
                    "date" to TEXT,
                    FOREIGN_KEY("categoryId", Category.TABLENAME, "id"))

            db.createTable(Category.TABLENAME, true,
                    "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                    "name" to TEXT)
        }
    }


}