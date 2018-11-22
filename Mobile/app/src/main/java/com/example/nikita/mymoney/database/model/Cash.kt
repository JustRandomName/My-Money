package com.example.nikita.mymoney.database.model

class Cash(_balance: Double) : Money(_balance) {
    companion object {
        const val TABLENAME: String = "Cash"
    }
}