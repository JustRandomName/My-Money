package com.example.nikita.mymoney.database.model

import android.content.ContentValues
import java.text.SimpleDateFormat
import java.util.*

class Card(override var id: Long? = null,
           val categoryId: Long,
           val name: String,
           val cost: Double,
           val _date: String = SimpleDateFormat("yyyy-MM-dd").format(Date())) : IdModel() {
    override var tableName: String = TABLE_NAME

    constructor(cardDTO: CardDTO) : this(id = cardDTO.id,
            name = cardDTO.name,
            categoryId = cardDTO.category.id!!,
            cost = cardDTO.cost)

    companion object : Money() {
        val TABLE_NAME: String = "Card"
    }

    override val dbModel: ContentValues
        get() {
            val values = ContentValues()
            values.put(CATEGORY_ID, categoryId)
            values.put(NAME, name)
            values.put(COST, cost)
            values.put(DATE, _date)
            return values
        }
}

data class CardDTO(var id: Long? = null, var category: Category, var name: String,
                   val cost: Double, val _date: String = SimpleDateFormat("yyyy-MM-dd").format(Date())) {
    override fun toString(): String {
        return "$name ${category.name} $cost $_date"
    }
}

data class CardCategoryJoinTable(val cardId: Long,
                                 val categoryId: Long,
                                 val cardName: String,
                                 val categoryName: String,
                                 val cost: Double,
                                 val _date: String)
