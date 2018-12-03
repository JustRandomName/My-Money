package com.example.nikita.mymoney.database.manager

import android.content.Context
import com.example.nikita.mymoney.database.model.Card
import com.example.nikita.mymoney.database.model.CardCategoryJoinTable
import com.example.nikita.mymoney.database.model.CardDTO
import com.example.nikita.mymoney.database.model.Category
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select

class CardManager(_ctx: Context) : SimpleManager(_ctx) {

    fun getCards(): List<CardDTO> {
        return database.use {
            select(Card.TABLE_NAME + " left join " + Category.TABLE_NAME,
                    "Card.id as cardhId, Category.id as categoryId, Card.name as cardName, Category.name as categoryName, cost, _date")
                    .whereArgs("Card.categoryId = Category.id")
                    .exec {
                        parseList<CardCategoryJoinTable>(classParser())
                    }
        }.map {
            CardDTO(id = it.cardId,
                    name = it.cardName,
                    category = Category(it.categoryId, it.categoryName),
                    cost = it.cost,
                    _date = it._date)
        }
    }

    fun getCardByCategoryId(categoryId: Long): List<CardDTO> {
        return database.use {
            select(Card.TABLE_NAME + " left join " + Category.TABLE_NAME,
                    "Card.id as cardId, Category.id as categoryId, Card.name as cardName, Category.name as categoryName, cost, _date")
                    .whereArgs("Card.categoryId = Category.id AND Category.Id = {categoryId}", "categoryId" to categoryId)
                    .exec {
                        parseList<CardCategoryJoinTable>(classParser())
                    }
        }.map {
            CardDTO(id = it.cardId,
                    name = it.cardName,
                    category = Category(it.categoryId, it.categoryName),
                    cost = it.cost,
                    _date = it._date)
        }
    }
}