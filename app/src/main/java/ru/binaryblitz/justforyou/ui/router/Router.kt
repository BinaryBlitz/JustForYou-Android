package ru.binaryblitz.justforyou.ui.router

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.components.Extras
import ru.binaryblitz.justforyou.data.programs.Block
import ru.binaryblitz.justforyou.data.programs.Program
import ru.binaryblitz.justforyou.ui.login.LoginActivity
import ru.binaryblitz.justforyou.ui.login.onboarding.OnBoardingActivity
import ru.binaryblitz.justforyou.ui.main.CalendarActivity
import ru.binaryblitz.justforyou.ui.main.MainActivity
import ru.binaryblitz.justforyou.ui.main.cart.CartActivity
import ru.binaryblitz.justforyou.ui.main.delivery_addresses.DeliveryPlacesActivity
import ru.binaryblitz.justforyou.ui.main.map.MapAddressActivity
import ru.binaryblitz.justforyou.ui.main.order.OrderActivity
import ru.binaryblitz.justforyou.ui.main.program_item.ProgramsActivity
import ru.binaryblitz.justforyou.ui.main.program_item.detailed_program.DetailedProgramActivity
import ru.binaryblitz.justforyou.ui.main.purchases.PurchasesActivity
import ru.binaryblitz.justforyou.ui.main.settings.SettingsActivity
import ru.binaryblitz.justforyou.ui.main.settings.payment_cards.PaymentCardsActivity
import ru.binaryblitz.justforyou.ui.main.user_orders.UserOrdersActivity
import ru.binaryblitz.justforyou.ui.main.web_payment.PaymentActivity


/**
 * Class that holds all screen navigation and transitions.
 */
object Router {
  fun openMainScreen(context: Activity) {
    val intent = Intent(context, MainActivity::class.java)
    context.finish()
    context.startActivity(intent)
  }

  fun openOnboardingScreen(context: Activity) {
    val intent = Intent(context, OnBoardingActivity::class.java)
    context.finish()
    context.startActivity(intent)
  }

  fun openStartScreen(context: Activity) {
    val intent = Intent(context, LoginActivity::class.java)
    context.startActivity(intent)
  }

  fun openProgramScreen(context: Activity, block: Block) {
    val intent = Intent(context, ProgramsActivity::class.java)
    intent.putExtra(Extras.EXTRA_BLOCK, block)
    context.startActivity(intent)
  }

  fun openDetailedProgramScreen(context: Activity, program: Program, block: String) {
    val intent = Intent(context, DetailedProgramActivity::class.java)
    intent.putExtra(Extras.EXTRA_PROGRAM, program)
    intent.putExtra(Extras.EXTRA_PROGRAM_BLOCK_NAME, block)
    context.startActivity(intent)
  }

  fun openSettingsScreen(context: Activity) {
    val intent = Intent(context, SettingsActivity::class.java)
    context.startActivity(intent)
  }

  fun openJustForYouLink(context: Activity, link: String) {
    val uri = Uri.parse(link)
    val intentBuilder = CustomTabsIntent.Builder()

    intentBuilder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
    intentBuilder.setSecondaryToolbarColor(
        ContextCompat.getColor(context, R.color.colorPrimaryDark))
    val customTabsIntent = intentBuilder.build()

    customTabsIntent.launchUrl(context, uri)
  }

  fun openCartScreen(context: Activity) {
    val intent = Intent(context, CartActivity::class.java)
    context.startActivity(intent)
  }

  fun openOrderScreen(context: Activity, program: Program, block: String, requestCode: Int) {
    val intent = Intent(context, OrderActivity::class.java)
    intent.putExtra(Extras.EXTRA_PROGRAM, program)
    intent.putExtra(Extras.EXTRA_PROGRAM_BLOCK_NAME, block)
    context.startActivityForResult(intent, requestCode)
  }

  fun openMapScreen(context: Activity, requestCode: Int) {
    val intent = Intent(context, MapAddressActivity::class.java)
    context.startActivityForResult(intent, requestCode)
  }

  fun openPlacesScreen(context: Activity, requestCode: Int) {
    val intent = Intent(context, DeliveryPlacesActivity::class.java)
    context.startActivityForResult(intent, requestCode)
  }

  fun openPurchasesScreen(context: Activity) {
    val intent = Intent(context, PurchasesActivity::class.java)
    context.startActivity(intent)
  }

  fun openOrdersScreen(context: Activity) {
    val intent = Intent(context, UserOrdersActivity::class.java)
    context.startActivity(intent)
  }

  fun openCalendarScreen(context: Activity, requestCode: Int) {
    val intent = Intent(context, CalendarActivity::class.java)
    context.startActivityForResult(intent, requestCode)
  }

  fun openPaymentCardsScreen(context: Activity) {
    val intent = Intent(context, PaymentCardsActivity::class.java)
    context.startActivity(intent)
  }

  fun openPaymentScreen(context: Activity, link: String, requestCode: Int) {
    val intent = Intent(context, PaymentActivity::class.java)
    intent.putExtra(Extras.EXTRA_URL, link)
    context.startActivityForResult(intent, requestCode)
  }

  fun openMainScreenAfterLogout(context: Activity) {
    val intent = Intent(context, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
    context.finish()
    context.startActivity(intent)
  }

  fun openGooglePlayLink(context: Activity) {
    val uri = Uri.parse("market://details?id=" + context.packageName)
    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
        Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
        Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
    try {
      context.startActivity(goToMarket)
    } catch (e: ActivityNotFoundException) {
      context.startActivity(Intent(Intent.ACTION_VIEW,
          Uri.parse("http://play.google.com/store/apps/details?id=" + context.packageName)))
    }

  }

}
