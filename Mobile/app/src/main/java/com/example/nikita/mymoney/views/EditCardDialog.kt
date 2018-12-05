package com.example.nikita.mymoney.views

import android.content.Context
import android.support.v7.app.AlertDialog
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import com.example.nikita.mymoney.database.manager.CardManager
import com.example.nikita.mymoney.database.model.Card
import com.example.nikita.mymoney.database.model.CardDTO
import com.example.nikita.mymoney.database.model.Category
import com.example.nikita.mymoney.service.PropertyService
import com.example.nikita.mymoney.Constants.Companion.BANK_CARD_NUMBER
import com.example.nikita.mymoney.Constants.Companion.BOTTOM_PADDING
import com.example.nikita.mymoney.Constants.Companion.CANCEL_BTN_LABEL
import com.example.nikita.mymoney.Constants.Companion.COST_NINT_TEXT
import com.example.nikita.mymoney.Constants.Companion.DEFAULT_SELECTED_ITEM_ID
import com.example.nikita.mymoney.Constants.Companion.EDIT_LABEL
import com.example.nikita.mymoney.Constants.Companion.LEFT_PADDING
import com.example.nikita.mymoney.Constants.Companion.NAME_NINT_TEXT
import com.example.nikita.mymoney.Constants.Companion.OK_BTN_LABEL
import com.example.nikita.mymoney.Constants.Companion.RIGHT_PADDING
import com.example.nikita.mymoney.Constants.Companion.TOP_PADDING
import com.example.nikita.mymoney.Constants.Companion.dropdownComponent
import java.util.*


class EditCardDialog {

    companion object {

        fun showEditDialog(ctn: Context, listItems: ArrayList<CardDTO>, manager: CardManager,
                           cardAdapter: ArrayAdapter<CardDTO>, selectedId: Int = DEFAULT_SELECTED_ITEM_ID) {
            val layout = LinearLayout(ctn)
            layout.orientation = LinearLayout.VERTICAL
            val builder = AlertDialog.Builder(ctn)
            val spinner = dropdownComponent(ctn, manager)
            layout.addView(spinner)
            val name = EditText(ctn)
            name.hint = NAME_NINT_TEXT
            layout.addView(name)

            val cost = EditText(ctn)

            if (selectedId != DEFAULT_SELECTED_ITEM_ID) {
                spinner.setSelection(manager.getAllCategories().indexOf(listItems[selectedId].category))
                cost.setText(listItems[selectedId].cost.toString())
                name.setText(listItems[selectedId].name)
            }
            cost.hint = COST_NINT_TEXT
            layout.addView(cost)
            layout.setPadding(LEFT_PADDING, TOP_PADDING, RIGHT_PADDING, BOTTOM_PADDING)
            builder.setView(layout)
            builder.setPositiveButton(OK_BTN_LABEL) { _, _ ->
                val cardDTO = CardDTO(id = listItems[selectedId].id, category = (spinner.selectedItem as Category), name = name.text.toString(),
                        cost = cost.text.toString().toDouble())
                listItems[selectedId] = cardDTO
                editListActivity(cardDTO, manager, cardAdapter)
            }

            builder.setNegativeButton(CANCEL_BTN_LABEL) { b, _ -> b.cancel() }

            builder.setTitle(EDIT_LABEL)
            builder.show()
        }

        private fun editListActivity(cardDTO: CardDTO, manager: CardManager, adapter: ArrayAdapter<CardDTO>) {
            manager.saveOrUpdate(Card(cardDTO))
            adapter.notifyDataSetChanged()
        }

        fun showEditCardDialog(ctx: Context) {
            val layout = LinearLayout(ctx)
            layout.orientation = LinearLayout.VERTICAL
            val bankNumber = EditText(ctx)
            val builder = AlertDialog.Builder(ctx)
            bankNumber.hint = BANK_CARD_NUMBER
            layout.addView(bankNumber)
            builder.setView(layout)
            if (PropertyService.getBankNumberFromProperty(ctx).isNotEmpty()) {
                bankNumber.setText(PropertyService.getBankNumberFromProperty(ctx))
            }

            builder.setPositiveButton(OK_BTN_LABEL) { _, _ ->
                PropertyService.setBankNumberToProperty(bankNumber.text.toString(), ctx)

            }

            builder.setNegativeButton(CANCEL_BTN_LABEL) { b, _ -> b.cancel() }
            builder.show()

        }
    }
}