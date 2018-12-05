package com.example.nikita.mymoney.service

import android.content.Context
import android.R.attr.data
import java.io.*


class PropertyService {

    companion object {

        fun getBankNumberFromProperty(context: Context): String {
            var ret = ""

            try {
                val inputStream = context.openFileInput("config.txt")

                if (inputStream != null) {
                    val inputStreamReader = InputStreamReader(inputStream)
                    val bufferedReader = BufferedReader(inputStreamReader)
                    val stringBuilder = StringBuilder()

                    stringBuilder.append(bufferedReader.readLine())
                    inputStream.close()
                    ret = stringBuilder.toString()
                }
            } catch (e: FileNotFoundException) {
            } catch (e: IOException) {
            }


            return ret
        }

        fun setBankNumberToProperty(bankNumber: String, context: Context) {
            try {
                val outputStreamWriter = OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE))
                outputStreamWriter.write(bankNumber)
                outputStreamWriter.close()
            } catch (e: IOException) {
            }
        }
    }

}