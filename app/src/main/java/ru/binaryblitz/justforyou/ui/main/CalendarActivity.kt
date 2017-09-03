package ru.binaryblitz.justforyou.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.activity_calendar.calendarCoordinator
import kotlinx.android.synthetic.main.activity_calendar.toolbar
import kotlinx.android.synthetic.main.content_calendar.calendarView
import kotlinx.android.synthetic.main.content_calendar.saveDaysButton
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.string
import ru.binaryblitz.justforyou.components.Extras
import java.util.Calendar

class CalendarActivity : AppCompatActivity(), OnDateSelectedListener {
  val currentDate = CalendarDay.today()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_calendar)
    initViewElements()
  }

  private fun initViewElements() {
    setSupportActionBar(toolbar)
    toolbar.title = getString(string.days_calendar_title)
    toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp)
    toolbar.setNavigationOnClickListener { onBackPressed() }
    calendarView.selectionMode = MaterialCalendarView.SELECTION_MODE_MULTIPLE
    calendarView.setOnDateChangedListener(this)
    saveDaysButton.setOnClickListener {
      val intent: Intent = Intent()
      val selectedDays: ArrayList<CalendarDay> = ArrayList()
      selectedDays.addAll(calendarView.selectedDates)
      intent.putExtra(Extras.EXTRA_DAYS_COUNT, calendarView.selectedDates.size)
      intent.putParcelableArrayListExtra(Extras.EXTRA_DAYS_DATA, selectedDays)
      setResult(Activity.RESULT_OK, intent)
      finish()
    }
  }

  override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
    val todayDate = Calendar.getInstance()
    val hour = todayDate.get(Calendar.HOUR_OF_DAY)
    if (widget.selectedDates.size > 0) {
      if (date.isAfter(currentDate)) {
        if (date.day == todayDate.get(Calendar.DAY_OF_MONTH) + 1 && hour >= 11) {
          Snackbar.make(calendarCoordinator, getString(string.hour_error),
              Snackbar.LENGTH_SHORT).show()
          widget.setDateSelected(date, false)
        }
        toolbar.title = String.format(getString(R.string.days_selected),
            widget.selectedDates.size)
      } else {
        Snackbar.make(calendarCoordinator, getString(R.string.passed_day_error),
            Snackbar.LENGTH_SHORT).show()
        widget.setDateSelected(date, false)
      }
    } else {
      toolbar.title = getString(string.days_calendar_title)
    }
  }
}
