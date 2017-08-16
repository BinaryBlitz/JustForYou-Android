package ru.binaryblitz.justforyou.ui.main.blocks

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.squareup.picasso.Picasso
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_programs.programsRefreshContainer
import kotlinx.android.synthetic.main.fragment_programs.programsView
import kotlinx.android.synthetic.main.fragment_programs.progressBarView
import kotlinx.android.synthetic.main.program_item.view.programImage
import kotlinx.android.synthetic.main.program_item.view.programTitle
import kotlinx.android.synthetic.main.program_item.view.programsCount
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.data.programs.Block
import ru.binaryblitz.justforyou.ui.base.BaseRecyclerAdapter
import ru.binaryblitz.justforyou.ui.router.Router


/**
 * Fragment view that contains the logic for displaying list of food delivery blocks
 */
class ProgramsFragment : MvpAppCompatFragment(), BlocksView, OnRefreshListener {
  @InjectPresenter(type = PresenterType.LOCAL)
  lateinit var presenter: BlocksPresenter
  lateinit var adapter: ProgramAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_programs, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initViewElements(view)
    presenter.getFoodBlocks()
  }

  fun initViewElements(view: View) {
    programsRefreshContainer.setOnRefreshListener(this)

    adapter = ProgramAdapter()
    adapter.onItemSelectAction.subscribe { block -> Router.openProgramScreen(activity, block) }
    programsView.layoutManager = LinearLayoutManager(activity)
    programsView.adapter = adapter

  }

  override fun showBlocks(blocks: List<Block>) {
    adapter.setData(blocks)
  }

  override fun showProgress() {
    programsView.visibility = View.GONE
    progressBarView.visibility = View.VISIBLE
    programsRefreshContainer.isRefreshing = false
  }

  override fun hideProgress() {
    programsView.visibility = View.VISIBLE
    progressBarView.visibility = View.GONE
    programsRefreshContainer.isRefreshing = false
  }

  override fun showError(message: String) {
    Snackbar.make(programsRefreshContainer, message, Snackbar.LENGTH_SHORT).show()
  }

  override fun onRefresh() {
    presenter.getFoodBlocks()
  }

  companion object {
    fun getInstance(): ProgramsFragment {
      val fragment: ProgramsFragment = ProgramsFragment()
      return fragment
    }
  }
}

class ProgramAdapter : BaseRecyclerAdapter<Block>() {
  var onItemSelectAction: PublishSubject<Block> = PublishSubject.create()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder<Block> {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.program_item, parent, false)
    return ViewHolder(view)
  }

  private inner class ViewHolder(itemView: View) : RecyclerViewHolder<Block>(itemView) {

    override fun setItem(item: Block, position: Int) {
      Picasso.with(itemView.context).load(item.imageUrl).fit().into(itemView.programImage)
      itemView.programTitle.text = item.name
      //TODO Обработка программ/программы
      itemView.programsCount.text = "" + item.programsCount + " Программ"
      itemView.setOnClickListener { onItemSelectAction.onNext(getItemAt(position)) }
    }

  }
}
