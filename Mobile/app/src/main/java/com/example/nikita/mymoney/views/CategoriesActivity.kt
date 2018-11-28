package com.example.nikita.mymoney.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.nikita.mymoney.R
import com.example.nikita.mymoney.database.manager.CategoryManager
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
        adapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                listItems)

        listItems.addAll(manager.getAllCategories())
        categories_list.adapter = adapter
        categories_list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

        }

        categories_list.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View,
                                        position: Int, id: Long) {
                //showAddingCategoryDialog()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }


//        add_button.setOnClickListener {  }
    }
}
