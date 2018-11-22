package com.example.nikita.mymoney.database.manager

import android.content.ContentValues
import android.content.Context
import com.example.nikita.mymoney.database.model.CashCategory

class CashCategoryManager(_ctx: Context) : SimpleManager(_ctx) {
    fun addNewCategory(name: String, cost: String) {
        database.use {
            val values = ContentValues()
            values.put("name", name)
            values.put("cost", cost)
            insert(CashCategory.TABLENAME, null, values)
        }
    }
}