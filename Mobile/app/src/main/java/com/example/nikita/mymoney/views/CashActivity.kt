package com.example.nikita.mymoney.views

import android.app.ListActivity
import android.os.Bundle
import android.view.View
import com.example.nikita.mymoney.R
import com.example.nikita.mymoney.database.manager.CashManager
import kotlinx.android.synthetic.main.activity_cash.*
import android.widget.ArrayAdapter
import com.example.nikita.mymoney.views.AddingCashDialog.Companion.showAddingDialog
import kotlinx.android.synthetic.main.content_cash.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.AdapterView.OnItemClickListener
import com.example.nikita.mymoney.database.model.Cash
import com.example.nikita.mymoney.database.model.CashDTO
import com.example.nikita.mymoney.database.model.Category
import java.util.*


class CashActivity : ListActivity() {

    lateinit var manager: CashManager
    var listItems = ArrayList<CashDTO>()
    var adapter: ArrayAdapter<CashDTO>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash)
        manager = CashManager(applicationContext)
        listItems.addAll(manager.getCash())
        adapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                listItems)
        list.adapter = adapter

        list.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            showAddingDialog(this, listItems, manager, adapter!!, id.toInt())
        }

        list.setOnItemLongClickListener { parent, view, position, id->
            val category = listItems[position]
            listItems.remove(category)
            adapter!!.notifyDataSetChanged()
            remove(category)
        }


        add_button.setOnClickListener { showAddingDialog(this, listItems, manager, adapter!!) }
    }

    private fun remove(cashDTO: CashDTO): Boolean {
        manager.remove(Cash(cashDTO))
        return true
    }

}
