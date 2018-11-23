package com.example.nikita.mymoney.smsParcer

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.nikita.mymoney.smsParcer.SmsReciver.Companion.SMS_BODY
import java.util.regex.Pattern


class SmsService : Service() {

    var smsDTO: SmsDTO? = null

    companion object {
        val OPLATA = "(?<=(OPLATA)) \\d.\\d{2}"
        val PLACE = "(?<=(BYN\\s))([\\w\\s-]*)?,"
        val OSTATOK = "(?<=(BYN\\s))([\\w\\s-]*)(?=,)"
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

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val smsBody = intent.extras!!.getString(SMS_BODY)
        parceSms(smsBody)
        return Service.START_STICKY
    }

    private fun parceSms(smsBody: String) {
        smsDTO!!.price = getOPLATA(smsBody)
        smsDTO!!.place = getPLACE(smsBody)
        smsDTO!!.remainder = getOSTATOK(smsBody)
        smsDTO!!.date = getDATE(smsBody)
    }


    private fun saveSmsData() {
        // TODO : save sms to database
    }
}