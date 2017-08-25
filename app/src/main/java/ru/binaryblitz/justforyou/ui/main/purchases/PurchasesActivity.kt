package ru.binaryblitz.justforyou.ui.main.purchases

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_purchases.purchasesProgress
import kotlinx.android.synthetic.main.activity_purchases.toolbar
import kotlinx.android.synthetic.main.content_purchases.purchasesView
import kotlinx.android.synthetic.main.purchase_item.view.daysRemainingCount
import kotlinx.android.synthetic.main.purchase_item.view.programName
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.string
import ru.binaryblitz.justforyou.network.responses.purchases.PurchasesResponse
import ru.binaryblitz.justforyou.ui.base.BaseRecyclerAdapter

class PurchasesActivity : MvpAppCompatActivity(), PurchasesView {
  @InjectPresenter
  lateinit var presenter: PurchasesPresenter
  lateinit var adapter: PurchasesAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_purchases)
    initViewElements()
    presenter.getPurchases()
  }

  private fun initViewElements() {
    setSupportActionBar(toolbar)
    toolbar.title = getString(string.my_purchases)
    toolbar.setNavigationIcon(R.drawable.ic_arrow_back24b)
    toolbar.setNavigationOnClickListener { onBackPressed() }
  }

  override fun showProgress() {
    purchasesProgress.visibility = View.VISIBLE
  }

  override fun hideProgress() {
    purchasesProgress.visibility = View.GONE
  }

  override fun showError(message: String) {
  }

  override fun showPurchases(addresses: List<PurchasesResponse>) {
    adapter = PurchasesAdapter()
    purchasesView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    purchasesView.adapter = adapter
    adapter.setData(addresses)
  }

}

class PurchasesAdapter : BaseRecyclerAdapter<PurchasesResponse>() {
  var onItemSelectAction: PublishSubject<PurchasesResponse> = PublishSubject.create()

  override fun onCreateViewHolder(parent: ViewGroup,
      viewType: Int): RecyclerViewHolder<PurchasesResponse> {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.purchase_item, parent, false)
    return ViewHolder(view)
  }

  private inner class ViewHolder(itemView: View) : RecyclerViewHolder<PurchasesResponse>(itemView) {

    override fun setItem(item: PurchasesResponse, position: Int) {
      itemView.programName.text = item.program?.name
      itemView.daysRemainingCount.text = "${item.numberOfDays} дней"
    }

  }
}
