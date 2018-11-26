package com.example.nikita.mymoney.database.model

import android.content.ContentValues

abstract class Model {
    open val ID: String = "id"

    abstract var tableName: String

    abstract val dbModel: ContentValues
    /*abstract fun getDbModel():ContentValues*/

    /*abstract fun getModel(): ContentValues*/
}