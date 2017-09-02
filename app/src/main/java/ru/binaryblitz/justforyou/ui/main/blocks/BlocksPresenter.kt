package ru.binaryblitz.justforyou.ui.main.blocks

import com.arellomobile.mvp.InjectViewState
import ru.binaryblitz.justforyou.data.user.UserStorageImpl
import ru.binaryblitz.justforyou.di.JustForYouApp
import ru.binaryblitz.justforyou.network.NetworkService
import ru.binaryblitz.justforyou.ui.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class BlocksPresenter : BasePresenter<BlocksView>() {
  @Inject
  lateinit var networkService: NetworkService
  @Inject
  lateinit var userProfileStorage: UserStorageImpl

  init {
    JustForYouApp.appComponent?.inject(this)
  }

  fun getFoodBlocks() {
    viewState.showProgress()
    networkService.getFoodBlocks(userProfileStorage.getToken())
        .subscribe(
            { programs ->
              viewState.hideProgress()
              viewState.showBlocks(programs)
            },
            { errorResponse ->
              viewState.showError(errorResponse.localizedMessage)
              viewState.hideProgress()
            }
        )
  }
}
