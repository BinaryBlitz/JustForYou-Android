package ru.binaryblitz.justforyou.ui.main.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetBehavior.BottomSheetCallback
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.activity_order.numberPicker
import kotlinx.android.synthetic.main.activity_order.orderCoordinator
import kotlinx.android.synthetic.main.activity_order.saveDeliveryTimeButton
import kotlinx.android.synthetic.main.activity_order.timePickerAlertSheet
import kotlinx.android.synthetic.main.activity_order.toolbar
import kotlinx.android.synthetic.main.content_order.addToCartButton
import kotlinx.android.synthetic.main.content_order.addressContainer
import kotlinx.android.synthetic.main.content_order.addressText
import kotlinx.android.synthetic.main.content_order.blockTitle
import kotlinx.android.synthetic.main.content_order.calendarContainer
import kotlinx.android.synthetic.main.content_order.daysText
import kotlinx.android.synthetic.main.content_order.deliveryTimeContainer
import kotlinx.android.synthetic.main.content_order.deliveryTimeText
import kotlinx.android.synthetic.main.content_order.programTitle
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.string
import ru.binaryblitz.justforyou.components.Constants
import ru.binaryblitz.justforyou.components.Extras
import ru.binaryblitz.justforyou.components.utils.DateUtils
import ru.binaryblitz.justforyou.data.programs.Program
import ru.binaryblitz.justforyou.network.responses.orders.DeliveriesItem
import ru.binaryblitz.justforyou.ui.main.program_item.CartProgramPresenter
import ru.binaryblitz.justforyou.ui.router.Router


class OrderActivity : AppCompatActivity() {
  val MAP_BUTTON_REQUEST_CODE = 1
  val CALENDAR_BUTTON_REQUEST_CODE = 2

  lateinit var program: Program
  lateinit var blockName: String

  lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
  lateinit var cartProgramPresenter: CartProgramPresenter

  var selectedDays: ArrayList<CalendarDay> = ArrayList()
  var address: Int? = 0
  var deliveryTime: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_order)

    cartProgramPresenter = CartProgramPresenter(this)
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
      Router.openCalendarScreen(this, CALENDAR_BUTTON_REQUEST_CODE)
    }

    bottomSheetBehavior = BottomSheetBehavior.from(timePickerAlertSheet as View)
    bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetCallback() {
      override fun onSlide(bottomSheet: View, slideOffset: Float) {
      }

      override fun onStateChanged(bottomSheet: View, newState: Int) {
        if (newState == BottomSheetBehavior.STATE_HIDDEN) {
        }
      }
    })

    deliveryTimeContainer.setOnClickListener { showTimePicker() }
    hideTimePicker(false)
    numberPicker.minValue = Constants.MIN_DELIVERY_TIMES
    numberPicker.maxValue = Constants.MAX_DELIVERY_TIMES
    numberPicker.displayedValues = resources.getStringArray(R.array.delivery_time_array)

    saveDeliveryTimeButton.setOnClickListener { hideTimePicker(true) }
    addToCartButton.setOnClickListener {
      if (isOrderInfoFilledCorrectly()) {
        // Do some stuff with converting selectedDays to deliveryDays...
        var deliveryDays: ArrayList<DeliveriesItem> = ArrayList()
        for ((position) in selectedDays.withIndex()) {
          var deliveryItem: DeliveriesItem = DeliveriesItem()
          deliveryItem.addressId = address!!
          deliveryItem.scheduledFor = DateUtils.convertToServerDate(selectedDays[position].date)
          deliveryItem.comment = ""
          deliveryDays.add(deliveryItem)
        }
        cartProgramPresenter.addProgramToCart(blockName, program, selectedDays.size, deliveryDays)
      } else {
        Snackbar.make(orderCoordinator, getString(string.order_info_error),
            Snackbar.LENGTH_SHORT).show()
      }
    }
  }

  private fun isOrderInfoFilledCorrectly(): Boolean {
    if (address != 0 && selectedDays.isNotEmpty() && deliveryTime != null) {
      return true
    }
    return false
  }

  fun showTimePicker() {
    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
  }

  fun hideTimePicker(isSelected: Boolean) {
    if (isSelected) {
      deliveryTime = numberPicker.displayedValues[numberPicker.value - 1]
      deliveryTimeText.text = deliveryTime
    }
    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == Activity.RESULT_OK) {
      when (requestCode) {
        MAP_BUTTON_REQUEST_CODE -> {
          addressText.text = data?.getStringExtra(Extras.EXTRA_ADDRESS_TEXT)
          address = data?.getIntExtra(Extras.EXTRA_ADDRESS_ID, 0)
        }
        CALENDAR_BUTTON_REQUEST_CODE -> {
          daysText.text = String.format(
              getString(R.string.days_selected, data?.getIntExtra(Extras.EXTRA_DAYS_COUNT, 0)))
          selectedDays = data?.getParcelableArrayListExtra<CalendarDay>(Extras.EXTRA_DAYS_DATA)!!
        }
      }
    }
  }
}
