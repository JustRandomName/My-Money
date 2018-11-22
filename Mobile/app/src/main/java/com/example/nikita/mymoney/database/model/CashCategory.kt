package com.example.nikita.mymoney.database.model

class CashCategory(_balance: Money) : Category(_balance) {
    companion object {
        const val TABLENAME: String = "CashCategory"
    }
}