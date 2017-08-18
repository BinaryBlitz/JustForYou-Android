package ru.binaryblitz.justforyou.ui.router

import android.app.Activity
import android.content.Intent
import ru.binaryblitz.justforyou.components.Extras
import ru.binaryblitz.justforyou.data.programs.Block
import ru.binaryblitz.justforyou.data.programs.Program
import ru.binaryblitz.justforyou.ui.login.LoginActivity
import ru.binaryblitz.justforyou.ui.login.onboarding.OnBoardingActivity
import ru.binaryblitz.justforyou.ui.main.MainActivity
import ru.binaryblitz.justforyou.ui.main.cart.CartActivity
import ru.binaryblitz.justforyou.ui.main.program_item.ProgramsActivity
import ru.binaryblitz.justforyou.ui.main.program_item.detailed_program.DetailedProgramActivity

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

  fun openDetailedProgramScreen(context: Activity, program: Program) {
    val intent = Intent(context, DetailedProgramActivity::class.java)
    intent.putExtra(Extras.EXTRA_PROGRAM, program)
    context.startActivity(intent)
  }

  fun openCartScreen(context: Activity) {
    val intent = Intent(context, CartActivity::class.java)
    context.startActivity(intent)
  }

}
