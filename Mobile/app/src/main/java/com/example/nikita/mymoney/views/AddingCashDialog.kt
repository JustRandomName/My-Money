package com.example.nikita.mymoney.views

import android.app.AlertDialog
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import com.example.nikita.mymoney.database.manager.CashManager
import com.example.nikita.mymoney.database.model.Cash
import com.example.nikita.mymoney.database.model.CashDTO
import com.example.nikita.mymoney.Constants.Companion.ADDING_TEXT_TITLE
import com.example.nikita.mymoney.Constants.Companion.BOTTOM_PADDING
import com.example.nikita.mymoney.Constants.Companion.CANCEL_BTN_LABEL
import com.example.nikita.mymoney.Constants.Companion.COST_NINT_TEXT
import com.example.nikita.mymoney.Constants.Companion.DEFAULT_SELECTED_ITEM_ID
import com.example.nikita.mymoney.Constants.Companion.LEFT_PADDING
import com.example.nikita.mymoney.Constants.Companion.NAME_NINT_TEXT
import com.example.nikita.mymoney.Constants.Companion.OK_BTN_LABEL
import com.example.nikita.mymoney.Constants.Companion.RIGHT_PADDING
import com.example.nikita.mymoney.Constants.Companion.TOP_PADDING
import com.example.nikita.mymoney.Constants.Companion.dropdownComponent
import com.example.nikita.mymoney.database.model.Category
import com.example.nikita.mymoney.database.model.CategoryDTO

class AddingCashDialog {

    companion object {
        /**
         * @param ctn - activity where call this alert
         * @param listItems - all categories from current window
         * @param manager - categoryManager for saveOrUpdate
         * @param cashAdapter - ???
         * */
        fun showAddingDialog(ctn: Context, listItems: ArrayList<CashDTO>, manager: CashManager,
                             cashAdapter: ArrayAdapter<CashDTO>, selectedId: Int = DEFAULT_SELECTED_ITEM_ID) {
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
                spinner.setSelection(manager.getAllCategories().map {CategoryDTO(it.id, it.name)}.indexOf(listItems[selectedId].category))
                cost.setText(listItems[selectedId].cost.toString())
                name.setText(listItems[selectedId].name)
            }
            cost.hint = COST_NINT_TEXT
            layout.addView(cost)
            layout.setPadding(LEFT_PADDING, TOP_PADDING, RIGHT_PADDING, BOTTOM_PADDING)
            builder.setView(layout)
            builder.setPositiveButton(OK_BTN_LABEL) { _, _ ->
                val cashDTO: CashDTO
                var cat = spinner.selectedItem as Category
                if (selectedId != DEFAULT_SELECTED_ITEM_ID) {
                    cashDTO = CashDTO(id = listItems[selectedId].id, category = CategoryDTO(cat.id, cat.name), name = name.text.toString(),
                            cost = getValidCost(cost.text.toString()))
                    listItems[selectedId] = cashDTO

                } else {
                    cashDTO = CashDTO(category = CategoryDTO(cat.id, cat.name), name = name.text.toString(),
                            cost = getValidCost(cost.text.toString()))
                }
                editListActivity(cashDTO, manager, listItems, cashAdapter)
            }

            builder.setNegativeButton(CANCEL_BTN_LABEL) { b, _ -> b.cancel() }

            builder.setTitle(ADDING_TEXT_TITLE)
            builder.show()
        }

        private fun getValidCost(cost: String): Double {
            var realCost = 0.0
            try {
                realCost = cost.toDouble()
            } catch (e: NumberFormatException) {
                // TODO : say "Bad cost"
            }
            return realCost
        }

        private fun editListActivity(cashDTO: CashDTO, manager: CashManager,
                                     listItems: ArrayList<CashDTO>, adapter: ArrayAdapter<CashDTO>) {

            if (addNewCash(cashDTO, manager)) {
                addItems(cashDTO, listItems)
            }
            adapter.notifyDataSetChanged()
        }

        private fun addItems(cashDTO: CashDTO, listItems: ArrayList<CashDTO>) {
            listItems.add(cashDTO)
        }

        private fun addNewCash(cashDTO: CashDTO, manager: CashManager): Boolean {
            val cash = Cash(cashDTO)
            manager.saveOrUpdate(cash)
            return cash.id == null
        }
    }
}