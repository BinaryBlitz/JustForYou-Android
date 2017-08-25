package ru.binaryblitz.justforyou.di

import android.content.Context
import dagger.Component
import ru.binaryblitz.justforyou.di.modules.ContextModule
import ru.binaryblitz.justforyou.di.modules.NetworkModule
import ru.binaryblitz.justforyou.network.MapService
import ru.binaryblitz.justforyou.network.NetworkService
import ru.binaryblitz.justforyou.ui.login.LoginPresenter
import ru.binaryblitz.justforyou.ui.main.blocks.BlocksPresenter
import ru.binaryblitz.justforyou.ui.main.cart.CartPresenter
import ru.binaryblitz.justforyou.ui.main.delivery_addresses.PlacesPresenter
import ru.binaryblitz.justforyou.ui.main.map.MapAddressPresenter
import ru.binaryblitz.justforyou.ui.main.program_item.ProgramsPresenter
import ru.binaryblitz.justforyou.ui.main.program_item.detailed_program.pages.menu.MenuPresenter
import ru.binaryblitz.justforyou.ui.main.settings.SettingsActivity
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(ContextModule::class, NetworkModule::class))
interface AppComponent {
  val context: Context
  val networkService: NetworkService
  val mapService: MapService
  fun inject(presenter: LoginPresenter)
  fun inject(presenter: BlocksPresenter)
  fun inject(presenter: ProgramsPresenter)
  fun inject(presenter: MenuPresenter)
  fun inject(settings: SettingsActivity)
  fun inject(presenter: CartPresenter)
  fun inject(presenter: MapAddressPresenter)
  fun inject(presenter: PlacesPresenter)
}
