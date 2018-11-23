package com.example.nikita.mymoney.database.model

import java.math.BigDecimal

class Balance(_balance: BigDecimal) {
    companion object {
        const val TABLENAME:String = "Balance"
    }
    var balance: BigDecimal = _balance
}