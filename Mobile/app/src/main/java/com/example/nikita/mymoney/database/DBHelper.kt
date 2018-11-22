package com.example.nikita.mymoney.database


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.nikita.mymoney.database.model.Card
import com.example.nikita.mymoney.database.model.CardCategory
import com.example.nikita.mymoney.database.model.Cash
import com.example.nikita.mymoney.database.model.CashCategory
import org.jetbrains.anko.db.*

class DBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MyMoney", null, 2) {
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
        db.dropTable(CardCategory.TABLENAME, true)
        db.dropTable(CashCategory.TABLENAME, true)
        db.createTable(Card.TABLENAME, true,
                "id" to INTEGER + PRIMARY_KEY + NOT_NULL,
                "balance" to REAL)
        db.createTable(Cash.TABLENAME, true,
                "id" to INTEGER + PRIMARY_KEY + NOT_NULL,
                "balance" to REAL)
        db.createTable(CardCategory.TABLENAME, true,
                "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT + NOT_NULL,
                "name" to REAL,
                FOREIGN_KEY("cardId", Card.TABLENAME, "id"))
        db.createTable(CashCategory.TABLENAME, true,
                "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT + NOT_NULL,
                "name" to REAL,
                FOREIGN_KEY("cashId", Cash.TABLENAME, "id"))

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        if(oldVersion < 2) {
            db.dropTable(Card.TABLENAME, true)
            db.dropTable(Cash.TABLENAME, true)
            db.dropTable(CardCategory.TABLENAME, true)
            db.dropTable(CashCategory.TABLENAME, true)
            db.createTable(Card.TABLENAME, true,
                    "id" to INTEGER + PRIMARY_KEY + NOT_NULL,
                    "balance" to REAL)
            db.createTable(Cash.TABLENAME, true,
                    "id" to INTEGER + PRIMARY_KEY + NOT_NULL,
                    "balance" to REAL)
            db.createTable(CardCategory.TABLENAME, true,
                    "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT + NOT_NULL,
                    "name" to REAL,
                    FOREIGN_KEY("cardId", Card.TABLENAME, "id"))
            db.createTable(CashCategory.TABLENAME, true,
                    "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT + NOT_NULL,
                    "name" to REAL,
                    FOREIGN_KEY("cashId", Cash.TABLENAME, "id"))
        }
    }


}