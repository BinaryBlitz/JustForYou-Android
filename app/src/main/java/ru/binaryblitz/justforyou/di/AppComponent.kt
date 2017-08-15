package ru.binaryblitz.justforyou.di

import android.content.Context
import com.arellomobile.mvp.MvpView
import dagger.Component
import ru.binaryblitz.justforyou.di.modules.ContextModule
import ru.binaryblitz.justforyou.di.modules.NetworkModule
import ru.binaryblitz.justforyou.network.NetworkService
import ru.binaryblitz.justforyou.ui.base.BasePresenter
import ru.binaryblitz.justforyou.ui.login.LoginPresenter
import ru.binaryblitz.justforyou.ui.main.programs.ProgramsPresenter
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(ContextModule::class, NetworkModule::class))
interface AppComponent {
  val context: Context
  val networkService: NetworkService
  fun inject(presenter: LoginPresenter)
  fun inject(presenter: ProgramsPresenter)
}
