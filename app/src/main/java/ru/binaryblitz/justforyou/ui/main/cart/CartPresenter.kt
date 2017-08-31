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

@InjectViewState
class CartPresenter : BasePresenter<CartView>() {
  var cartProgramsLocalStorage: CartLocalStorage = ProgramsStorage()
  var userProfileStorage: UserProfileStorage = UserStorageImpl()
  lateinit var programs: List<CartModel>
  @Inject
  lateinit var networkService: NetworkService
  private var orderId: Int = 0

  init {
    JustForYouApp.appComponent?.inject(this)
  }

  fun getProgramsFromCart() {
    programs = cartProgramsLocalStorage.getCartPrograms()
    viewState.showPrograms(programs)
  }

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

  fun makePayment() {
    viewState.showPaymentProgress()
    networkService.makeOrderPayment(orderId, userProfileStorage.getToken())
        .subscribe(
            { response ->
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

  fun makePaymentWithCard(cardId: Int) {
    viewState.showPaymentProgress()
    networkService.payWithCreditCard(cardId, orderId, userProfileStorage.getToken())
        .subscribe(
            { response ->
              cartProgramsLocalStorage.clear()
              viewState.showSuccessPaymentMessage()
              viewState.hidePaymentProgress()
            },
            { errorResponse ->
              viewState.hidePaymentProgress()
              viewState.showError(errorResponse.localizedMessage)
            }
        )
  }

  fun addDeliveryDays(deliveryBody: DeliveryBody, orderId: Int) {
    viewState.showProgress()
    networkService.addDeliveryDays(orderId, deliveryBody, userProfileStorage.getToken())
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
