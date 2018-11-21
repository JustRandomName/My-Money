package com.example.nikita.mymoney.database.model

class Cash(_balance: Double) {
    val balance: Double = _balance

    companion object {
        const val TABLENAME: String = "Cash"
    }
}