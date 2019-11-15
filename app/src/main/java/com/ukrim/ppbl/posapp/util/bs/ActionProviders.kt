package com.ukrim.ppbl.posapp.util.bs

import android.content.Context
import com.arthurivanets.bottomsheets.sheets.model.Option
import com.ukrim.ppbl.posapp.R

object ActionProviders {

    object Id {
        const val EDIT = 1L
        const val DELETE = 2L
    }

    fun getActionOptions(context: Context): List<Option> {
        return ArrayList<Option>().apply {
            // Edit Option
            add(
                context.createOption(
                    Id.EDIT,
                    R.drawable.ic_edit,
                    context.getString(R.string.action_edit)
                )
            )
            add(
                context.createOption(
                    Id.DELETE,
                    R.drawable.ic_delete,
                    context.getString(R.string.action_delete)
                )
            )
        }
    }

}