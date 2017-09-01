package ru.binaryblitz.justforyou.ui.main.web_payment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_order.toolbar
import kotlinx.android.synthetic.main.content_payment.paymentWebView
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.string
import ru.binaryblitz.justforyou.components.Extras


private val succeedPaymentUrl = "https://form.payonlinesystem.com/default/draft/ok"

class PaymentActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_payment)
    initViewElements(intent.getStringExtra(Extras.EXTRA_URL))
  }

  @SuppressLint("SetJavaScriptEnabled")
  private fun initViewElements(url: String) {
    setSupportActionBar(toolbar)
    toolbar.title = getString(string.payment_screen_title)
    toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp)
    toolbar.setNavigationOnClickListener { onBackPressed() }
    paymentWebView.settings.javaScriptEnabled = true
    paymentWebView.loadUrl(url)
    paymentWebView.webViewClient = object : WebViewClient() {
      override fun shouldOverrideUrlLoading(wView: WebView, url: String): Boolean {
        return isUrlPaymentSucceed(url)
      }
    }
  }

  override fun onBackPressed() {
    if (!isUrlPaymentSucceed(paymentWebView.url)) {
      super.onBackPressed()
    }
  }

  private fun isUrlPaymentSucceed(url: String): Boolean {
    if (url == succeedPaymentUrl || url.contains("success")) {
      setResult(Activity.RESULT_OK)
      finish()
      return true
    } else {
      return false
    }
  }
}
