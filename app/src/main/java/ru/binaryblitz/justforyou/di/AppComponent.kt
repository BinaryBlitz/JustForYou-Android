package ru.binaryblitz.justforyou.di

import android.content.Context
import dagger.Component
import ru.binaryblitz.justforyou.data.cart.ProgramsStorage
import ru.binaryblitz.justforyou.data.user.UserStorageImpl
import ru.binaryblitz.justforyou.di.modules.ApplicationModule
import ru.binaryblitz.justforyou.di.modules.ContextModule
import ru.binaryblitz.justforyou.network.MapService
import ru.binaryblitz.justforyou.network.NetworkService
import ru.binaryblitz.justforyou.ui.base.BaseCartActivity
import ru.binaryblitz.justforyou.ui.login.LoginPresenter
import ru.binaryblitz.justforyou.ui.main.MainActivity
import ru.binaryblitz.justforyou.ui.main.blocks.BlocksPresenter
import ru.binaryblitz.justforyou.ui.main.cart.CartPresenter
import ru.binaryblitz.justforyou.ui.main.deliveries.DeliveriesPresenter
import ru.binaryblitz.justforyou.ui.main.delivery_addresses.PlacesPresenter
import ru.binaryblitz.justforyou.ui.main.map.MapAddressPresenter
import ru.binaryblitz.justforyou.ui.main.program_item.CartProgramPresenter
import ru.binaryblitz.justforyou.ui.main.program_item.ProgramsPresenter
import ru.binaryblitz.justforyou.ui.main.program_item.detailed_program.pages.menu.MenuPresenter
import ru.binaryblitz.justforyou.ui.main.promotions.PromotionsPresenter
import ru.binaryblitz.justforyou.ui.main.purchases.PurchasesPresenter
import ru.binaryblitz.justforyou.ui.main.settings.SettingsActivity
import ru.binaryblitz.justforyou.ui.main.settings.payment_cards.PaymentCardsPresenter
import ru.binaryblitz.justforyou.ui.main.substitutions.SubstitutionsPresenter
import ru.binaryblitz.justforyou.ui.main.substitutions.products.ProductsPresenter
import ru.binaryblitz.justforyou.ui.main.user_orders.UserOrdersPresenter
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(ContextModule::class, ApplicationModule::class))
interface AppComponent {
  val context: Context
  val networkService: NetworkService
  val mapService: MapService
  val cartProgramsStorage: ProgramsStorage
  val profileStorage: UserStorageImpl
  fun inject(mainScreen: MainActivity)
  fun inject(presenter: LoginPresenter)
  fun inject(presenter: BlocksPresenter)
  fun inject(presenter: ProgramsPresenter)
  fun inject(presenter: MenuPresenter)
  fun inject(settings: SettingsActivity)
  fun inject(presenter: CartPresenter)
  fun inject(presenter: MapAddressPresenter)
  fun inject(presenter: PlacesPresenter)
  fun inject(presenter: PurchasesPresenter)
  fun inject(presenter: UserOrdersPresenter)
  fun inject(presenter: PaymentCardsPresenter)
  fun inject(presenter: DeliveriesPresenter)
  fun inject(baseCartScreen: BaseCartActivity)
  fun inject(orderPresenter: CartProgramPresenter)
  fun inject(promotionsPresenter: PromotionsPresenter)
  fun inject(subsPresenter: SubstitutionsPresenter)
  fun inject(productsPresenter: ProductsPresenter)
}
