package ru.binaryblitz.justforyou.ui.main.substitutions.products

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_user_orders.ordersProgress
import kotlinx.android.synthetic.main.activity_user_orders.toolbar
import kotlinx.android.synthetic.main.content_user_orders.ordersView
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.network.responses.product_types.ProductTypes
import ru.binaryblitz.justforyou.ui.main.substitutions.products.adapters.ProductsAdapter
import ru.binaryblitz.justforyou.ui.main.substitutions.products.adapters.SectionHeader


class ProductsActivity : MvpAppCompatActivity(), ProductsView {
  @InjectPresenter
  lateinit var presenter: ProductsPresenter
  lateinit var adapter: ProductsAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_orders)
    initViewElements()
    presenter.getProducts()
  }

  private fun initViewElements() {
    setSupportActionBar(toolbar)
    toolbar.title = "Продукты"
    toolbar.setNavigationIcon(R.drawable.ic_arrow_back24b)
    toolbar.setNavigationOnClickListener { onBackPressed() }
  }

  override fun showProgress() {
    ordersProgress.visibility = View.VISIBLE
  }

  override fun hideProgress() {
    ordersProgress.visibility = View.GONE
  }

  override fun showError(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  }

  override fun showProducts(productsTypes: List<ProductTypes>) {
    ordersView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    val sections = ArrayList<SectionHeader>()
    for ((sectionPosition) in productsTypes.withIndex()) {
      val section = SectionHeader(productsTypes[sectionPosition].products!!,
          productsTypes[sectionPosition].name!!)
      sections.add(section)
    }
    adapter = ProductsAdapter(this, sections)
    ordersView.adapter = adapter

    adapter.onItemSelectAction.subscribe { productId -> presenter.createNewSub(productId) }

  }

  override fun successAddition() {
    setResult(Activity.RESULT_OK)
    finish()
  }
}
