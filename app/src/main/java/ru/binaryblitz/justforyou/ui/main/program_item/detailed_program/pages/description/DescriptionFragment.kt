package ru.binaryblitz.justforyou.ui.main.program_item.detailed_program.pages.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import kotlinx.android.synthetic.main.fragment_description.descriptionText
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.data.programs.Program

class DescriptionFragment : MvpAppCompatFragment() {

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater?.inflate(R.layout.fragment_description, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    var program: Program = arguments.getParcelable(ARG_PROGRAM)
    descriptionText.text = program.description
  }

  companion object {
    val ARG_PROGRAM: String = "block_item"

    fun getInstance(type: Program): DescriptionFragment {
      val fragment = DescriptionFragment()
      val args = Bundle()
      args.putParcelable(
          ARG_PROGRAM, type)
      fragment.arguments = args
      return fragment
    }
  }
}
