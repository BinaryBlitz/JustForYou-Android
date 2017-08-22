package ru.binaryblitz.justforyou.ui.main.program_item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detailed_program.numberPicker
import kotlinx.android.synthetic.main.activity_detailed_program.proceedProgramButton
import kotlinx.android.synthetic.main.fragment_program_item.addToCartButton
import kotlinx.android.synthetic.main.fragment_program_item.closeAlert
import kotlinx.android.synthetic.main.fragment_program_item.dayPickerAlertSheet
import kotlinx.android.synthetic.main.fragment_program_item.programContainerView
import kotlinx.android.synthetic.main.fragment_program_item.programDescription
import kotlinx.android.synthetic.main.fragment_program_item.programImage
import kotlinx.android.synthetic.main.fragment_program_item.programItemContainer
import kotlinx.android.synthetic.main.fragment_program_item.programPricePerDay
import kotlinx.android.synthetic.main.fragment_program_item.programPricePerWeek
import kotlinx.android.synthetic.main.fragment_program_item.programTitle
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.components.Constants
import ru.binaryblitz.justforyou.data.cart.CartLocalStorage
import ru.binaryblitz.justforyou.data.cart.ProgramsStorage
import ru.binaryblitz.justforyou.data.programs.Program
import ru.binaryblitz.justforyou.ui.router.Router

class ProgramItemFragment : MvpAppCompatFragment() {
  var cartProgramsLocalStorage: CartLocalStorage = ProgramsStorage()

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater?.inflate(R.layout.fragment_program_item, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val program: Program = arguments.getParcelable(ARG_BLOCK)
    val cartProgramPresenter = CartProgramPresenter(activity)
    programTitle.text = program.name
    Picasso.with(activity).load(program.imageUrl).fit().centerCrop().into(programImage)
    programDescription.text = program.description
    programPricePerDay.text = getString(R.string.per_one_day) + "${program.primaryPrice}"
    programPricePerWeek.text = getString(R.string.per_ten_days) + "${program.secondaryPrice}"
    programContainerView.setOnClickListener { Router.openDetailedProgramScreen(activity, program) }
    proceedProgramButton.setOnClickListener { showDayPickerAlert() }
    closeAlert.setOnClickListener { hideDayPickerAlert() }

    numberPicker.maxValue = Constants.MAX_PROGRAM_DAYS
    numberPicker.minValue = Constants.MIN_PROGRAM_DAYS

    addToCartButton.setOnClickListener {
      cartProgramPresenter.addProgramToCart(program, programItemContainer, cartProgramsLocalStorage,
          numberPicker)
      hideDayPickerAlert()
    }
  }


  fun showDayPickerAlert() {
    dayPickerAlertSheet.visibility = View.VISIBLE
  }

  fun hideDayPickerAlert() {
    dayPickerAlertSheet.visibility = View.GONE
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
