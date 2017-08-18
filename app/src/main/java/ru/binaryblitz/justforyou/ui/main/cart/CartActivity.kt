package ru.binaryblitz.justforyou.ui.main.cart

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_cart.cartProgramsList
import kotlinx.android.synthetic.main.activity_program.toolbar
import kotlinx.android.synthetic.main.cart_item.view.blockTitle
import kotlinx.android.synthetic.main.cart_item.view.programTitle
import kotlinx.android.synthetic.main.cart_item.view.programsPrice
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.string
import ru.binaryblitz.justforyou.data.cart.CartModel
import ru.binaryblitz.justforyou.ui.base.BaseRecyclerAdapter

class CartActivity : MvpAppCompatActivity(), CartView {
  @InjectPresenter
  lateinit var presenter: CartPresenter
  lateinit var adapter: CartAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_cart)
    initViewElements(getString(string.cart))
    presenter.getProgramsFromCart()
  }

  private fun initViewElements(title: String) {
    toolbar.title = title
    toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp)
    toolbar.setNavigationOnClickListener { onBackPressed() }
  }

  override fun showPrograms(programs: List<CartModel>) {
    cartProgramsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    adapter = CartAdapter()
    cartProgramsList.adapter = adapter
    adapter.setData(programs)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    return false
  }
}

class CartAdapter : BaseRecyclerAdapter<CartModel>() {
  var onItemSelectAction: PublishSubject<CartModel> = PublishSubject.create()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder<CartModel> {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
    return ViewHolder(view)
  }

  private inner class ViewHolder(itemView: View) : RecyclerViewHolder<CartModel>(itemView) {

    override fun setItem(item: CartModel, position: Int) {
      itemView.programTitle.text = item.programName
      itemView.blockTitle.text = item.blockName
      itemView.programsPrice.text = String.format(
          itemView.context.getString(R.string.program_price),
          item.price, item.days, item.price!! * item.days!!)
    }

  }
}
