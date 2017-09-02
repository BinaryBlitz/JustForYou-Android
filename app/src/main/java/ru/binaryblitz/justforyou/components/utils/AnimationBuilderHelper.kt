package ru.binaryblitz.justforyou.components.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import com.roughike.bottombar.BottomBar

val ANIM_DURATION_TOOLBAR: Long = 300
val TOOLBAR_SIZE: Int = 56

object AnimationBuilderHelper {

  fun initMainView(context: Context, toolbar: View, bottomBar: BottomBar) {
    startViewAnim(context, bottomBar, false)
    startViewAnim(context, toolbar, true)
  }

  fun startViewAnim(context: Context, view: View, positiveDirection: Boolean) {
    var viewSize = dpToPx(context, TOOLBAR_SIZE)
    var animPosition: Float
    if (positiveDirection) {
      viewSize = -viewSize
    }
    view.translationY = viewSize.toFloat()

    if (positiveDirection) animPosition = view.y - viewSize else animPosition = 0f
    view.animate()
        .translationY(animPosition)
        .setDuration(ANIM_DURATION_TOOLBAR).startDelay = ANIM_DURATION_TOOLBAR
  }

  private fun dpToPx(context: Context, dp: Int): Int {
    val displayMetrics = context.resources.displayMetrics
    val px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    return px
  }

}
