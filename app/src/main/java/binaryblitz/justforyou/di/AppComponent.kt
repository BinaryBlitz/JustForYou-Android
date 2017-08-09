package binaryblitz.justforyou.di

import android.content.Context
import binaryblitz.justforyou.di.modules.ContextModule
import binaryblitz.justforyou.di.modules.NetworkModule
import binaryblitz.justforyou.network.NetworkService
import binaryblitz.justforyou.ui.login.LoginPresenter
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(ContextModule::class, NetworkModule::class))
interface AppComponent {
  val context: Context
  val networkService: NetworkService
  fun inject(presenter: LoginPresenter)
}
