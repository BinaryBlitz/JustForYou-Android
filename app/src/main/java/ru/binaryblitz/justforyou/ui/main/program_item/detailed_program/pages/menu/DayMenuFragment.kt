package ru.binaryblitz.justforyou.ui.main.program_item.detailed_program.pages.menu

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_day.menuCaloriesCount
import kotlinx.android.synthetic.main.fragment_day.menuDayNumber
import kotlinx.android.synthetic.main.fragment_day.menuView
import kotlinx.android.synthetic.main.menu_item.view.menuItemCaloriesCount
import kotlinx.android.synthetic.main.menu_item.view.menuItemName
import kotlinx.android.synthetic.main.menu_item.view.menuItemTime
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.string
import ru.binaryblitz.justforyou.data.menu.Menu
import ru.binaryblitz.justforyou.data.menu.MenuItem
import ru.binaryblitz.justforyou.ui.base.BaseRecyclerAdapter

class DayMenuFragment : MvpAppCompatFragment() {
  lateinit var adapter: MenuAdapter

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater?.inflate(R.layout.fragment_day, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initViewElements()
  }

  private fun initViewElements() {
    var menu: Menu = arguments.getParcelable(ARG_DAY)
    val dayPosition = arguments.getInt(ARG_POSITION) + 1
    menuDayNumber.text = "$dayPosition " + getString(string.day_text)
    menuCaloriesCount.text = "${menu.calories}" + getString(string.calories_text)
    adapter = MenuAdapter()
    menuView.layoutManager = LinearLayoutManager(activity)
    menuView.adapter = adapter
    adapter.setData(menu.items!!)
  }

  companion object {
    // Argument for day menu item parcelable extra
    val ARG_DAY: String = "day_item"
    // Number of current day in menu
    val ARG_POSITION: String = "position_item"

    fun getInstance(type: Menu, position: Int): DayMenuFragment {
      val fragment = DayMenuFragment()
      val args = Bundle()
      args.putParcelable(
          ARG_DAY, type)
      args.putInt(ARG_POSITION, position)
      fragment.arguments = args
      return fragment
    }
  }


  class MenuAdapter : BaseRecyclerAdapter<MenuItem>() {
    var onItemSelectAction: PublishSubject<MenuItem> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup,
        viewType: Int): RecyclerViewHolder<MenuItem> {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
      return ViewHolder(view)
    }

    private inner class ViewHolder(itemView: View) : RecyclerViewHolder<MenuItem>(itemView) {

      override fun setItem(item: MenuItem, position: Int) {
        itemView.menuItemName.text = item.content
        itemView.menuItemCaloriesCount.text = "${item.calories} " + itemView.context.getString(
            R.string.calories_text)
        itemView.menuItemTime.text = "${item.startsAt} - ${item.endsAt}"
      }

    }
  }
}
