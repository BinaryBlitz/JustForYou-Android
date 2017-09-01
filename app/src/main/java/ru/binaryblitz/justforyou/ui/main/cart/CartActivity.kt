package ru.binaryblitz.justforyou.ui.main.cart

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetBehavior.BottomSheetCallback
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import io.reactivex.subjects.PublishSubject
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_cart.cardPickerAlertSheet
import kotlinx.android.synthetic.main.activity_cart.cartProgramsList
import kotlinx.android.synthetic.main.activity_cart.cartProgress
import kotlinx.android.synthetic.main.activity_cart.makeOrderButton
import kotlinx.android.synthetic.main.activity_cart.noCardsView
import kotlinx.android.synthetic.main.activity_cart.orderWithNewCardButton
import kotlinx.android.synthetic.main.activity_cart.paymentContainer
import kotlinx.android.synthetic.main.activity_cart.paymentProgress
import kotlinx.android.synthetic.main.activity_cart.userCardView
import kotlinx.android.synthetic.main.activity_program.toolbar
import kotlinx.android.synthetic.main.cart_item.view.blockTitle
import kotlinx.android.synthetic.main.cart_item.view.closeAlert
import kotlinx.android.synthetic.main.cart_item.view.programTitle
import kotlinx.android.synthetic.main.cart_item.view.programsPrice
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.string
import ru.binaryblitz.justforyou.components.Extras
import ru.binaryblitz.justforyou.data.cart.CartLocalStorage
import ru.binaryblitz.justforyou.data.cart.CartModel
import ru.binaryblitz.justforyou.data.cart.ProgramsStorage
import ru.binaryblitz.justforyou.data.user.UserProfileStorage
import ru.binaryblitz.justforyou.data.user.UserStorageImpl
import ru.binaryblitz.justforyou.network.responses.orders.DeliveriesItem
import ru.binaryblitz.justforyou.network.responses.orders.DeliveryBody
import ru.binaryblitz.justforyou.network.responses.orders.LineItemsAttributesItem
import ru.binaryblitz.justforyou.network.responses.orders.Order
import ru.binaryblitz.justforyou.network.responses.orders.OrderBody
import ru.binaryblitz.justforyou.network.responses.orders.OrderResponse
import ru.binaryblitz.justforyou.network.responses.payment_cards.PaymentCard
import ru.binaryblitz.justforyou.ui.base.BaseRecyclerAdapter
import ru.binaryblitz.justforyou.ui.main.settings.payment_cards.CardsAdapter
import ru.binaryblitz.justforyou.ui.router.Router

class CartActivity : MvpAppCompatActivity(), CartView {
  @InjectPresenter
  lateinit var presenter: CartPresenter
  lateinit var adapter: CartAdapter
  lateinit var cardsAdapter: CardsAdapter
  var cartProgramsLocalStorage: CartLocalStorage = ProgramsStorage()
  var userProfileStorage: UserProfileStorage = UserStorageImpl()
  lateinit var creditCardsBehavior: BottomSheetBehavior<View>

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
    creditCardsBehavior = BottomSheetBehavior.from(cardPickerAlertSheet as View)
    creditCardsBehavior.setBottomSheetCallback(object : BottomSheetCallback() {
      override fun onSlide(bottomSheet: View, slideOffset: Float) {
      }

      override fun onStateChanged(bottomSheet: View, newState: Int) {
        if (newState == BottomSheetBehavior.STATE_HIDDEN) {
        }
      }
    })
    creditCardsBehavior.state = BottomSheetBehavior.STATE_HIDDEN
  }

  private fun setTotalAmount() {
    var paymentAmount = 0
    if (presenter.programs.isNotEmpty()) {
      makeOrderButton.visibility = View.VISIBLE
      for ((position) in presenter.programs.withIndex()) {
        paymentAmount += presenter.programs[position].price!! * presenter.programs[position].days!!
      }
    }
    makeOrderButton.text = String.format(getString(string.pay_sum), paymentAmount)
  }

  override fun showPrograms(programs: List<CartModel>) {
    cartProgramsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    adapter = CartAdapter()
    cartProgramsList.adapter = adapter
    adapter.setData(programs)
    adapter.onItemSelectAction.subscribe { model ->
      cartProgramsLocalStorage.removeProgramFromCart(model.programId!!)
    }
    makeOrderButton.setOnClickListener {
      createOrder(programs)
    }
    setTotalAmount()
  }

  private fun createOrder(programs: List<CartModel>) {
    val attributes = ArrayList<LineItemsAttributesItem>()
    for ((position) in programs.withIndex()) {
      attributes.add(
          LineItemsAttributesItem(programs[position].days, programs[position].programId))
    }
    presenter.createOrder(OrderBody(Order(attributes, userProfileStorage.getUser().phoneNumber)))
  }

  override fun orderCreated(order: OrderResponse) {
    presenter.getCards()
  }

  override fun showSuccessPaymentMessage() {
    Toast.makeText(applicationContext, getString(string.success_payment), Toast.LENGTH_SHORT).show()
    finish()
  }

  override fun showPaymentCards(cards: List<PaymentCard>) {
    creditCardsBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    cardsAdapter = CardsAdapter()
    userCardView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    userCardView.adapter = cardsAdapter
    cardsAdapter.setData(cards)
    cardsAdapter.onItemSelectAction.subscribe { card -> presenter.makePaymentWithCard(card.id) }
    orderWithNewCardButton.setOnClickListener { presenter.addNewPaymentCard() }
    if (cards.size == 0) {
      noCardsView.visibility = View.VISIBLE
    } else {
      noCardsView.visibility = View.GONE
    }
  }

  override fun openPaymentUrl(url: String) {
    Router.openPaymentScreen(this, url, Extras.paymentRequestCode)
    hideProgress()
  }

  override fun sendDeliveryDays() {
    var deliveryDays: RealmList<DeliveriesItem> = RealmList()
    for ((position) in presenter.programs.withIndex()) {
      for ((deliveryItemPosition) in presenter.programs[position].deliveries.withIndex()) {
        var deliveryDay: DeliveriesItem = DeliveriesItem()
        deliveryDay.comment = presenter.programs[position].deliveries[deliveryItemPosition].comment
        deliveryDay.addressId = presenter.programs[position].deliveries[deliveryItemPosition].addressId
        deliveryDay.scheduledFor = presenter.programs[position].deliveries[deliveryItemPosition].scheduledFor
        deliveryDays.add(deliveryDay)
      }
    }
    presenter.addDeliveryDays(DeliveryBody(deliveryDays))
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    return false
  }

  override fun showProgress() {
    cartProgress.visibility = View.VISIBLE
    cartProgramsList.visibility = View.GONE
  }

  override fun hideProgress() {
    cartProgress.visibility = View.GONE
    cartProgramsList.visibility = View.VISIBLE
  }

  override fun showError(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  }

  override fun showPaymentProgress() {
    paymentContainer.visibility = View.INVISIBLE
    paymentProgress.visibility = View.VISIBLE
  }

  override fun hidePaymentProgress() {
    paymentContainer.visibility = View.VISIBLE
    paymentProgress.visibility = View.INVISIBLE
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == RESULT_OK && requestCode == Extras.paymentRequestCode) {
      presenter.addDeliveryDaysToLastPurchase()
    }
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
      itemView.closeAlert.setOnClickListener {
        dataItems.remove(item)
        notifyItemRemoved(position)
        onItemSelectAction.onNext(item)
      }
    }

  }
}
