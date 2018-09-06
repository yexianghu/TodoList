package com.yezi.todolist.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yezi.todolist.R
import kotlinx.android.synthetic.main.dialog_new_list.view.*


class NewListThemeViewHolder(v: View) : RecyclerView.ViewHolder(v) {
}

class NewListThemeAdapter(private val context:Context, private val themeColors:IntArray) : RecyclerView.Adapter<NewListThemeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewListThemeViewHolder {
        val iv = ImageView(parent.context)
        val sideOfLength = parent.context.resources.getDimensionPixelSize(R.dimen.dialog_new_list_theme_select_length)
        iv.layoutParams = ViewGroup.LayoutParams(sideOfLength, sideOfLength)
        return NewListThemeViewHolder(iv)
    }

    override fun getItemCount(): Int {
        return themeColors.size
    }

    override fun onBindViewHolder(holder: NewListThemeViewHolder, position: Int) {
        val imageView = holder.itemView as ImageView
        imageView.setImageDrawable(ColorDrawable(themeColors[position]))
    }

}


fun createNewListDialog(context: Context):AlertDialog {
    val dialogBuilder = AlertDialog.Builder(context, R.style.DialogTheme)
    val view = LayoutInflater.from(context).inflate(R.layout.dialog_new_list, null)

    view.new_list_theme.apply {
        layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        adapter = NewListThemeAdapter(context, context.resources.getIntArray(R.array.list_theme_colors))
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
        itemDecoration.setDrawable(ColorDrawable(Color.GREEN))
        this.addItemDecoration(itemDecoration)
    }

    dialogBuilder.setView(view)

    dialogBuilder.setPositiveButton(R.string.dialog_create_new_list) {
        dialog, _ ->
            //view.new_list_name.text
    }
    dialogBuilder.setNegativeButton(R.string.dialog_cancel) { dialog, _ -> dialog.cancel()}
    return dialogBuilder.create()
}