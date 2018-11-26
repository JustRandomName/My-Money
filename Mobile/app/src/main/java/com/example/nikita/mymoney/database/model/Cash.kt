package com.example.nikita.mymoney.database.model

import android.content.ContentValues
import java.time.LocalDate

data class Cash(val id: Int? = null, val categoryId: Int, val cost: Double, val date: String = LocalDate.now().toString()) : Model() {
    override var tableName: String = TABLE_NAME

    companion object : Money() {
        val TABLE_NAME: String = "Cash"
    }

    override val dbModel: ContentValues
        get() {
            val values = ContentValues()
            values.put(CATEGORY_ID, categoryId)
            values.put(COST, cost)
            values.put(DATE, date)
            return values
        }
}