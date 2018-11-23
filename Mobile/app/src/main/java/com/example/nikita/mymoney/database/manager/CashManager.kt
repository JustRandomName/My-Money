package com.example.nikita.mymoney.database.manager

import android.content.ContentValues
import android.content.Context
import com.example.nikita.mymoney.database.model.Cash
import java.time.LocalDateTime

class CashManager(_ctx: Context) : SimpleManager(_ctx) {

    fun addNew(categoryName: Int, categoryCost: Double) {
        database.use {
            val values = ContentValues()
            values.put("categoryId", categoryName)
            values.put("cost", categoryCost)
            values.put("date", LocalDateTime.now().toString())
            insert(Cash.TABLENAME, null, values)
        }
    }
}