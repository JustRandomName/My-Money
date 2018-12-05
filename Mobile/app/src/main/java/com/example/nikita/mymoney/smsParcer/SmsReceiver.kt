package com.example.nikita.mymoney.smsParcer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import com.example.nikita.mymoney.service.PropertyService.Companion.getBankNumberFromProperty
import com.example.nikita.mymoney.service.SmsService
import com.example.nikita.mymoney.Constants.Companion.ACTION
import com.example.nikita.mymoney.Constants.Companion.PDUS


class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != null &&
                ACTION.compareTo(intent.action, false) == 0) {
            val pduArray = intent.extras.get(PDUS) as Array<Any>
            val messages = arrayOfNulls<SmsMessage>(pduArray.size)
            for (i in pduArray.indices) {
                messages[i] = SmsMessage.createFromPdu(pduArray[i] as ByteArray)
            }
            val sms_from = messages[0]!!.getDisplayOriginatingAddress()
            if (sms_from.equals(getBankNumberFromProperty(context), ignoreCase = true)) {
                val bodyText = StringBuilder()
                for (i in 0 until messages.size) {
                    bodyText.append(messages[i]!!.messageBody)
                }
                val body = bodyText.toString()
                parseSMS(String(bodyText))
                abortBroadcast()
            }

        }
    }


    private fun parseSMS(smsBody : String) {
        Thread {
            val smsServ = SmsService()
            smsServ.parceSms(smsBody)
        }.start()
    }
}