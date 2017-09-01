package ru.binaryblitz.justforyou.ui.main.deliveries

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.content_deliveries.deliveriesView
import kotlinx.android.synthetic.main.content_deliveries.swipeCalendar
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.components.utils.DateUtils
import ru.binaryblitz.justforyou.data.programs.Program
import ru.binaryblitz.justforyou.network.responses.deliveries.Delivery
import ru.binaryblitz.justforyou.ui.base.BaseRecyclerAdapter


/**
 * Fragment view that contains the calendar of deliveries
 */
class DeliveriesFragment : MvpAppCompatFragment(), DeliveriesView, OnRefreshListener, OnDateSelectedListener {
  @InjectPresenter(type = PresenterType.LOCAL)
  lateinit var presenter: DeliveriesPresenter
  lateinit var adapter: ProgramAdapter
  private val decorator: DayDecorator = DayDecorator()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.content_deliveries, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initViewElements(view)
    presenter.getDeliveries()
  }

  fun initViewElements(view: View) {
    deliveriesView.selectionMode = MaterialCalendarView.SELECTION_MODE_NONE
    deliveriesView.setOnDateChangedListener(this)
    swipeCalendar.setOnRefreshListener (this)
  }

  override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
  }

  override fun showDeliveries(deliveries: List<Delivery>) {
    for ((position) in deliveries.withIndex()) {
      deliveriesView.setDateSelected(
          DateUtils.parseServerDate(deliveries[position].scheduledFor!!), true)
      deliveriesView.selectionColor = activity.resources.getColor(R.color.primary_light)
    }

  }

  override fun showProgress() {
    swipeCalendar.isRefreshing = false
  }

  override fun hideProgress() {
    swipeCalendar.isRefreshing = false
  }

  override fun showError(message: String) {
  }

  override fun onRefresh() {
    presenter.getDeliveries()
  }

  companion object {
    fun getInstance(): DeliveriesFragment {
      val fragment: DeliveriesFragment = DeliveriesFragment()
      return fragment
    }
  }
}

class ProgramAdapter : BaseRecyclerAdapter<Program>() {
  var onItemSelectAction: PublishSubject<Program> = PublishSubject.create()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder<Program> {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.program_item, parent, false)
    return ViewHolder(view)
  }

  private inner class ViewHolder(itemView: View) : RecyclerViewHolder<Program>(itemView) {

    override fun setItem(item: Program, position: Int) {
    }

  }
}
