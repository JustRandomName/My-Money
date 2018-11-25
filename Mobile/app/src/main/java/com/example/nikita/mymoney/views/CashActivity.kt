package com.example.nikita.mymoney.views

import android.app.ListActivity
import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.nikita.mymoney.R
import com.example.nikita.mymoney.database.manager.CashManager
import kotlinx.android.synthetic.main.activity_cash.*
import android.widget.ArrayAdapter
import com.example.nikita.mymoney.views.AddingNewDialog.Companion.showAddingDialog
import kotlinx.android.synthetic.main.content_cash.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.AdapterView.OnItemClickListener
import com.example.nikita.mymoney.views.AddingNewDialog.Companion.editCategory


class CashActivity : ListActivity() {

    lateinit var manager: CashManager
    var listItems = ArrayList<String>()
    var adapter: ArrayAdapter<String>? = null
    private var ctn: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash)
        manager = CashManager(applicationContext)
        adapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                listItems)
        list.adapter = adapter

        list.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            editCategory(this, listItems, id)
        }

        list.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View,
                                        position: Int, id: Long) {
                editCategory(ctn as CashActivity, listItems, id)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        add_button.setOnClickListener { showAddingDialog(this, listItems, manager, adapter!!) }
        ctn = this
    }
}
