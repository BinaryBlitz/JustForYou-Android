package ru.binaryblitz.justforyou.ui.main.substitutions.products.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import ru.binaryblitz.justforyou.R

class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  var header: TextView

  init {
    header = itemView.findViewById<View>(R.id.section) as TextView
  }
}
