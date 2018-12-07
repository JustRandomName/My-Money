package com.example.nikita.mymoney.views

import android.app.AlertDialog
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import com.example.nikita.mymoney.database.manager.CategoryManager
import com.example.nikita.mymoney.database.model.Category
import com.example.nikita.mymoney.Constants.Companion.ADDING_TEXT_TITLE
import com.example.nikita.mymoney.Constants.Companion.BOTTOM_PADDING
import com.example.nikita.mymoney.Constants.Companion.CANCEL_BTN_LABEL
import com.example.nikita.mymoney.Constants.Companion.DEFAULT_SELECTED_ITEM_ID
import com.example.nikita.mymoney.Constants.Companion.LEFT_PADDING
import com.example.nikita.mymoney.Constants.Companion.NAME_NINT_TEXT
import com.example.nikita.mymoney.Constants.Companion.OK_BTN_LABEL
import com.example.nikita.mymoney.Constants.Companion.RIGHT_PADDING
import com.example.nikita.mymoney.Constants.Companion.TOP_PADDING
import com.example.nikita.mymoney.database.model.CategoryDTO


class AddingCategoriesDialog {
    companion object {

        /**
         * @param ctx - activity where call this alert
         * @param manager - categoryManager for saveOrUpdate
         * @param categoryAdapter - ???
         * */
        fun showAddingCategoryDialog(ctx: Context, manager: CategoryManager, listItems: ArrayList<CategoryDTO>,
                                     categoryAdapter: ArrayAdapter<CategoryDTO>, selectedId: Int = DEFAULT_SELECTED_ITEM_ID) {
            val layout = LinearLayout(ctx)
            layout.orientation = LinearLayout.VERTICAL
            val builder = AlertDialog.Builder(ctx)
            val name = EditText(ctx)
            name.hint = NAME_NINT_TEXT
            if (selectedId != DEFAULT_SELECTED_ITEM_ID) {
                name.setText(listItems[selectedId].name)
            }

            layout.addView(name)
            layout.setPadding(LEFT_PADDING, TOP_PADDING, RIGHT_PADDING, BOTTOM_PADDING)
            builder.setView(layout)
            builder.setPositiveButton(OK_BTN_LABEL) { _, _ ->
                val category: CategoryDTO
                if (selectedId == DEFAULT_SELECTED_ITEM_ID) {
                    category = CategoryDTO(catId = null, name = name.text.toString())
                    editListActivity(category, manager, listItems, categoryAdapter)
                } else {
                    category = CategoryDTO(catId = listItems[selectedId].catId, name = name.text.toString())
                    editListActivity(category, manager, listItems, categoryAdapter)
                    listItems[selectedId] = category
                }
            }

            builder.setNegativeButton(CANCEL_BTN_LABEL) { b, _ -> b.cancel() }
            builder.setTitle(ADDING_TEXT_TITLE)
            builder.show()
        }

        private fun editListActivity(category: CategoryDTO, manager: CategoryManager,
                                     listItems: ArrayList<CategoryDTO>, adapter: ArrayAdapter<CategoryDTO>) {

            if (addNewCategory(category.entity, manager)) {
                listItems.add(category)
            }
            adapter.notifyDataSetChanged()
        }

        private fun addNewCategory(entity: Category, manager: CategoryManager): Boolean {
            return if (entity.id == null) {
                manager.saveOrUpdate(entity)
                true
            } else {
                manager.saveOrUpdate(entity)
                false
            }
        }
    }
}
