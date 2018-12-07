package com.example.nikita.mymoney.database.model

import android.content.ContentValues

data class Category(override var id: Long? = null, val name: String) : IdModel() {
    override var tableName: String = TABLE_NAME


    override val dbModel: ContentValues
        get() {
            val values = ContentValues()
            values.put(NAME, name)
            values.put(ID, id)
            return values
        }

    override fun toString(): String {
        return name
    }

    companion object : Id() {
        const val TABLE_NAME: String = "Category"
        val NAME: String = "name"
    }

    constructor(categoryDTO: CategoryDTO) : this(id = categoryDTO.catId, name = categoryDTO.name)

}

data class CategoryDTO(var catId: Long? = null,
                       var name: String,
                       var cost: Double? = 0.0) {
    override fun toString(): String {
        return "$name ${cost ?: 0.0}"
    }

    val entity: Category
        get() {
            return Category(this.catId, this.name)
        }
}