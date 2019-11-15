package com.ukrim.ppbl.posapp.util.bs

import android.content.Context
import com.arthurivanets.bottomsheets.sheets.model.Option
import com.ukrim.ppbl.posapp.R

object ConfirmProvider {

    object Id {
        const val CONFIRM = 1L
        const val CANCEL = 2L
    }

    fun getConfirmActionOptions(context: Context): List<Option> {
        return ArrayList<Option>().apply {
            // Edit Option
            add(
                context.createOption(
                    Id.CONFIRM,
                    R.drawable.ic_delete,
                    context.getString(R.string.action_delete)
                )
            )
            add(
                context.createOption(
                    Id.CANCEL,
                    R.drawable.ic_cancel,
                    context.getString(R.string.action_cancel)
                )
            )
        }
    }

}