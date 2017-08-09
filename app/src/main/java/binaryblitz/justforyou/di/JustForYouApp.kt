package binaryblitz.justforyou.di

import android.app.Application
import binaryblitz.justforyou.di.modules.ContextModule

class JustForYouApp : Application() {

  override fun onCreate() {
    super.onCreate()

    appComponent = DaggerAppComponent.builder()
        .contextModule(ContextModule(this))
        .build()
  }

  companion object {
    var appComponent: AppComponent? = null
  }
}
