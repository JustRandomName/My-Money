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

class AddingDialog {

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
         * @param manager - manager for saveOrUpdate
         * @param adapter - ???
         * */
        fun showAddingDialog(ctn: Context, listItems: ArrayList<CashDTO>, manager: CashManager,
                             adapter: ArrayAdapter<CashDTO>, selectedId: Int = -1) {
            val layout = LinearLayout(ctn)
            layout.orientation = LinearLayout.VERTICAL
            val builder = AlertDialog.Builder(ctn)
            var spinner = dropdownComponent(ctn, manager)
            layout.addView(dropdownComponent(ctn, manager))
            val name = EditText(ctn)
            name.hint = NAME_NINT_TEXT
            layout.addView(name)

            val cost = EditText(ctn)

            if (selectedId != -1) {
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
                editListActivity(cashDTO, manager, listItems, adapter)
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
            listItems.add(CashDTO(id = cashDTO.id, cost = cashDTO.cost, name = cashDTO.name, category = cashDTO.category))

        }

        private fun addNewCash(cashDTO: CashDTO, manager: CashManager): Boolean {
            cashDTO.id = manager.saveOrUpdate(Cash(id = cashDTO.id, name = cashDTO.name, categoryId = cashDTO.category.id, cost = cashDTO.cost))
            return true
        }
    }
}