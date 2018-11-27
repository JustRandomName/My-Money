package com.example.nikita.mymoney.views

import android.app.ListActivity
import android.os.Bundle
import android.view.View
import com.example.nikita.mymoney.R
import com.example.nikita.mymoney.database.manager.CashManager
import kotlinx.android.synthetic.main.activity_cash.*
import android.widget.ArrayAdapter
import com.example.nikita.mymoney.views.AddingDialog.Companion.showAddingDialog
import kotlinx.android.synthetic.main.content_cash.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.AdapterView.OnItemClickListener
import com.example.nikita.mymoney.database.model.CashDTO
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*
import java.util.stream.Collectors.toList
import android.widget.Toast




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
        
        list.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View,
                                        position: Int, id: Long) {
                showAddingDialog(applicationContext, listItems, manager, adapter!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        settings.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        itemSelected: View, selectedItemPosition: Int, selectedId: Long) {

                val choose = settings.selectedItem as String
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        add_button.setOnClickListener { showAddingDialog(this, listItems, manager, adapter!!) }
    }
}
