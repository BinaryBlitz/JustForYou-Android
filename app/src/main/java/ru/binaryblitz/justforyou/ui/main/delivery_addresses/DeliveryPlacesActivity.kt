package ru.binaryblitz.justforyou.ui.main.delivery_addresses

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_delivery_places.addNewAddressButton
import kotlinx.android.synthetic.main.activity_delivery_places.placesProgress
import kotlinx.android.synthetic.main.activity_settings.toolbar
import kotlinx.android.synthetic.main.content_delivery_places.placesView
import kotlinx.android.synthetic.main.place_item.view.placesContainerView
import kotlinx.android.synthetic.main.place_item.view.placesTitle
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.string
import ru.binaryblitz.justforyou.components.Extras
import ru.binaryblitz.justforyou.network.responses.delivery_addresses.create.Address
import ru.binaryblitz.justforyou.ui.base.BaseRecyclerAdapter
import ru.binaryblitz.justforyou.ui.router.Router

val NEW_ADDRESS_REQUEST_CODE = 1001

class DeliveryPlacesActivity : MvpAppCompatActivity(), PlacesView {
  @InjectPresenter
  lateinit var presenter: PlacesPresenter
  lateinit var adapter: PlacesAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_delivery_places)
    initViewElements()
    presenter.getPlaces()
  }

  private fun initViewElements() {
    setSupportActionBar(toolbar)
    toolbar.title = getString(string.addresses)
    toolbar.setNavigationIcon(R.drawable.ic_arrow_back24b)
    toolbar.setNavigationOnClickListener { onBackPressed() }
    addNewAddressButton.setOnClickListener { Router.openMapScreen(this, NEW_ADDRESS_REQUEST_CODE) }
  }

  override fun showProgress() {
    placesProgress.visibility = View.VISIBLE
  }

  override fun hideProgress() {
    placesProgress.visibility = View.GONE
  }

  override fun showError(message: String) {

  }

  override fun showPlaces(addresses: List<Address>) {
    adapter = PlacesAdapter()
    placesView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    placesView.adapter = adapter
    adapter.setData(addresses)
    adapter.onItemSelectAction.subscribe { address ->
      val intent: Intent = Intent()
      intent.putExtra(Extras.EXTRA_ADDRESS_ID, address.id)
      intent.putExtra(Extras.EXTRA_ADDRESS_TEXT, address.content)
      setResult(Activity.RESULT_OK, intent)
      finish()
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == Activity.RESULT_OK && requestCode == NEW_ADDRESS_REQUEST_CODE) {
      presenter.getPlaces()
    }
  }
}

class PlacesAdapter : BaseRecyclerAdapter<Address>() {
  var onItemSelectAction: PublishSubject<Address> = PublishSubject.create()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder<Address> {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
    return ViewHolder(view)
  }

  private inner class ViewHolder(itemView: View) : RecyclerViewHolder<Address>(itemView) {

    override fun setItem(item: Address, position: Int) {
      itemView.placesTitle.text = "${item.content}, ${item.house}"
      itemView.placesContainerView.setOnClickListener {
        onItemSelectAction.onNext(item)
      }
    }

  }
}
