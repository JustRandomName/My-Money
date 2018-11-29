package com.example.nikita.mymoney.database.manager

import android.content.Context
import com.example.nikita.mymoney.database.DBHelper
import com.example.nikita.mymoney.database.model.Category
import com.example.nikita.mymoney.database.model.IdModel
import com.example.nikita.mymoney.database.model.Model
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.select

open class SimpleManager(_ctx: Context) {

    private var ctx: Context? = _ctx

    val database: DBHelper = DBHelper.getInstance(ctx!!)

    fun <T : IdModel> saveOrUpdate(model: T): Long? {
        if(model.id != null){
            database.use {
                update(model.tableName, model.dbModel,"id = ?", arrayOf(model.id.toString()))
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

    fun <T : IdModel> remove(model: T) {
        database.use {
            delete(model.tableName, "id = ?", arrayOf(model.id.toString()))
        }
    }

}