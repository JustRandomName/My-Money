package com.example.nikita.mymoney.smsParcer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import org.jetbrains.anko.doAsync


class SmsReceiver : BroadcastReceiver() {
    companion object {
        val ACTION = "android.provider.Telephony.SMS_RECEIVED"
        val BANKMANE = "+375298479550"
        val SMS_BODY = "sms_body"
        val PDUS = "pdus"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != null &&
                ACTION.compareTo(intent.action, false) == 0) {
            val pduArray = intent.extras.get(PDUS) as Array<Any>
            val messages = arrayOfNulls<SmsMessage>(pduArray.size)
            for (i in pduArray.indices) {
                messages[i] = SmsMessage.createFromPdu(pduArray[i] as ByteArray)
            }
            val sms_from = messages[0]!!.getDisplayOriginatingAddress()
            if (sms_from.equals(BANKMANE, ignoreCase = true)) {
                val bodyText = StringBuilder()
                for (i in 0 until messages.size) {
                    bodyText.append(messages[i]!!.messageBody)
                }
                val body = bodyText.toString()
                parseSMS(body)
//                val mIntent = Intent(context, SmsService::class.java)
//                mIntent.putExtra(SMS_BODY, body)
//                context.startService(mIntent)
//


                abortBroadcast()
            }

        }
    }


    private fun parseSMS(smsBody : String) {
        doAsync {
            val smsServ = SmsService()
            smsServ.parceSms(smsBody)
        }
        abortBroadcast()
    }
}