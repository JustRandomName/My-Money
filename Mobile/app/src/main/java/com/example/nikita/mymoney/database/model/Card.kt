package com.example.nikita.mymoney.database.model

class Card(_balance: Double) : Money(_balance) {
    companion object {
        const val TABLENAME: String = "Card"
    }
}