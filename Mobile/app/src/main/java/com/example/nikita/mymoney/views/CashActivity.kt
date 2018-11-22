package com.example.nikita.mymoney.views

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import com.example.nikita.mymoney.R
import kotlinx.android.synthetic.main.activity_cash.*


class CashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash)
        setSupportActionBar(toolbar)
        add_button.setOnClickListener { showAddingDialog() }
    }

    private fun showAddingDialog() {
        val alert = AlertDialog.Builder(this)

        alert.setTitle("Title")
        alert.setMessage("Message")

// Set an EditText view to get user input
        val input = EditText(this)
        alert.setView(input)

        alert.setPositiveButton("Ok") { dialog, whichButton ->
            addNewCategory(input.text.toString())

        }

        alert.setNegativeButton("Cancel", { dialog, whichButton ->
        })

        alert.show()
    }

    private fun addNewCategory(text: String) {
        // TODO : save in database
    }

}
