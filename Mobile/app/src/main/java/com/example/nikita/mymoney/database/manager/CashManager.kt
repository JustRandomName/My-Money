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

/*
    db.select("User", "name")
    .whereArgs("(_id > {userId}) and (name = {userName})",
    "userName" to "John",
    "userId" to 42)
*/

    /*data class CashDTO(var id: Long? = null, val category: Category, var name: String,
                       val cost: Double, val date: String = LocalDate.now().toString()) {*/

    fun getAllCategories(): List<Category> {
        return database.use {
            select(Category.TABLE_NAME).exec {
                parseList(classParser())
            }
        }
    }
}
