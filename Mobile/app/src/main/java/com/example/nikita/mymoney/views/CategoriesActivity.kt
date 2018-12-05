package com.example.nikita.mymoney.views

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.nikita.mymoney.R
import com.example.nikita.mymoney.database.manager.CardManager
import com.example.nikita.mymoney.database.manager.CashManager
import com.example.nikita.mymoney.database.manager.CategoryManager
import com.example.nikita.mymoney.database.model.*
import com.example.nikita.mymoney.views.AddingCategoriesDialog.Companion.showAddingCategoryDialog
import com.example.nikita.mymoney.Constants.Companion.CANCEL_BTN_LABEL
import com.example.nikita.mymoney.Constants.Companion.DEFAULT_SELECTED_ITEM_ID
import com.example.nikita.mymoney.Constants.Companion.NOT_SELECTED
import com.example.nikita.mymoney.Constants.Companion.OK_BTN_LABEL
import com.example.nikita.mymoney.Constants.Companion.REMOVE_LABEL
import kotlinx.android.synthetic.main.activity_categories.*

class CategoriesActivity : AppCompatActivity() {

    lateinit var categoryManager: CategoryManager
    lateinit var cardManager: CardManager
    lateinit var cashManager: CashManager
    var listItems = ArrayList<Category>()
    var adapter: ArrayAdapter<Category>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        categoryManager = CategoryManager(applicationContext)
        cashManager = CashManager(applicationContext)
        cardManager = CardManager(applicationContext)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        listItems.addAll(categoryManager.getAllCategories().filter { it.id != DEFAULT_SELECTED_ITEM_ID.toLong() })

        categories_list.adapter = adapter
        categories_list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            showAddingCategoryDialog(this, categoryManager, listItems, adapter!!, position)
        }

        categories_list.setOnItemLongClickListener { parent, view, position, id->
            val category = listItems[position]
            remove(category)
        }

        add.setOnClickListener { showAddingCategoryDialog(this, categoryManager, listItems, adapter!!) }
    }

    private fun remove(category: Category): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton(OK_BTN_LABEL) { _, _ ->
            setDefaultCategory(category)
            listItems.remove(category)
            adapter!!.notifyDataSetChanged()
            categoryManager.remove(category)
        }
        builder.setNegativeButton(CANCEL_BTN_LABEL) { _, _ ->

        }
        builder.setTitle(REMOVE_LABEL)
        builder.show()
        return true
    }

    private fun setDefaultCategory(category: Category) {
        val cashList = getCashByCategory(category)
        cashList.map {
            it.category = Category(DEFAULT_SELECTED_ITEM_ID.toLong(), NOT_SELECTED)
        }
        cashList.map { cashManager.saveOrUpdate(Cash(it)) }

        val cardList = getCardByCategory(category)
        cardList.map {
            it.category = Category(DEFAULT_SELECTED_ITEM_ID.toLong(), NOT_SELECTED)
        }
        cardList.map { cardManager.saveOrUpdate(Card(it)) }
    }

    private fun getCashByCategory(category: Category): List<CashDTO> {
        return cashManager.getCashByCategoryId((category.id!!)).filter { it.category.id ==  category.id }
    }

    private fun getCardByCategory(category: Category): List<CardDTO> {
        return cardManager.getCardByCategoryId((category.id!!)).filter { it.category.id ==  category.id }
    }
}