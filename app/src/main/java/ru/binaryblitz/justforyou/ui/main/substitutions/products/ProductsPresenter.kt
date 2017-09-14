package ru.binaryblitz.justforyou.ui.main.substitutions.products

import com.arellomobile.mvp.InjectViewState
import ru.binaryblitz.justforyou.data.user.UserStorageImpl
import ru.binaryblitz.justforyou.di.JustForYouApp
import ru.binaryblitz.justforyou.network.NetworkService
import ru.binaryblitz.justforyou.ui.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class ProductsPresenter : BasePresenter<ProductsView>() {
  @Inject
  lateinit var networkService: NetworkService
  @Inject
  lateinit var userProfileStorage: UserStorageImpl

  init {
    JustForYouApp.appComponent?.inject(this)
  }

  fun getProducts() {
    viewState.showProgress()
    networkService.getProducts(userProfileStorage.getToken())
        .subscribe(
            { products ->
              viewState.hideProgress()
              viewState.showProducts(products)
            },
            { errorResponse ->
              viewState.hideProgress()
              viewState.showError(errorResponse.localizedMessage)
            }
        )
  }

  fun createNewSub(productId: Int) {
    viewState.showProgress()
    networkService.createUserSubstitution(productId, userProfileStorage.getToken())
        .subscribe(
            { products ->
              viewState.hideProgress()
              viewState.successAddition()
            },
            { errorResponse ->
              viewState.hideProgress()
              viewState.showError("Вы уже добавили данный продукт ранее")
            }
        )
  }

}
