package ru.binaryblitz.justforyou.ui.main.program_item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_program_item.programContainerView
import kotlinx.android.synthetic.main.fragment_program_item.programDescription
import kotlinx.android.synthetic.main.fragment_program_item.programImage
import kotlinx.android.synthetic.main.fragment_program_item.programPricePerDay
import kotlinx.android.synthetic.main.fragment_program_item.programPricePerWeek
import kotlinx.android.synthetic.main.fragment_program_item.programTitle
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.data.programs.Program
import ru.binaryblitz.justforyou.ui.router.Router

class ProgramItemFragment : MvpAppCompatFragment() {

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater?.inflate(R.layout.fragment_program_item, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    var program: Program = arguments.getParcelable(ARG_BLOCK)
    programTitle.text = program.name
    Picasso.with(activity).load(program.imageUrl).fit().centerCrop().into(programImage)
    programDescription.text = program.description
    programPricePerDay.text = "Цена за 1 день: " + program.primaryPrice.toString()
    programPricePerWeek.text = "При заказе от 10 дней: " + program.secondaryPrice.toString()
    programContainerView.setOnClickListener { Router.openDetailedProgramScreen(activity, program) }
  }

  companion object {
    val ARG_BLOCK: String = "block_item"

    fun getInstance(type: Program): ProgramItemFragment {
      val fragment = ProgramItemFragment()
      val args = Bundle()
      args.putParcelable(
          ARG_BLOCK, type)
      fragment.arguments = args
      return fragment
    }
  }
}
