package ru.binaryblitz.justforyou.ui.main.user_orders

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_user_orders.ordersProgress
import kotlinx.android.synthetic.main.activity_user_orders.toolbar
import kotlinx.android.synthetic.main.content_user_orders.ordersView
import kotlinx.android.synthetic.main.order_item.view.orderDate
import kotlinx.android.synthetic.main.order_item.view.orderPrice
import kotlinx.android.synthetic.main.order_item.view.orderTitle
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.string
import ru.binaryblitz.justforyou.components.utils.DateUtils
import ru.binaryblitz.justforyou.network.responses.orders.PurchaseItem
import ru.binaryblitz.justforyou.ui.base.BaseRecyclerAdapter

class UserOrdersActivity : MvpAppCompatActivity(), UserOrdersView {
  @InjectPresenter
  lateinit var presenter: UserOrdersPresenter
  lateinit var adapter: OrdersAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_orders)
    initViewElements()
    presenter.getOrders()
  }

  private fun initViewElements() {
    setSupportActionBar(toolbar)
    toolbar.title = getString(string.payment_history)
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

  override fun showOrders(addresses: List<PurchaseItem>) {
    adapter = OrdersAdapter()
    ordersView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    ordersView.adapter = adapter
    adapter.setData(addresses)
  }

}

class OrdersAdapter : BaseRecyclerAdapter<PurchaseItem>() {
  var onItemSelectAction: PublishSubject<PurchaseItem> = PublishSubject.create()

  override fun onCreateViewHolder(parent: ViewGroup,
      viewType: Int): RecyclerViewHolder<PurchaseItem> {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)
    return ViewHolder(view)
  }

  private inner class ViewHolder(itemView: View) : RecyclerViewHolder<PurchaseItem>(itemView) {

    override fun setItem(item: PurchaseItem, position: Int) {
      if (item.programs?.size != 0) {
        itemView.orderTitle.text = item.programs?.get(0)?.name
      }
      itemView.orderPrice.text = String.format(itemView.context.getString(string.order_price),
          item.totalPrice)
      itemView.orderDate.text = DateUtils.parseServerDate(item.createdAt!!).toString()
    }

  }
}

