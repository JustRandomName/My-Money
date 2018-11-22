package com.example.nikita.mymoney.database.model

open class Money(_balance: Double) {
    var balance: Double = _balance
        set(value) {
            balance = value
        }

}