package com.example.nikita.mymoney.database.manager

import android.content.Context
import com.example.nikita.mymoney.database.DBHelper
import com.example.nikita.mymoney.database.model.Model
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update

open class SimpleManager(_ctx: Context) {

    private var ctx: Context? = _ctx

    val database: DBHelper = DBHelper.getInstance(ctx!!)

    fun <T : Model> addNew(model: T): Long {
        return database.use {
            model.dbModel
            insert(model.tableName, null, model.dbModel)
        }
    }

    fun add() {}

    fun <T : Model> update(model: T) {
        return database.use {
            model.dbModel
//            update(model.tableName, model.dbModel) <- для этого переопределить метод
            update(model.tableName, model.dbModel, "id = ?", null) // <- хуй знает работает или нет, проверить не предоставляеться возможным
        }
    }

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
}