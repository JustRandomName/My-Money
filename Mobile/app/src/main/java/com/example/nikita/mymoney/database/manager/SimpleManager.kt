package com.example.nikita.mymoney.database.manager

import android.content.Context
import com.example.nikita.mymoney.database.DBHelper
import com.example.nikita.mymoney.database.model.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.select

open class SimpleManager(_ctx: Context) {

    private var ctx: Context? = _ctx

    val database: DBHelper = DBHelper.getInstance(ctx!!)

    fun <T : IdModel> saveOrUpdate(model: T): Long? {
        if (model.id != null) {
            database.use {
                update(model.tableName, model.dbModel, "id = ?", arrayOf(model.id.toString()))
            }
        } else {
            return database.use {
                model.dbModel
                insert(model.tableName, null, model.dbModel)
            }
        }
        return model.id
    }

    fun add() {}

    inline fun <reified T : Model> getById(id: Int, tableName: String): T {
        return database.use {
            select(tableName)
                    .whereArgs("id = {id}",
                            "id" to id.toString())
                    .exec {
                        parseSingle(classParser())
                    }
        }
    }

    fun getAllCategories(): List<Category> {
        return database.use {
            select(Category.TABLE_NAME).orderBy("id").exec {
                parseList(classParser())
            }
        }
    }


    fun getAllCategoriesDTOs(): List<CategoryDTO> {
        val cash: List<CategoryDTO> = database.use {
            select(Category.TABLE_NAME + " LEFT OUTER JOIN " + Cash.TABLE_NAME,
                    "Category.id as catId, Category.name as name, cost")
                    .whereArgs("Cash.categoryId = Category.id")
                    .groupBy("Category.id")
                    .exec {
                        parseList(classParser())
                    }
        }


        val card: List<CategoryDTO> = database.use {
            select(Category.TABLE_NAME + " LEFT OUTER JOIN " + Card.TABLE_NAME,
                    "Category.id as catId, Category.name as name, cost")
                    .whereArgs("Card.categoryId = Category.id")
                    .groupBy("Category.id")
                    .exec {
                        parseList(classParser())
                    }
        }

        val k = getAllCategories().map { x ->
            CategoryDTO(catId = x.id, name = x.name, cost = cash.filter {
                it.catId == x.id
            }.map {
                it.cost
            }.fold(0.0) { acc, s -> acc + s!! })
        }
        k.forEach { y ->
            y.cost = y.cost!! + card.filter {
                it.catId == y.catId
            }.map {
                it.cost
            }.fold(0.0) { acc, s -> acc + s!! }
        }
        return k
    }


    fun <T : IdModel> remove(model: T) {
        database.use {
            delete(model.tableName, "id = ?", arrayOf(model.id.toString()))
        }
    }
}