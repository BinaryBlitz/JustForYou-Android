package ru.binaryblitz.justforyou.ui.main.substitutions

import android.app.Activity
import android.app.AlertDialog.Builder
import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_user_subs.newSubsButton
import kotlinx.android.synthetic.main.content_user_orders.ordersView
import kotlinx.android.synthetic.main.order_item.view.orderDate
import kotlinx.android.synthetic.main.order_item.view.orderPrice
import kotlinx.android.synthetic.main.order_item.view.orderTitle
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.string
import ru.binaryblitz.justforyou.components.Extras
import ru.binaryblitz.justforyou.network.responses.substitutions.Substitutions
import ru.binaryblitz.justforyou.ui.base.BaseRecyclerAdapter
import ru.binaryblitz.justforyou.ui.router.Router

class SubstitutionsActivity : MvpAppCompatActivity(), SubstitutionsView {
  @InjectPresenter
  lateinit var presenter: SubstitutionsPresenter
  lateinit var adapter: SubsAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_user_subs)
    initViewElements()
    presenter.getSubstitutions()
  }

  private fun initViewElements() {
    setSupportActionBar(toolbar)
    toolbar.title = "Замены"
    toolbar.setNavigationIcon(R.drawable.ic_arrow_back24b)
    toolbar.setNavigationOnClickListener { onBackPressed() }
    newSubsButton.setOnClickListener {
      if (adapter.itemCount >= 3) {
        showError(
            "Вы не можете добавить больше трех продуктов!")
        return@setOnClickListener
      }
      Router.openProductsScreen(this)
    }
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

  override fun showSubs(subs: List<Substitutions>) {
    adapter = SubsAdapter()
    ordersView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    ordersView.adapter = adapter
    adapter.setData(subs)
    adapter.onItemSelectAction.subscribe { productId -> openAlertDialog(productId) }
  }

  private fun openAlertDialog(id: Int) {
    val alert = Builder(this)
    alert.setTitle("Удалить замену продукта?")
    alert.setPositiveButton(getString(string.yes)) {
      dialog, whichButton ->
      dialog.dismiss()
      presenter.removeSub(id)
    }
    alert.setNegativeButton(getString(string.no)) {
      dialog, whichButton ->
      dialog.dismiss()
    }
    alert.show()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == Activity.RESULT_OK && requestCode == Extras.newSubRequestCode) {
      presenter.getSubstitutions()
    }
  }
}

class SubsAdapter : BaseRecyclerAdapter<Substitutions>() {
  var onItemSelectAction: PublishSubject<Int> = PublishSubject.create()

  override fun onCreateViewHolder(parent: ViewGroup,
      viewType: Int): RecyclerViewHolder<Substitutions> {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)
    return ViewHolder(view)
  }

  private inner class ViewHolder(itemView: View) : RecyclerViewHolder<Substitutions>(itemView) {
    init {
      itemView.setOnLongClickListener {
        selectItem(adapterPosition)
      }
    }

    override fun setItem(item: Substitutions, position: Int) {
      itemView.orderTitle.text = item.product?.name
      itemView.orderPrice.visibility = View.GONE
      itemView.orderDate.visibility = View.GONE
    }
  }

  fun removeItem(adapterPosition: Int): Boolean {
    dataItems.remove(dataItems[adapterPosition])
    notifyItemRemoved(adapterPosition)
    return true
  }

  fun selectItem(adapterPosition: Int): Boolean {
    onItemSelectAction.onNext(dataItems[adapterPosition].id!!)
    return true
  }
}
