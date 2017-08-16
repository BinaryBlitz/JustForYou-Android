package ru.binaryblitz.justforyou.ui.main.program_item

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.components.Extras
import ru.binaryblitz.justforyou.data.programs.Block
import ru.binaryblitz.justforyou.data.programs.Program

class ProgramsActivity : MvpAppCompatActivity(), ProgramsView {
  @InjectPresenter
  lateinit var presenter: ProgramsPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_program)

    presenter.getBlockPrograms(intent.getParcelableExtra<Block>(Extras.EXTRA_BLOCK).id!!)
  }

  override fun showProgress() {
  }

  override fun hideProgress() {
  }

  override fun showError(message: String) {
  }

  override fun showPrograms(blocks: List<Program>) {
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_program, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val id = item.itemId
    if (id == R.id.action_settings) {
      return true
    }
    return super.onOptionsItemSelected(item)
  }


}
