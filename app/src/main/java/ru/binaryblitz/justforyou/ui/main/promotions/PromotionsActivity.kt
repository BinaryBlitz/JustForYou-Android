package ru.binaryblitz.justforyou.ui.main.promotions

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_user_orders.ordersProgress
import kotlinx.android.synthetic.main.activity_user_orders.toolbar
import kotlinx.android.synthetic.main.content_user_orders.ordersView
import kotlinx.android.synthetic.main.order_item.view.orderPrice
import kotlinx.android.synthetic.main.order_item.view.orderTitle
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.string
import ru.binaryblitz.justforyou.network.responses.promotions.Promotion
import ru.binaryblitz.justforyou.ui.base.BaseRecyclerAdapter

class PromotionsActivity : MvpAppCompatActivity(), PromotionsView {
  @InjectPresenter
  lateinit var presenter: PromotionsPresenter
  lateinit var adapter: OrdersAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_orders)
    initViewElements()
    presenter.getPromotions()
  }

  private fun initViewElements() {
    setSupportActionBar(toolbar)
    toolbar.title = getString(string.promotions_title)
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

  override fun showPromotions(promotions: List<Promotion>) {
    adapter = OrdersAdapter()
    ordersView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    ordersView.adapter = adapter
    adapter.setData(promotions)
  }

}

class OrdersAdapter : BaseRecyclerAdapter<Promotion>() {
  override fun onCreateViewHolder(parent: ViewGroup,
      viewType: Int): RecyclerViewHolder<Promotion> {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)
    return ViewHolder(view)
  }

  private inner class ViewHolder(itemView: View) : RecyclerViewHolder<Promotion>(itemView) {

    override fun setItem(item: Promotion, position: Int) {
      itemView.orderTitle.text = item.name
      itemView.orderPrice.text = item.description
      itemView.orderPrice.setTextColor(itemView.context.resources.getColor(R.color.greyColor))
    }

  }
}

