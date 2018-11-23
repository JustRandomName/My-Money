package com.example.nikita.mymoney.smsParcer

import org.junit.Assert.*
import org.junit.Test

class SmsServiceTest {

    companion object {
        val smsBody = "KARTA:5351*6613\n" +
                "22/11/18 15:05\n" +
                "OPLATA 2.64 BYN\n" +
                "TO 5-GO UCHEBNOGO KORP, , MINSK\n" +
                "OSTATOK 4.64 BYN\n" +
                "Spr.:5099999"
    }

    @Test
    fun parseDATE_test() {
        assertEquals("22/11/18" ,SmsService.getDATE(smsBody))
    }

    @Test
    fun parseOPLATA_test() {
        assertEquals(" 2.64" ,SmsService.getOPLATA(smsBody))
    }

    @Test
    fun parsePLACE_test() {
        assertEquals("TO 5-GO UCHEBNOGO KORP" ,SmsService.getPLACE(smsBody))
    }

    @Test
    fun parseOSTATOC_test() {
        assertEquals("4.64" ,SmsService.getOSTATOK(smsBody))
    }

}