package com.example.nikita.mymoney.views

import android.content.Context
import android.support.v7.app.AlertDialog
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import com.example.nikita.mymoney.database.manager.CardManager
import com.example.nikita.mymoney.database.model.Card
import com.example.nikita.mymoney.database.model.CardDTO
import com.example.nikita.mymoney.database.model.Category

class EditCard {

    companion object {

        private const val OK_BTN_LABEL = "OK"
        private const val CANCEL_BTN_LABEL = "Cancel"
        private const val NAME_NINT_TEXT = "Name"
        private const val COST_NINT_TEXT = "Cost"
        private const val ADDING_TEXT_TITLE = "Adding"
        private const val LEFT_PADDING = 45
        private const val RIGHT_PADDING = 45
        private const val TOP_PADDING = 5
        private const val BOTTOM_PADDING = 5

        private fun dropdownComponent(ctx: Context, manager: CardManager): Spinner {
            val dropdown = Spinner(ctx)
            val items = manager.getAllCategories()
            dropdown.adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, items)
            return dropdown
        }


        fun showEditDialog(ctn: Context, listItems: ArrayList<CardDTO>, manager: CardManager,
                           cardAdapter: ArrayAdapter<CardDTO>, selectedId: Int = -1) {
            val layout = LinearLayout(ctn)
            layout.orientation = LinearLayout.VERTICAL
            val builder = AlertDialog.Builder(ctn)
            val spinner = dropdownComponent(ctn, manager)
            layout.addView(spinner)
            val name = EditText(ctn)
            name.hint = NAME_NINT_TEXT
            layout.addView(name)

            val cost = EditText(ctn)

            if (selectedId != -1) {
                spinner.setSelection(manager.getAllCategories().indexOf(listItems[selectedId].category))
                cost.setText(listItems[selectedId].cost.toString())
                name.setText(listItems[selectedId].name)
            }
            cost.hint = COST_NINT_TEXT
            layout.addView(cost)
            layout.setPadding(LEFT_PADDING, TOP_PADDING, RIGHT_PADDING, BOTTOM_PADDING)
            builder.setView(layout)
            builder.setPositiveButton(OK_BTN_LABEL) { _, _ ->
                val cardDTO = CardDTO(id = listItems[selectedId].id ,category = (spinner.selectedItem as Category), name = name.text.toString(),
                        cost = cost.text.toString().toDouble())
                listItems[selectedId] = cardDTO
                editListActivity(cardDTO, manager, listItems, cardAdapter)
            }

            builder.setNegativeButton(CANCEL_BTN_LABEL) { b, _ -> b.cancel() }

            builder.setTitle("Edit")
            builder.show()
        }

        private fun editListActivity(cardDTO: CardDTO, manager: CardManager, listItems: ArrayList<CardDTO>, adapter: ArrayAdapter<CardDTO>) {
            manager.saveOrUpdate(Card(cardDTO))
            adapter.notifyDataSetChanged()
        }

    }
}