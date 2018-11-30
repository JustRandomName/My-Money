package com.example.nikita.mymoney.views

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.example.nikita.mymoney.R
import com.example.nikita.mymoney.database.manager.CardManager
import com.example.nikita.mymoney.database.model.Card
import com.example.nikita.mymoney.database.model.CardDTO
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.android.synthetic.main.content_card.*
import java.util.*

class CardActivity : AppCompatActivity() {

    lateinit var manager: CardManager
    var listItems = ArrayList<CardDTO>()
    var adapter: ArrayAdapter<CardDTO>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)
        setSupportActionBar(toolbar)
        manager = CardManager(applicationContext)
        listItems.addAll(manager.getCards())
        adapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                listItems)
        categories_card_list.adapter = adapter

        categories_card_list.setOnItemLongClickListener { parent, view, position, id ->
            val cashDTO = listItems[position]
            listItems.remove(cashDTO)
            adapter!!.notifyDataSetChanged()
            remove(cashDTO)
        }
    }


        private fun remove(cardDTO: CardDTO): Boolean {
            val builder = AlertDialog.Builder(this)
            builder.setPositiveButton("Ok") { _, _ ->
                listItems.remove(cardDTO)
                adapter!!.notifyDataSetChanged()
                manager.remove(Card(cardDTO))
            }
            builder.setNegativeButton("No") { _, _ ->

            }
            builder.setTitle("Remove?")
            builder.show()
            return true
        }
}
