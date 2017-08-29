package ru.binaryblitz.justforyou.ui.main.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_order.toolbar
import kotlinx.android.synthetic.main.content_order.addressContainer
import kotlinx.android.synthetic.main.content_order.addressText
import kotlinx.android.synthetic.main.content_order.blockTitle
import kotlinx.android.synthetic.main.content_order.calendarContainer
import kotlinx.android.synthetic.main.content_order.programTitle
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.string
import ru.binaryblitz.justforyou.components.Extras
import ru.binaryblitz.justforyou.data.programs.Program
import ru.binaryblitz.justforyou.ui.router.Router


class OrderActivity : AppCompatActivity() {
  val MAP_BUTTON_REQUEST_CODE = 1
  lateinit var program: Program
  lateinit var blockName: String

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_order)

    initViewElements()
  }

  private fun initViewElements() {
    setSupportActionBar(toolbar)
    toolbar.title = getString(string.new_order)
    toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp)
    toolbar.setNavigationOnClickListener { onBackPressed() }

    program = intent.getParcelableExtra(Extras.EXTRA_PROGRAM)
    blockName = intent.getStringExtra(Extras.EXTRA_PROGRAM_BLOCK_NAME)

    programTitle.text = program.name
    blockTitle.text = blockName

    addressContainer.setOnClickListener {
      Router.openPlacesScreen(this, MAP_BUTTON_REQUEST_CODE)
    }
    calendarContainer.setOnClickListener {
      Router.openCalendarScreen(this)
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == Activity.RESULT_OK && requestCode == MAP_BUTTON_REQUEST_CODE) {
      addressText.text = data?.getStringExtra(Extras.EXTRA_ADDRESS_TEXT)
    }
  }
}
