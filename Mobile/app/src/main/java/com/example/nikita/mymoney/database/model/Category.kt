package com.example.nikita.mymoney.database.model

open class Category(_id: Int, _name: String) {
    companion object {
        const val TABLENAME: String = "Category"
    }

    var name: String = _name
    var id: Int = _id

    override fun toString(): String {
        return name
    }
}