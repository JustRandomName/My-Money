package com.example.nikita.mymoney.database.manager

import android.content.Context
import com.example.nikita.mymoney.database.model.Cash
import com.example.nikita.mymoney.database.model.CashDTO
import com.example.nikita.mymoney.database.model.Category
import org.jetbrains.anko.db.*

class CashManager(_ctx: Context) : SimpleManager(_ctx) {

    //Category.id,  Cash.name, Category.name, cost, date
    fun getCash(): List<CashDTO> {
        return database.use {
            select(Cash.TABLE_NAME + " inner join " + Category.TABLE_NAME + " on Cash.categoryId = Category.id", "Category.id, Cash.name, Category.name, cost, date").exec {
                parseList(object : MapRowParser<CashDTO> {
                    override fun parseRow(columns: Map<String, Any?>): CashDTO {
                        return CashDTO(id = columns["Cash.id"] as Int, name = columns["Cash.name"] as String,
                                category = Category(columns["Category.id"] as Int, columns["Category.name"] as String)
                                , cost = columns["cost"] as Double, date = columns["date"] as String)
                    }
                })

            }
        }
    }

    fun getAllCategories(): List<Category> {
        return database.use {
            select(Category.TABLE_NAME).exec {
                parseList(classParser())
            }
        }
    }
}
