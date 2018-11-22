package com.example.nikita.mymoney.database.model

class CashCategory(_name: String) : Category(_name) {
    companion object {
        const val TABLENAME: String = "CashCategory"
    }
}