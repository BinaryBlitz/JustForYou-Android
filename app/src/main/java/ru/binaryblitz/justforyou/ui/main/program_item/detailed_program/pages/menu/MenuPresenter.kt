package ru.binaryblitz.justforyou.ui.main.program_item.detailed_program.pages.menu

import com.arellomobile.mvp.InjectViewState
import ru.binaryblitz.justforyou.data.user.UserStorageImpl
import ru.binaryblitz.justforyou.di.JustForYouApp
import ru.binaryblitz.justforyou.network.NetworkService
import ru.binaryblitz.justforyou.ui.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class MenuPresenter : BasePresenter<MenuView>() {
  @Inject
  lateinit var networkService: NetworkService
  @Inject
  lateinit var userProfileStorage: UserStorageImpl

  init {
    JustForYouApp.appComponent?.inject(this)
  }

  fun getMenu(programId: Int) {
    viewState.showProgress()
    networkService.getMenu(programId, userProfileStorage.getToken())
        .subscribe(
            { menu ->
              viewState.hideProgress()
              viewState.showMenu(menu)
            },
            { errorResponse ->
              viewState.showError(errorResponse.localizedMessage)
              viewState.hideProgress()
            }
        )
  }
}
