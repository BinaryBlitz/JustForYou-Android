package ru.binaryblitz.justforyou.ui.main.cart

import com.arellomobile.mvp.InjectViewState
import ru.binaryblitz.justforyou.data.cart.CartLocalStorage
import ru.binaryblitz.justforyou.data.cart.CartModel
import ru.binaryblitz.justforyou.data.cart.ProgramsStorage
import ru.binaryblitz.justforyou.data.user.UserProfileStorage
import ru.binaryblitz.justforyou.data.user.UserStorageImpl
import ru.binaryblitz.justforyou.di.JustForYouApp
import ru.binaryblitz.justforyou.network.NetworkService
import ru.binaryblitz.justforyou.network.responses.orders.DeliveryBody
import ru.binaryblitz.justforyou.network.responses.orders.OrderBody
import ru.binaryblitz.justforyou.ui.base.BasePresenter
import javax.inject.Inject

/**
 * This presenter contains payment logic.
 * First user creates an order,
 * After that select payment method (add a new card or taking from previously added)
 * Then if payment succeeds, add delivery days to the current purchase.
 */

@InjectViewState
class CartPresenter : BasePresenter<CartView>() {
  var cartProgramsLocalStorage: CartLocalStorage = ProgramsStorage()
  var userProfileStorage: UserProfileStorage = UserStorageImpl()
  lateinit var programs: List<CartModel>
  @Inject
  lateinit var networkService: NetworkService
  var orderId: Int = 0
  var purchaseId: Int = 0
  var isBrowserPaymentOpened = false

  init {
    JustForYouApp.appComponent?.inject(this)
  }

  fun getProgramsFromCart() {
    programs = cartProgramsLocalStorage.getCartPrograms()
    viewState.showPrograms(programs)
  }

  /**
   * Creates an order, if succeed -> gets user payment cards
   */
  fun createOrder(orderBody: OrderBody) {
    viewState.showProgress()
    networkService.createOrder(orderBody, userProfileStorage.getToken())
        .subscribe(
            { order ->
              orderId = order.id
              viewState.hideProgress()
              viewState.orderCreated(order)
            },
            { errorResponse ->
              viewState.hideProgress()
              viewState.showError(errorResponse.localizedMessage)
            }
        )
  }

  /**
   * Adds new credit card and makes payment in webview using url
   */
  fun addNewPaymentCard() {
    viewState.showPaymentProgress()
    networkService.makeOrderPayment(orderId, userProfileStorage.getToken())
        .subscribe(
            { response ->
              isBrowserPaymentOpened = true
              viewState.openPaymentUrl(response.paymentUrl!!)
              cartProgramsLocalStorage.clear()
              viewState.hidePaymentProgress()
            },
            { errorResponse ->
              viewState.hidePaymentProgress()
              viewState.showError(errorResponse.localizedMessage)
            }
        )
  }

  /**
   * Creates payment using previously added user's credit card
   */
  fun makePaymentWithCard(cardId: Int) {
    viewState.showPaymentProgress()
    networkService.payWithCreditCard(cardId, orderId, userProfileStorage.getToken())
        .subscribe(
            { response ->
              isBrowserPaymentOpened = true
              viewState.showSuccessPaymentMessage()
              viewState.hidePaymentProgress()
              addDeliveryDaysToLastPurchase()
            },
            { errorResponse ->
              viewState.hidePaymentProgress()
              viewState.showError(errorResponse.localizedMessage)
            }
        )
  }

  /**
   * First, gets all purchases, sorts it by descending and takes latest purchaseId
   * After that adds delivery days to that purchase
   */
  fun addDeliveryDaysToLastPurchase() {
    viewState.showProgress()
    networkService.getPurchases(userProfileStorage.getToken())
        .subscribe(
            { purchases ->
              purchases.sortedByDescending { it.id }
              purchaseId = purchases[0].id!!
              viewState.hideProgress()
              viewState.sendDeliveryDays()
            },
            { errorResponse ->
              viewState.hideProgress()
              viewState.showError(errorResponse.localizedMessage)
            }
        )
  }

  /**
   * Adds delivery days to selected purchase item
   */
  fun addDeliveryDays(deliveryBody: DeliveryBody) {
    viewState.showProgress()
    networkService.addDeliveryDays(purchaseId, deliveryBody, userProfileStorage.getToken())
        .subscribe(
            { response ->
              viewState.hideProgress()
            },
            { errorResponse ->
              viewState.hideProgress()
              viewState.showError(errorResponse.localizedMessage)
            }
        )
  }

  /**
   * Gets all user's payment cards
   */
  fun getCards() {
    viewState.showProgress()
    networkService.getPaymentCards(userProfileStorage.getToken())
        .subscribe(
            { cards ->
              viewState.hideProgress()
              viewState.showPaymentCards(cards)
            },
            { errorResponse ->
              viewState.hideProgress()
              viewState.showError(errorResponse.localizedMessage)
            }
        )
  }

}
