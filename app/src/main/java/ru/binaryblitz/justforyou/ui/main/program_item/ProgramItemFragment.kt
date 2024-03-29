package ru.binaryblitz.justforyou.ui.main.program_item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detailed_program.proceedProgramButton
import kotlinx.android.synthetic.main.fragment_program_item.programContainerView
import kotlinx.android.synthetic.main.fragment_program_item.programDescription
import kotlinx.android.synthetic.main.fragment_program_item.programImage
import kotlinx.android.synthetic.main.fragment_program_item.programPricePerDay
import kotlinx.android.synthetic.main.fragment_program_item.programPricePerWeek
import kotlinx.android.synthetic.main.fragment_program_item.programTitle
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.string
import ru.binaryblitz.justforyou.components.Constants
import ru.binaryblitz.justforyou.components.Extras
import ru.binaryblitz.justforyou.data.programs.Program
import ru.binaryblitz.justforyou.ui.router.Router

class ProgramItemFragment : MvpAppCompatFragment() {
  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater?.inflate(R.layout.fragment_program_item, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val program: Program = arguments.getParcelable(ARG_BLOCK)
    val cartProgramPresenter = CartProgramPresenter(activity)
    programTitle.text = program.name
    Picasso.with(activity).load(program.imageUrl).placeholder(
        R.drawable.program_placeholder).fit().centerCrop().into(programImage)
    programDescription.text = program.description
    programPricePerDay.text = getString(R.string.per_one_day) + "${program.primaryPrice}"
    programPricePerWeek.text = getString(R.string.per_ten_days) + "${program.secondaryPrice}"
    if (program.individualPrice) {
      programPricePerDay.text = getString(string.individual_price)
      programPricePerWeek.visibility = View.GONE
      proceedProgramButton.text = getString(string.call_manager)
    }
    programContainerView.setOnClickListener {
      Router.openDetailedProgramScreen(activity, program, ProgramsActivity.blockName)
    }
    proceedProgramButton.setOnClickListener {
      if (program.individualPrice) {
        Router.dialPhoneNumber(activity, Constants.supportNumber)
        return@setOnClickListener
      }
      Router.openOrderScreen(activity, program, ProgramsActivity.blockName, Extras.orderRequestCode)
    }
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
