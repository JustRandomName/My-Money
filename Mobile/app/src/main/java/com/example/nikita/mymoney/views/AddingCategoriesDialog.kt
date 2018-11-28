package com.example.nikita.mymoney.views

import android.R
import android.app.AlertDialog
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import com.example.nikita.mymoney.database.manager.CategoryManager
import com.example.nikita.mymoney.database.model.Category

class AddingCategoriesDialog {
    companion object {

        private const val OK_BTN_LABEL = "OK"
        private const val CANCEL_BTN_LABEL = "Cancel"
        private const val NAME_NINT_TEXT = "Name"
        private const val ADDING_TEXT_TITLE = "Adding"
        private const val LEFT_PADDING = 45
        private const val RIGHT_PADDING = 45
        private const val TOP_PADDING = 5
        private const val BOTTOM_PADDING = 5

        /**
         * @param ctn - activity where call this alert
         * @param listItems - all categories from current window
         * @param manager - manager for saveOrUpdate
         * @param cashAdapter - ???
         * */
        fun showAddingCategoryDialog(ctn: Context, manager: CategoryManager,
                             cashAdapter: ArrayAdapter<Category>) {
            val layout = LinearLayout(ctn)
            layout.orientation = LinearLayout.VERTICAL
            val builder = AlertDialog.Builder(ctn)
            val name = EditText(ctn)
            name.hint = NAME_NINT_TEXT
            layout.addView(name)
            layout.setPadding(LEFT_PADDING, TOP_PADDING, RIGHT_PADDING, BOTTOM_PADDING)
            builder.setView(layout)
            builder.setPositiveButton(OK_BTN_LABEL) { _, _ ->

            }

            builder.setNegativeButton(CANCEL_BTN_LABEL) { b, _ -> b.cancel() }

            builder.setTitle(ADDING_TEXT_TITLE)
            builder.show()
        }

        private fun editListActivity(Category: Category, manager: CategoryManager,
                                     listItems: ArrayList<Category>, adapter: ArrayAdapter<Category>) {

            if (addNewCash(Category, manager)) {
                addItems(Category, listItems)
            }
            adapter.notifyDataSetChanged()
        }

        private fun addItems(Category: Category, listItems: ArrayList<Category>) {
            listItems.add(Category)
        }

        private fun addNewCash(Category: Category, manager: CategoryManager): Boolean {
            //manager.saveOrUpdate(Cash(Category))
            return Category.id == null
        }
    }
}
