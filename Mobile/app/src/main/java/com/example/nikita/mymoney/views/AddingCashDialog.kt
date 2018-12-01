package com.example.nikita.mymoney.views

import android.app.AlertDialog
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import com.example.nikita.mymoney.database.manager.CashManager
import com.example.nikita.mymoney.database.model.Cash
import com.example.nikita.mymoney.database.model.CashDTO
import com.example.nikita.mymoney.database.model.Category

class AddingCashDialog {

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

        private fun dropdownComponent(ctx: Context, manager: CashManager): Spinner {
            val dropdown = Spinner(ctx)
            val items = manager.getAllCategories()
            dropdown.adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, items)
            return dropdown
        }

        /**
         * @param ctn - activity where call this alert
         * @param listItems - all categories from current window
         * @param manager - categoryManager for saveOrUpdate
         * @param cashAdapter - ???
         * */
        fun showAddingDialog(ctn: Context, listItems: ArrayList<CashDTO>, manager: CashManager,
                             cashAdapter: ArrayAdapter<CashDTO>, selectedId: Int = -1) {
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
                val cashDTO: CashDTO
                if (selectedId != -1) {
                    cashDTO = CashDTO(id = listItems[selectedId].id, category = spinner.selectedItem as Category, name = name.text.toString(),
                            cost = getValidCost(cost.text.toString()))
                    listItems[selectedId] = cashDTO

                } else {
                    cashDTO = CashDTO(category = (spinner.selectedItem as Category), name = name.text.toString(),
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