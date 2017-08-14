package ru.binaryblitz.justforyou.ui.base

interface BaseLCEView {
  fun showProgress()
  fun hideProgress()
  fun showError(message: String)
}
