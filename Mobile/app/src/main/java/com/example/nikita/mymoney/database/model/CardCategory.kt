package com.example.nikita.mymoney.database.model

class CardCategory(_balance: Money) : Category(_balance) {
    companion object {
        const val TABLENAME: String = "CardCategory"
    }
}