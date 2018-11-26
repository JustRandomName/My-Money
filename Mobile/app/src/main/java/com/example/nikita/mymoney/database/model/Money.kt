package com.example.nikita.mymoney.database.model


open class Id {
    val ID = "id"
}

open class Money : Id() {
    val CATEGORY_ID: String = "categoryId"
    val COST: String = "cost"
    val DATE: String = "date"
}