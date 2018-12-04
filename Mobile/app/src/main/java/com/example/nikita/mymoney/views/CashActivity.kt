package com.example.nikita.mymoney.views

import android.app.AlertDialog
import android.app.ListActivity
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import com.example.nikita.mymoney.R
import com.example.nikita.mymoney.database.manager.CashManager
import com.example.nikita.mymoney.database.model.Cash
import com.example.nikita.mymoney.database.model.CashDTO
import com.example.nikita.mymoney.views.AddingCashDialog.Companion.showAddingDialog
import kotlinx.android.synthetic.main.activity_cash.*
import kotlinx.android.synthetic.main.content_cash.*
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
            showAddingDialog(this, listItems, manager, adapter!!, position)
        }

        list.setOnItemLongClickListener { parent, view, position, id->
            val cashDTO = listItems[position]
            remove(cashDTO)
        }


        add_button.setOnClickListener { showAddingDialog(this, listItems, manager, adapter!!) }
    }

    private fun remove(cashDTO: CashDTO): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Ok") { _, _ ->
            listItems.remove(cashDTO)
            adapter!!.notifyDataSetChanged()
            manager.remove(Cash(cashDTO))
        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.setTitle("Remove?")
        builder.show()
        return true
    }
}
