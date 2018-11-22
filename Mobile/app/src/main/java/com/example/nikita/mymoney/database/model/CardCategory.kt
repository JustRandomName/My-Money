package com.example.nikita.mymoney.database.model

class CardCategory(_name: String) : Category(_name) {
    companion object {
        const val TABLENAME: String = "CardCategory"
    }
}