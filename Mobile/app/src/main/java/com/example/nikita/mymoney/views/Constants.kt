package com.example.nikita.mymoney.views

import android.R
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.nikita.mymoney.database.manager.SimpleManager

class Constants {
    companion object {
        const val OK_BTN_LABEL = "OK"
        const val CANCEL_BTN_LABEL = "Cancel"
        const val NAME_NINT_TEXT = "Name"
        const val COST_NINT_TEXT = "Cost"
        const val ADDING_TEXT_TITLE = "Adding"
        const val EDIT_LABEL = "Edit"
        const val REMOVE_LABEL = "Remove?"
        const val NOT_SELECTED = "Not selected"
        const val LEFT_PADDING = 45
        const val RIGHT_PADDING = 45
        const val TOP_PADDING = 5
        const val BOTTOM_PADDING = 5
        const val DEFAULT_SELECTED_ITEM_ID = -1

        fun dropdownComponent(ctx: Context, manager: SimpleManager): Spinner {
            val dropdown = Spinner(ctx)
            val items = manager.getAllCategories()
            dropdown.adapter = ArrayAdapter(ctx, R.layout.simple_spinner_dropdown_item, items)
            return dropdown
        }
    }
}