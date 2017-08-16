package ru.binaryblitz.justforyou.ui.main.program_item.detailed_program.pages.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import kotlinx.android.synthetic.main.fragment_about.programAbout
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.data.programs.Program

class AboutFragment : MvpAppCompatFragment() {

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater?.inflate(R.layout.fragment_about, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    var program: Program = arguments.getParcelable(ARG_PROGRAM)

    val aboutText = StringBuilder()
    for ((index) in program.prescription?.withIndex()!!) {
      val positionNumber = index + 1
      aboutText.append(
          positionNumber.toString() + ". " + (program.prescription as List<String>)[index] + "\n\n")
    }
    programAbout.text = aboutText.toString()

  }

  companion object {
    val ARG_PROGRAM: String = "block_item"

    fun getInstance(type: Program): AboutFragment {
      val fragment = AboutFragment()
      val args = Bundle()
      args.putParcelable(
          ARG_PROGRAM, type)
      fragment.arguments = args
      return fragment
    }
  }
}
