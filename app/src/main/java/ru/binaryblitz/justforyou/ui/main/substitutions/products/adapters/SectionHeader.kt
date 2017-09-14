package ru.binaryblitz.justforyou.ui.main.substitutions.products.adapters

import com.intrusoft.sectionedrecyclerview.Section
import ru.binaryblitz.justforyou.network.responses.product_types.ProductsItem

class SectionHeader(internal var itemsList: List<ProductsItem>,
    headerText: String) : Section<ProductsItem> {
  var headerText: String
    internal set

  init {
    this.headerText = headerText
  }

  override fun getChildItems(): List<ProductsItem> {
    return itemsList
  }
}
