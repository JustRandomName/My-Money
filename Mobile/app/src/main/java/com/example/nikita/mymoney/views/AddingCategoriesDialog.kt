package com.example.nikita.mymoney.views

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
         * @param ctx - activity where call this alert
         * @param manager - manager for saveOrUpdate
         * @param categoryAdapter - ???
         * */
        fun showAddingCategoryDialog(ctx: Context, manager: CategoryManager, listItems: ArrayList<Category>,
                                     categoryAdapter: ArrayAdapter<Category>, selectedId: Int = -1) {
            val layout = LinearLayout(ctx)
            layout.orientation = LinearLayout.VERTICAL
            val builder = AlertDialog.Builder(ctx)
            val name = EditText(ctx)
            name.hint = NAME_NINT_TEXT
            if (selectedId != -1) {
                name.setText(listItems[selectedId].name)
            }

            layout.addView(name)
            layout.setPadding(LEFT_PADDING, TOP_PADDING, RIGHT_PADDING, BOTTOM_PADDING)
            builder.setView(layout)
            builder.setPositiveButton(OK_BTN_LABEL) { _, _ ->
                val category: Category
                if (selectedId == -1) {
                    category = Category(id = null, name = name.text.toString());
                    editListActivity(category, manager, listItems, categoryAdapter)
                } else {
                    category = Category(id = listItems[selectedId].id, name = name.text.toString())
                    editListActivity(category, manager, listItems, categoryAdapter)
                    listItems[selectedId] = category
                }
            }

            builder.setNegativeButton(CANCEL_BTN_LABEL) { b, _ -> b.cancel() }
            builder.setTitle(ADDING_TEXT_TITLE)
            builder.show()
        }

        private fun editListActivity(category: Category, manager: CategoryManager,
                                     listItems: ArrayList<Category>, adapter: ArrayAdapter<Category>) {

            if (addNewCategory(category, manager)) {
                addItems(category, listItems)
            }
            adapter.notifyDataSetChanged()
        }

        private fun addItems(Category: Category, listItems: ArrayList<Category>) {
            listItems.add(Category)
        }

        private fun addNewCategory(entity: Category, manager: CategoryManager): Boolean {
            manager.saveOrUpdate(entity)
            return entity.id == null
        }
    }
}
