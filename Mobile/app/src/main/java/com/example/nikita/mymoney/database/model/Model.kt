package com.example.nikita.mymoney.database.model

import android.content.ContentValues

abstract class Model() {
    open val ID: String = "id"
    abstract var tableName: String
    abstract val dbModel: ContentValues
}

abstract class IdModel : Model() {
    abstract var id: Long?
}