package com.example.nikita.mymoney.database.manager

import android.content.Context
import android.provider.SyncStateContract.Helpers.insert
import com.example.nikita.mymoney.database.DBHelper
import com.example.nikita.mymoney.database.model.Cash
import com.example.nikita.mymoney.database.model.Model
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.parseSingle
import org.jetbrains.anko.db.select
import java.util.stream.Collectors

open class SimpleManager(_ctx: Context) {

    private var ctx: Context? = _ctx

    val database: DBHelper = DBHelper.getInstance(ctx!!)

    fun <T : Model> addNew(model: T) {
        database.use {
            model.dbModel
            insert(model.tableName, null, model.dbModel)
        }
    }

    fun add() {}

    fun <T : Model> update(model: T) {
        database.use {
            model.dbModel
            insert(model.tableName, null, model.dbModel)
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