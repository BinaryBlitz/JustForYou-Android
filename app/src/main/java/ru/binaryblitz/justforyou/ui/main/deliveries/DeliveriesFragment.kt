package ru.binaryblitz.justforyou.ui.main.deliveries

import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.content_deliveries.deliveriesDayList
import kotlinx.android.synthetic.main.content_deliveries.deliveriesView
import kotlinx.android.synthetic.main.content_deliveries.expandButton
import kotlinx.android.synthetic.main.content_deliveries.swipeCalendar
import kotlinx.android.synthetic.main.delivery_item.view.deliveryAddress
import kotlinx.android.synthetic.main.delivery_item.view.programName
import kotlinx.android.synthetic.main.delivery_item.view.timeDeliveryText
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.color
import ru.binaryblitz.justforyou.components.utils.DateUtils
import ru.binaryblitz.justforyou.network.responses.deliveries.Delivery
import ru.binaryblitz.justforyou.ui.base.BaseRecyclerAdapter


/**
 * Fragment view that contains the calendar of deliveries
 */
class DeliveriesFragment : MvpAppCompatFragment(), DeliveriesView, OnRefreshListener{
  @InjectPresenter()
  lateinit var presenter: DeliveriesPresenter
  var adapter: DeliveriesAdapter = DeliveriesAdapter()
  private var isCalendarExpanded = true

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
    swipeCalendar.setOnRefreshListener(this)
    deliveriesDayList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,
        true)
    expandButton.setOnClickListener { if (isCalendarExpanded) hideCalendar() else showCalendar() }
    deliveriesView.topbarVisible = false
  }

  override fun showDeliveries(deliveries: List<Delivery>) {
    val handler = Handler()
    handler.postDelayed(object : Runnable {
      override fun run() {
        // Workaround to stop UI-freezing at rendering diffucult calendar
        renderCalendar(deliveries)
      }
    }, 1000)
    deliveriesDayList.adapter = adapter
    adapter.setData(deliveries.sortedByDescending { it.id })
  }

  private fun renderCalendar(
      deliveries: List<Delivery>) {
    for ((position) in deliveries.withIndex()) {
      deliveriesView.setDateSelected(
          DateUtils.parseServerDate(deliveries[position].scheduledFor!!), true)
      deliveriesView.selectionColor = activity.resources.getColor(color.primary_light)
      hideProgress()
    }
  }

  override fun showProgress() {
    swipeCalendar.isRefreshing = true
  }

  override fun hideProgress() {
    swipeCalendar.isRefreshing = false
  }

  override fun showError(message: String) {
  }

  override fun onRefresh() {
    presenter.getDeliveries()
  }

  fun showCalendar() {
    deliveriesView.visibility = View.VISIBLE
    expandButton.setImageResource(R.drawable.ic_expand_less_black_24dp)
    isCalendarExpanded = true
  }

  fun hideCalendar() {
    deliveriesView.visibility = View.GONE
    expandButton.setImageResource(R.drawable.ic_expand_more_black_24dp)
    isCalendarExpanded = false
  }


  companion object {
    fun getInstance(): DeliveriesFragment {
      val fragment: DeliveriesFragment = DeliveriesFragment()
      return fragment
    }
  }
}

class DeliveriesAdapter : BaseRecyclerAdapter<Delivery>() {
  var onItemSelectAction: PublishSubject<Delivery> = PublishSubject.create()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder<Delivery> {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.delivery_item, parent, false)
    return ViewHolder(view)
  }

  private inner class ViewHolder(itemView: View) : RecyclerViewHolder<Delivery>(itemView) {

    override fun setItem(item: Delivery, position: Int) {
      itemView.timeDeliveryText.text = DateUtils.parseServerDate(
          item.scheduledFor!!).toLocaleString()
      itemView.programName.text = item.purchase?.program?.name
      itemView.deliveryAddress.text = item.address?.content
    }

  }
}
