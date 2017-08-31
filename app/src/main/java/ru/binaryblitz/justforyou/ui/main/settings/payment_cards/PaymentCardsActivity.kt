package ru.binaryblitz.justforyou.ui.main.settings.payment_cards

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_payment_cards.cardsProgress
import kotlinx.android.synthetic.main.activity_payment_cards.toolbar
import kotlinx.android.synthetic.main.content_payment_cards.cardsView
import kotlinx.android.synthetic.main.payment_card_item.view.cardHolder
import kotlinx.android.synthetic.main.payment_card_item.view.cardNumber
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.string
import ru.binaryblitz.justforyou.network.responses.payment_cards.PaymentCard
import ru.binaryblitz.justforyou.ui.base.BaseRecyclerAdapter

class PaymentCardsActivity : MvpAppCompatActivity(), PaymentCardsView {
  @InjectPresenter
  lateinit var presenter: PaymentCardsPresenter
  lateinit var adapter: CardsAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_payment_cards)
    initViewElements()
    presenter.getCards()
  }

  private fun initViewElements() {
    setSupportActionBar(toolbar)
    toolbar.title = getString(string.my_payment_cards)
    toolbar.setNavigationIcon(R.drawable.ic_arrow_back24b)
    toolbar.setNavigationOnClickListener { onBackPressed() }
  }

  override fun showProgress() {
    cardsProgress.visibility = View.VISIBLE
  }

  override fun hideProgress() {
    cardsProgress.visibility = View.GONE
  }

  override fun showError(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  }

  override fun showCards(addresses: List<PaymentCard>) {
    adapter = CardsAdapter()
    cardsView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    cardsView.adapter = adapter
    adapter.setData(addresses)
  }

}

class CardsAdapter : BaseRecyclerAdapter<PaymentCard>() {
  var onItemSelectAction: PublishSubject<PaymentCard> = PublishSubject.create()

  override fun onCreateViewHolder(parent: ViewGroup,
      viewType: Int): RecyclerViewHolder<PaymentCard> {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.payment_card_item, parent,
        false)
    return ViewHolder(view)
  }

  private inner class ViewHolder(itemView: View) : RecyclerViewHolder<PaymentCard>(itemView) {

    override fun setItem(item: PaymentCard, position: Int) {
      itemView.cardHolder.text = item.holder
      itemView.cardNumber.text = item.number
      itemView.setOnClickListener { onItemSelectAction.onNext(item) }
    }

  }
}
