package com.example.nikita.mymoney.database.model

import android.content.ContentValues
import java.text.SimpleDateFormat
import java.util.*

class Card(override var id: Long? = null,
           val categoryId: Long,
           val cost: Double,
           val date: String = SimpleDateFormat("yyyy/MM/dd").format(Date())) : IdModel() {
    override var tableName: String = TABLE_NAME

    companion object : Money() {
        val TABLE_NAME: String = "Card"
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