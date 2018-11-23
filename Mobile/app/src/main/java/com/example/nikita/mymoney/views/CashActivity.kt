package com.example.nikita.mymoney.views

import android.app.AlertDialog
import android.app.ListActivity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.example.nikita.mymoney.R
import com.example.nikita.mymoney.database.manager.CashManager
import kotlinx.android.synthetic.main.activity_cash.*
import java.math.BigDecimal
import com.example.nikita.mymoney.database.model.Category
import kotlinx.android.synthetic.main.content_cash.*
import android.widget.ArrayAdapter
import java.time.LocalDate
import java.time.LocalDateTime


class CashActivity : ListActivity() {

    lateinit var manager: CashManager
    private var categoryId: Int = 0
    private var categoryCost: Double = 0.0
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    var listItems = ArrayList<String>()

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    var adapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash)
        manager = CashManager(applicationContext)
        adapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                listItems)
        listAdapter = adapter
        /*setSupportActionBar(toolbar)*/
        add_button.setOnClickListener { showAddingCategoryName() }
    }

    fun addItems(cost:Double) {
        listItems.add("$cost : ${LocalDate.now()}")
        adapter!!.notifyDataSetChanged()
    }

    private fun addNewCash(categoryId: Int, cost: Double) {
        manager.addNew(categoryId, categoryCost)
    }

    private fun showAddingCategoryName() {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val dropdown = Spinner(this)
        val items = arrayOf(Category(1, "one"), Category(1, "two"))
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        dropdown.adapter = adapter
        layout.addView(dropdown)
        val name = EditText(this)
        layout.addView(name)
        val alert = AlertDialog.Builder(this)
        alert.setView(layout)
        alert.setTitle("Adding new category")
        alert.setPositiveButton("Ok") { _, _ ->
            // TODO : validate category name
            categoryId = (dropdown.selectedItem as Category).id
            categoryCost = name.text.toString().toDouble()
            addNewCash(categoryId, categoryCost)
            addItems(categoryCost)
        }

        alert.setNegativeButton("Cancel") { _, _ ->
        }

        alert.show()
    }

}
