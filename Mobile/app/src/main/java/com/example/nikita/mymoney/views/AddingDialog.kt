package com.example.nikita.mymoney.views

import android.app.AlertDialog
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import com.example.nikita.mymoney.database.manager.CashManager
import com.example.nikita.mymoney.database.model.Cash
import java.time.LocalDate

class AddingDialog {

    companion object {

        private const val OK_BTN_LABEL = "OK"
        private const val CANCEL_BTN_LABEL = "Cancel"
        private const val NAME_NINT_TEXT = "Name"
        private const val COST_NINT_TEXT = "Cost"
        private const val EDIT_TEXT_TITLE = "Edit"
        private const val ADDING_TEXT_TITLE = "Adding"
        private const val LEFT_PADDING = 45
        private const val RIGHT_PADDING = 45
        private const val TOP_PADDING = 5
        private const val BOTTOM_PADDING = 5

        /**
         * @param ctn - activity where call this alert
         * @param listItems - all categories from activity
         * @param id - id of selected category
         * */
        fun editCategory(ctn: Context, listItems: ArrayList<String>, id: Long) {
            val layout = LinearLayout(ctn)
            layout.orientation = LinearLayout.VERTICAL
            val builder = AlertDialog.Builder(ctn)
            val name = EditText(ctn)
            name.hint = NAME_NINT_TEXT
            name.setText(listItems[id.toInt()])
            layout.addView(name)
            val cost = EditText(ctn)
            cost.hint = COST_NINT_TEXT
            cost.setText(listItems[id.toInt()])
            layout.addView(cost)
            layout.setPadding(5, 5, 5, 5)
            builder.setView(layout)
            builder.setPositiveButton(OK_BTN_LABEL) { _, _ ->
                updateListView(id, listItems, name.text.toString(), getValidCost(cost.text.toString()))

                saveOrUpdateCategory(name.text.toString(), getValidCost(cost.text.toString()))
            }
            builder.setNegativeButton(CANCEL_BTN_LABEL) { builder, _ -> builder.cancel() }

            builder.setTitle(EDIT_TEXT_TITLE)
            builder.show()
        }

        /**
         * @param ctn - activity where call this alert
         * @param listItems - all categories from current window
         * @param manager - manager for saveOrUpdate
         * @param adapter - ???
         * */
        fun showAddingDialog(ctn: Context, listItems: ArrayList<String>, manager: CashManager, adapter: ArrayAdapter<String>) {
            val layout = LinearLayout(ctn)
            layout.orientation = LinearLayout.VERTICAL
            val builder = AlertDialog.Builder(ctn)
            val name = EditText(ctn)
            name.hint = NAME_NINT_TEXT
            layout.addView(name)
            val cost = EditText(ctn)
            cost.hint = COST_NINT_TEXT
            layout.addView(cost)
            layout.setPadding(LEFT_PADDING, TOP_PADDING, RIGHT_PADDING, BOTTOM_PADDING)
            builder.setView(layout)
            builder.setPositiveButton(OK_BTN_LABEL) { _, _ ->
                saveOrUpdateCategory(name.text.toString(), getValidCost(cost.text.toString()))
                editListActivity(name.text.toString(), getValidCost(cost.text.toString()), manager, listItems, adapter)

            }
            builder.setNegativeButton(CANCEL_BTN_LABEL) { builder, _ -> builder.cancel() }

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

        private fun updateListView(id: Long, listItems: ArrayList<String>, name: String, cost: Double) {
            // TODO : need to update database and adapter
            listItems[id.toInt()] = "will be update"
            saveOrUpdateCategory(name, cost)
        }

        private fun editListActivity(name: String, cost: Double, manager: CashManager,
                                     listItems: ArrayList<String>, adapter: ArrayAdapter<String>) {
            addNewCash(name.toInt(), cost, manager)
            addItems(cost, listItems, adapter)
        }

        private fun addItems(cost: Double, listItems: ArrayList<String>, adapter: ArrayAdapter<String>) {
            listItems.add("$cost : ${LocalDate.now()}")
            adapter.notifyDataSetChanged()
        }

        private fun addNewCash(categoryId: Int, cost: Double, manager: CashManager) {
//            manager.addNew(categoryId, cost)
        }


        private fun saveOrUpdateCategory(name: String, cost: Double) {

        }

    }
}