package com.example.nikita.mymoney.database.manager

import android.content.Context
import com.example.nikita.mymoney.database.model.Cash
import com.example.nikita.mymoney.database.model.CashCategoryJoinTable
import com.example.nikita.mymoney.database.model.CashDTO
import com.example.nikita.mymoney.database.model.Category
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select

class CashManager(_ctx: Context) : SimpleManager(_ctx) {

    fun getCash(): List<CashDTO> {
        return database.use {
            select(Cash.TABLE_NAME + " left join " + Category.TABLE_NAME,
                    "Cash.id as cashId, Category.id as categoryId, Cash.name as cashName, Category.name as categoryName, cost, date")
                    .whereArgs("Cash.categoryId = Category.id")
                    .exec {
                        parseList<CashCategoryJoinTable>(classParser())
                    }
        }.map {
            CashDTO(id = it.cashId,
                    name = it.cashName,
                    category = Category(it.categoryId, it.categoryName),
                    cost = it.cost,
                    date = it.date)
        }
    }

    fun getCashByCategoryId(categoryId: Long): List<CashDTO> {
        return database.use {
            select(Cash.TABLE_NAME + " left join " + Category.TABLE_NAME,
                    "Cash.id as cashId, Category.id as categoryId, Cash.name as cashName, Category.name as categoryName, cost, date")
                    .whereArgs("Cash.categoryId = Category.id AND Category.Id = {categoryId}", "categoryId" to categoryId)
                    .exec {
                        parseList<CashCategoryJoinTable>(classParser())
                    }
        }.map {
            CashDTO(id = it.cashId,
                    name = it.cashName,
                    category = Category(it.categoryId, it.categoryName),
                    cost = it.cost,
                    date = it.date)
        }
    }
}
