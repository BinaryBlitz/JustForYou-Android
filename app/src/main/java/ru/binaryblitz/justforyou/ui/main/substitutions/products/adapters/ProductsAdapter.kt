package ru.binaryblitz.justforyou.ui.main.substitutions.products.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter
import io.reactivex.subjects.PublishSubject
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.network.responses.product_types.ProductsItem

data class ProductsAdapter(var context: Context,
    var sectionItemList: List<SectionHeader>?) : SectionRecyclerViewAdapter<SectionHeader, ProductsItem, SectionViewHolder, ChildViewHolder>(
    context, sectionItemList) {
  var onItemSelectAction: PublishSubject<Int> = PublishSubject.create()

  override fun onCreateSectionViewHolder(sectionViewGroup: ViewGroup,
      viewType: Int): SectionViewHolder {
    val view = LayoutInflater.from(context).inflate(R.layout.product_header, sectionViewGroup,
        false)
    return SectionViewHolder(view)
  }

  override fun onCreateChildViewHolder(childViewGroup: ViewGroup, viewType: Int): ChildViewHolder {
    val view = LayoutInflater.from(context).inflate(R.layout.product_item, childViewGroup, false)
    return ChildViewHolder(view)
  }

  override fun onBindSectionViewHolder(sectionViewHolder: SectionViewHolder, sectionPosition: Int,
      SectionHeader: SectionHeader) {
    sectionViewHolder.header.text = SectionHeader.headerText
  }

  override fun onBindChildViewHolder(childViewHolder: ChildViewHolder, sectionPosition: Int,
      childPosition: Int, product: ProductsItem) {
    childViewHolder.name.text = product.name
    childViewHolder.name.setOnClickListener {
      onItemSelectAction.onNext(sectionItemList!![sectionPosition].itemsList[childPosition].id!!)
    }
  }
}
