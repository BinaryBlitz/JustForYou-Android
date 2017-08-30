package ru.binaryblitz.justforyou.ui.main.settings.payment_cards

import com.arellomobile.mvp.InjectViewState
import ru.binaryblitz.justforyou.data.user.UserProfileStorage
import ru.binaryblitz.justforyou.data.user.UserStorageImpl
import ru.binaryblitz.justforyou.di.JustForYouApp
import ru.binaryblitz.justforyou.network.NetworkService
import ru.binaryblitz.justforyou.ui.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class PaymentCardsPresenter : BasePresenter<PaymentCardsView>() {
  @Inject
  lateinit var networkService: NetworkService
  var userProfileStorage: UserProfileStorage = UserStorageImpl()

  init {
    JustForYouApp.appComponent?.inject(this)
  }

  fun getCards() {
    viewState.showProgress()
    networkService.getPaymentCards(userProfileStorage.getToken())
        .subscribe(
            { cards ->
              viewState.hideProgress()
              viewState.showCards(cards)
            },
            { errorResponse ->
              viewState.hideProgress()
              viewState.showError(errorResponse.localizedMessage)
            }
        )
  }

}
