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
import com.example.nikita.mymoney.database.model.Card
import com.example.nikita.mymoney.database.model.Cash
import com.example.nikita.mymoney.database.model.Category
import com.example.nikita.mymoney.views.AddingCategoriesDialog.Companion.showAddingCategoryDialog
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
        listItems.addAll(categoryManager.getAllCategories().filter { it.id != -1L })

        categories_list.adapter = adapter
        categories_list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            showAddingCategoryDialog(this, categoryManager, listItems, adapter!!, position)
        }

        categories_list.setOnItemLongClickListener { parent, view, position, id->
            var cashList = cashManager.getCashByCategoryId((listItems[position].id!!)).filter { it.category.id ==  listItems[position].id!! }
            cashList.map {
                it.category = Category(-1L, "Not Selected")
            }
            cashList.map { cashManager.saveOrUpdate(Cash(it)) }


            var cardList = cardManager.getCardByCategoryId((listItems[position].id!!)).filter { it.category.id ==  listItems[position].id!! }
            cardList.map {
                it.category = Category(-1L, "Not Selected")
            }
            cardList.map { cardManager.saveOrUpdate(Card(it)) }


            val category = listItems[position]
            remove(category)
        }

        add.setOnClickListener { showAddingCategoryDialog(this, categoryManager, listItems, adapter!!) }
    }

    private fun remove(category: Category): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Ok") { _, _ ->
            listItems.remove(category)
            adapter!!.notifyDataSetChanged()
            categoryManager.remove(category)
        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.setTitle("Remove?")
        builder.show()
        return true
    }
}