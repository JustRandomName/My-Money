package com.example.nikita.mymoney.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.nikita.mymoney.database.model.Balance
import com.example.nikita.mymoney.database.model.Card
import com.example.nikita.mymoney.database.model.Cash
import com.example.nikita.mymoney.database.model.Category
import org.jetbrains.anko.db.*

class DBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MyMoney", null, 15) {
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
        db.dropTable(Card.TABLE_NAME, true)
        db.dropTable(Cash.TABLE_NAME, true)

        db.createTable(Balance.TABLE_NAME, true,
                Balance.BALANCE to REAL)
        db.createTable(Card.TABLE_NAME, true,
                Card.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Card.CATEGORY_ID to INTEGER,
                Card.COST to REAL,
                Card.DATE to TEXT,
                FOREIGN_KEY(Card.CATEGORY_ID, Category.TABLE_NAME, Category.ID))

        db.createTable(Cash.TABLE_NAME, true,
                Cash.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Cash.CATEGORY_ID to INTEGER,
                Cash.COST to REAL,
                Cash.NAME to TEXT,
                Cash.DATE to TEXT,
                FOREIGN_KEY(Cash.CATEGORY_ID, Category.TABLE_NAME, Category.ID))

        db.createTable(Category.TABLE_NAME, true,
                Category.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Category.NAME to TEXT)
        db.createTable(Balance.TABLE_NAME, true,
                Balance.BALANCE to REAL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        if (oldVersion < 15) {
            db.dropTable(Card.TABLE_NAME, true)
            db.dropTable(Cash.TABLE_NAME, true)

            db.createTable(Balance.TABLE_NAME, true,
                    Balance.BALANCE to REAL)
            db.createTable(Card.TABLE_NAME, true,
                    Card.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                    Card.CATEGORY_ID to INTEGER,
                    Card.COST to REAL,
                    Card.DATE to TEXT,
                    FOREIGN_KEY(Card.CATEGORY_ID, Category.TABLE_NAME, Category.ID))

            db.createTable(Cash.TABLE_NAME, true,
                    Cash.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                    Cash.CATEGORY_ID to INTEGER,
                    Cash.NAME to TEXT,
                    Cash.COST to REAL,
                    Cash.DATE to TEXT,
                    FOREIGN_KEY(Cash.CATEGORY_ID, Category.TABLE_NAME, Category.ID))

            db.createTable(Category.TABLE_NAME, true,
                    Category.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                    Category.NAME to TEXT)
            db.createTable(Balance.TABLE_NAME, true,
                    Balance.BALANCE to REAL)
            db.insert(Balance.TABLE_NAME, null, Balance(balance = 0.0).dbModel)
            db.insert(Category.TABLE_NAME, null, Category(name = "Food").dbModel)
            db.insert(Category.TABLE_NAME, null, Category(name = "Fuel").dbModel)
            db.insert(Category.TABLE_NAME, null, Category(name = "Сlothes").dbModel)
        }
    }


}