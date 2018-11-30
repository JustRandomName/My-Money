package com.example.nikita.mymoney.views

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.nikita.mymoney.R
import com.example.nikita.mymoney.database.manager.CategoryManager
import com.example.nikita.mymoney.database.model.Cash
import com.example.nikita.mymoney.database.model.CashDTO
import com.example.nikita.mymoney.database.model.Category
import com.example.nikita.mymoney.views.AddingCategoriesDialog.Companion.showAddingCategoryDialog
import kotlinx.android.synthetic.main.activity_categories.*

class CategoriesActivity : AppCompatActivity() {

    lateinit var manager: CategoryManager
    var listItems = ArrayList<Category>()
    var adapter: ArrayAdapter<Category>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        manager = CategoryManager(applicationContext)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        listItems.addAll(manager.getAllCategories().filter { it.id != -1L })

        categories_list.adapter = adapter
        categories_list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            showAddingCategoryDialog(this, manager, listItems, adapter!!, position)
        }

        categories_list.setOnItemLongClickListener { parent, view, position, id->
            var m = HashMap<String, Any>()
            m.put("categoryId", listItems[position].id!!)

            var cashList = manager.getByParametrs<Cash>(Cash.TABLE_NAME, m)
            val category = listItems[position]
            remove(category)
        }
        add.setOnClickListener { showAddingCategoryDialog(this, manager, listItems, adapter!!) }
    }

    private fun remove(category: Category): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Ok") { _, _ ->
            listItems.remove(category)
            adapter!!.notifyDataSetChanged()
            manager.remove(category)
        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.setTitle("Remove?")
        builder.show()
        return true
    }
}