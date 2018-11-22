package com.example.nikita.mymoney.views

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import com.example.nikita.mymoney.R
import com.example.nikita.mymoney.database.manager.CashCategoryManager
import kotlinx.android.synthetic.main.activity_cash.*


class CashActivity : AppCompatActivity() {

    lateinit var manager: CashCategoryManager
    private var categoryName: String = ""
    private var categoryCost: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash)
        manager = CashCategoryManager(applicationContext)
        setSupportActionBar(toolbar)
        add_button.setOnClickListener { showAddingCategoryName() }
    }

    private fun showAddingCategoryName() {
        val alert = AlertDialog.Builder(this)

        alert.setTitle("Adding new category")

        val name = EditText(this)
        alert.setView(name)

        alert.setPositiveButton("Ok") { _, _ ->
            // TODO : validate category name
            categoryName = name.text.toString()
            showAddingCategoryCost()
        }

        alert.setNegativeButton("Cancel") { _, _ ->
        }

        alert.show()
    }

    private fun showAddingCategoryCost() {
        val alert = AlertDialog.Builder(this)

        alert.setTitle("Adding new category cost")
        alert.setMessage(categoryName)

        val cost = EditText(this)
        alert.setView(cost)

        alert.setPositiveButton("Ok") { _, _ ->
            // TODO : validate cost value
            categoryCost = cost.text.toString()
            addNewCategory(categoryName, categoryCost)
        }

        alert.setNegativeButton("Cancel") { _, _ ->
        }

        alert.show()
    }

    private fun addNewCategory(categoryName:String, categoryCost:String) {
        manager.addNewCategory(categoryName, categoryCost)
    }

}
