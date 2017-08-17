package ru.binaryblitz.justforyou.ui.main.program_item.detailed_program.pages.menu

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import kotlinx.android.synthetic.main.fragment_menu.menuProgressBarView
import kotlinx.android.synthetic.main.fragment_menu.menuRefreshContainer
import kotlinx.android.synthetic.main.fragment_menu.menuView
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.data.menu.Menu
import ru.binaryblitz.justforyou.data.programs.Program


class MenuFragment : MvpAppCompatFragment(), MenuView, OnRefreshListener {
  @InjectPresenter(type = PresenterType.LOCAL)
  lateinit var presenter: MenuPresenter
//  lateinit var adapter: ProgramAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_menu, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initViewElements(view)
    presenter.getMenu(arguments.getParcelable<Program>(ARG_PROGRAM).id!!)
  }

  fun initViewElements(view: View) {
    menuRefreshContainer.setOnRefreshListener(this)

//    adapter = ProgramAdapter()
//    adapter.onItemSelectAction.subscribe { block -> Router.openProgramScreen(activity, block) }
//    menuView.layoutManager = LinearLayoutManager(activity)
//    menuView.adapter = adapter
  }

  override fun showMenu(menu: List<Menu>) {
    val a = 5
  }

  override fun showProgress() {
    menuView.visibility = View.GONE
    menuProgressBarView.visibility = View.VISIBLE
    menuRefreshContainer.isRefreshing = false
  }

  override fun hideProgress() {
    menuView.visibility = View.VISIBLE
    menuProgressBarView.visibility = View.GONE
    menuRefreshContainer.isRefreshing = false
  }

  override fun showError(message: String) {
    Snackbar.make(menuRefreshContainer, message, Snackbar.LENGTH_SHORT).show()
  }

  override fun onRefresh() {
    presenter.getMenu(arguments.getParcelable<Program>(ARG_PROGRAM).id!!)
  }

  companion object {
    val ARG_PROGRAM: String = "program_item"

    fun getInstance(type: Program): MenuFragment {
      val fragment = MenuFragment()
      val args = Bundle()
      args.putParcelable(
          ARG_PROGRAM, type)
      fragment.arguments = args
      return fragment
    }
  }
}
//
//class ProgramAdapter : BaseRecyclerAdapter<Block>() {
//  var onItemSelectAction: PublishSubject<Block> = PublishSubject.create()
//
//  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder<Block> {
//    val view = LayoutInflater.from(parent.context).inflate(R.layout.program_item, parent, false)
//    return ViewHolder(view)
//  }
//
//  private inner class ViewHolder(itemView: View) : RecyclerViewHolder<Block>(itemView) {
//
//    override fun setItem(item: Block, position: Int) {
//      Picasso.with(itemView.context).load(item.imageUrl).fit().into(itemView.programImage)
//      itemView.programTitle.text = item.name
//      //TODO Обработка программ/программы
//      itemView.programsCount.text = "" + item.programsCount + " Программ"
//      itemView.setOnClickListener { onItemSelectAction.onNext(getItemAt(position)) }
//    }
//
//  }
//}
