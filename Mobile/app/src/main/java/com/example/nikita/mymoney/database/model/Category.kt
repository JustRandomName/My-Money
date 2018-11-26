package com.example.nikita.mymoney.database.model

import android.content.ContentValues

data class Category(val id: Int? = null, val name: String) : Model() {
    override var tableName: String = TABLE_NAME

    override val dbModel: ContentValues
        get() {
            val values = ContentValues()
            values.put(NAME, name)
            return values
        }

    override fun toString(): String {
        return name
    }

    companion object : Id() {
        const val TABLE_NAME: String = "Category"
        val NAME: String = "name"
    }
}