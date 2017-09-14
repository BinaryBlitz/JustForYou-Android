package ru.binaryblitz.justforyou.ui.main.substitutions.products.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import ru.binaryblitz.justforyou.R

class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  var name: TextView

  init {
    name = itemView.findViewById<View>(R.id.productName) as TextView
  }
}
