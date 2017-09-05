package ru.binaryblitz.justforyou.ui.main.support

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import kotlinx.android.synthetic.main.fragment_support.callSupport
import kotlinx.android.synthetic.main.fragment_support.facebook
import kotlinx.android.synthetic.main.fragment_support.instagram
import kotlinx.android.synthetic.main.fragment_support.reviewButton
import kotlinx.android.synthetic.main.fragment_support.sendSupport
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.components.Constants
import ru.binaryblitz.justforyou.ui.router.Router


class SupportFragment : MvpAppCompatFragment() {

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater!!.inflate(R.layout.fragment_support, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    callSupport.setOnClickListener { Router.dialPhoneNumber(activity, Constants.supportNumber) }
    sendSupport.setOnClickListener {
      sendEmail(Constants.email)
    }
    instagram.setOnClickListener {
      Router.openJustForYouLink(activity, "https://www.instagram.com/justforyou_ru/")
    }
    facebook.setOnClickListener {
      Router.openJustForYouLink(activity, "https://www.facebook.com/JUSTFORYOURUSSIA")
    }
    reviewButton.setOnClickListener { Router.openGooglePlayLink(activity) }
  }


  fun sendEmail(email: String) {
    val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
        "mailto", email, null))
    startActivity(Intent.createChooser(emailIntent, "Отправка сообщения менеджеру..."))
  }
}
