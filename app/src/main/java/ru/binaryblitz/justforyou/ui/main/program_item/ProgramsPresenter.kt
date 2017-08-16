package ru.binaryblitz.justforyou.ui.main.program_item

import com.arellomobile.mvp.InjectViewState
import ru.binaryblitz.justforyou.data.user.UserProfileStorage
import ru.binaryblitz.justforyou.data.user.UserStorageImpl
import ru.binaryblitz.justforyou.di.JustForYouApp
import ru.binaryblitz.justforyou.network.NetworkService
import ru.binaryblitz.justforyou.ui.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class ProgramsPresenter : BasePresenter<ProgramsView>() {
  @Inject
  lateinit var networkService: NetworkService
  var userProfileStorage: UserProfileStorage = UserStorageImpl()

  init {
    JustForYouApp.appComponent?.inject(this)
  }

  fun getBlockPrograms(blockId: Int) {
    viewState.showProgress()
    networkService.getPrograms(blockId, userProfileStorage.getToken())
        .subscribe(
            { programs ->
              viewState.hideProgress()
              viewState.showPrograms(programs)
            },
            { errorResponse ->
              viewState.showError(errorResponse.localizedMessage)
              viewState.hideProgress()
            }
        )
  }
}
