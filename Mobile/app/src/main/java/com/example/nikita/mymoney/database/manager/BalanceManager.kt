package com.example.nikita.mymoney.database.manager

import android.content.Context
import com.example.nikita.mymoney.database.model.Balance
import com.example.nikita.mymoney.database.model.Card
import com.example.nikita.mymoney.database.model.Cash
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select
import java.text.SimpleDateFormat
import java.util.*


class BalanceManager(_ctx: Context) : SimpleManager(_ctx) {


    /*select date(from_unixtime(timestamp)) from mytabel where date(from_unixtime(timestamp))
    between '2014-10-09' and '2014-10-10'*/

    //select * from mytable where `date` >= '2014-10-09' and `date` <= '2014-10-10'
    fun getb(): Any {
        var balance: Double = 0.0
        var date: Date = Date()
        Calendar.YEAR
        var cash:List<Double> =  database.use {
            select(Cash.TABLE_NAME)
                    .whereArgs("date(datetime(date, 'unixepoch'))  between {startDate} and {endDate}",
                            "startDate" to SimpleDateFormat("yyyy-dd-MM").format(getFirstDayOfMonth(Date())),
                            "endDate" to SimpleDateFormat("yyyy-dd-MM").format(getLastDayOfQuarter(Date()).time)).exec {
                        parseList<Cash>(classParser())
                    }.map { it.cost }/*.reduce { sum, element -> sum + element }*/
        }

        var card:List<Double> = database.use {
            select(Card.TABLE_NAME)
                    .whereArgs("date(datetime(date, 'unixepoch'))  between {startDate} and {endDate}",
                            "startDate" to SimpleDateFormat("yyyy-dd-MM").format(getFirstDayOfMonth(Date())),
                            "endDate" to SimpleDateFormat("yyyy-dd-MM").format(getLastDayOfQuarter(Date()).time)).exec {
                        parseList<Cash>(classParser())
                    }.map { it.cost }/*.reduce { sum, element -> sum + element }*/
        }

        return balance
    }
    fun getBalance(): Double {
        return database.use {
            select(Balance.TABLE_NAME, Balance.BALANCE).exec {
                parseList(object : MapRowParser<Double> {
                    override fun parseRow(columns: Map<String, Any?>): Double {
                        return columns.getValue(Balance.BALANCE) as Double
                    }
                })
            }
        }[0]
    }

    private fun getFirstDayOfMonth(date: Date): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH))
        cal.set(Calendar.DAY_OF_MONTH, 1)
        return cal.time
    }

    private fun getLastDayOfQuarter(date: Date): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH))
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH))
        return cal.time
    }
}