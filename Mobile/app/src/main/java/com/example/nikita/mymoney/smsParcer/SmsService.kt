package com.example.nikita.mymoney.smsParcer

import com.example.nikita.mymoney.MainActivity
import com.example.nikita.mymoney.database.manager.CardManager
import com.example.nikita.mymoney.database.model.Card
import java.util.regex.Pattern


class SmsService {

    var smsDTO = SmsDTO()

    companion object {
        val OPLATA = "(?<=(OPLATA)) \\d.\\d{2}"
        val PLACE = "(?<=(BYN\\s))([\\w\\s-]*)(?=,)"
        val OSTATOK = "((?<=(OSTATOK\\s))(\\d.\\d{2}))"
        val DATA = "(\\d{2}\\/\\d{2}\\/\\d{2})"

        fun getOSTATOK(smsBody: String): String {
            val pattern = Pattern.compile(OSTATOK)
            val matcher = pattern.matcher(smsBody)
            var result = ""
            while (matcher.find()) {
                result += matcher.group()
            }
            return result
        }

        fun getPLACE(smsBody: String): String {
            val pattern = Pattern.compile(PLACE)
            val matcher = pattern.matcher(smsBody)
            var result = ""
            while (matcher.find()) {
                result += matcher.group()
            }
            return result


        }

        fun getOPLATA(smsBody: String): String {
            val pattern = Pattern.compile(OPLATA)
            val matcher = pattern.matcher(smsBody)
            var result = ""
            while (matcher.find()) {
                result += matcher.group()
            }
            return result


        }

        fun getDATE(smsBody: String): String {
            val pattern = Pattern.compile(DATA)
            val matcher = pattern.matcher(smsBody)
            var result = ""
            while (matcher.find()) {
                result += matcher.group()
            }
            return result
        }


    }

    fun parceSms(smsBody: String) {
        smsDTO.price = getOPLATA(smsBody)
        smsDTO.place = getPLACE(smsBody)
        smsDTO.remainder = getOSTATOK(smsBody)
        smsDTO.date = getDATE(smsBody)
        saveSmsData()
    }


    private fun saveSmsData() {
        val cardManager = CardManager(MainActivity.aplContext)
        var a = cardManager.saveOrUpdate(Card(categoryId = -1, name = smsDTO.place, _date = "", cost = smsDTO.price.toDouble()))
        println(a)
        // TODO : save sms to database from smsDTO
    }
}