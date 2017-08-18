package ru.binaryblitz.justforyou.di

import android.app.Application
import io.realm.Realm
import ru.binaryblitz.justforyou.di.modules.ContextModule

class JustForYouApp : Application() {

  override fun onCreate() {
    super.onCreate()

    appComponent = DaggerAppComponent.builder()
        .contextModule(ContextModule(this))
        .build()

    Realm.init(this)

  }

  companion object {
    var appComponent: AppComponent? = null
  }
}
