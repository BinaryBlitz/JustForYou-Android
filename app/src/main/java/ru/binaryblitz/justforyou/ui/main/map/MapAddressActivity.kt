package ru.binaryblitz.justforyou.ui.main.map

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_map_address.addAddressButton
import kotlinx.android.synthetic.main.activity_map_address.addressSearch
import kotlinx.android.synthetic.main.activity_map_address.addressSheet
import kotlinx.android.synthetic.main.activity_map_address.locationButton
import kotlinx.android.synthetic.main.activity_map_address.mapAddressCoordinator
import kotlinx.android.synthetic.main.activity_map_address.mapProgressBar
import kotlinx.android.synthetic.main.activity_map_address.searchIcon
import kotlinx.android.synthetic.main.layout_address.apartmentNumber
import kotlinx.android.synthetic.main.layout_address.entranceNumber
import kotlinx.android.synthetic.main.layout_address.floorNumber
import kotlinx.android.synthetic.main.layout_address.street
import ru.binaryblitz.justforyou.R
import ru.binaryblitz.justforyou.R.id
import ru.binaryblitz.justforyou.R.string
import ru.binaryblitz.justforyou.network.responses.delivery_addresses.create.Address
import ru.binaryblitz.justforyou.network.responses.delivery_addresses.create.AddressBodyData
import ru.binaryblitz.justforyou.network.responses.map.ResultsItem

class MapAddressActivity : MvpAppCompatActivity(), OnMapReadyCallback, MapAddressView,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnKeyListener {
  @InjectPresenter
  lateinit var presenter: MapAddressPresenter
  private lateinit var googleMap: GoogleMap
  private var currentMarker: Marker? = null
  lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
  private val defaultZoom = 11.0f
  private var permissionRequestCode: Int = 101
  private var client: GoogleApiClient? = null
  private var defaultLatLng = LatLng(55.751574, 37.57385)
  private var searchQuery = ""
  private lateinit var selectedAddressItem: ResultsItem

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_map_address)
    initViewElements()
  }

  private fun initViewElements() {
    val mapFragment = supportFragmentManager
        .findFragmentById(id.map) as SupportMapFragment
    mapFragment.getMapAsync(this)
    bottomSheetBehavior = BottomSheetBehavior.from(addressSheet as View)
    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    requestLocationPermission()
    locationButton.setOnClickListener { requestLocationPermission() }
    addressSearch.setFocusable(false)
    addressSearch.setFocusableInTouchMode(true)
    searchIcon.setOnClickListener {
      presenter.getLocationFromQuery(addressSearch.text.toString(), getString(string.geocoder_key))
      hideSoftKeyboard(this)
    }
    addAddressButton.setOnClickListener {
      if (isAddressFilled()) {
        presenter.addNewAddress(
            AddressBodyData(
                Address(null, "${selectedAddressItem.addressComponents?.get(0)?.shortName}",
                    selectedAddressItem.geometry.location.lat,
                    entranceNumber.text.toString().toInt(),
                    floorNumber.text.toString().toInt(), selectedAddressItem.formattedAddress,
                    apartmentNumber.text.toString().toInt(),
                    selectedAddressItem.geometry.location.lng)))
      } else {
        showError(getString(string.address_error))
      }
    }

    addressSearch.setOnKeyListener(this)
  }

  override fun onKey(view: View?, keyCode: Int, keyEvent: KeyEvent): Boolean {
    if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
      searchIcon.performClick()
      return true
    }
    return false
  }

  private fun isAddressFilled(): Boolean {
    if (entranceNumber.text.isNotEmpty() && apartmentNumber.text.isNotEmpty()
        && floorNumber.text.isNotEmpty()) {
      return true
    }
    return false
  }

  override fun onBackPressed() {
    super.onBackPressed()
    setResult(Activity.RESULT_OK)
  }

  override fun showAddressInfo() {
    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
  }

  override fun successAddressAddition() {
    setResult(Activity.RESULT_OK)
    finish()
  }

  override fun hideAddressInfo() {
    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
  }

  override fun onMapReady(googleMap: GoogleMap) {
    this.googleMap = googleMap

    client = GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build()
    val cameraPosition = CameraPosition.Builder().target(defaultLatLng).zoom(defaultZoom).build()
    val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
    googleMap.moveCamera(cameraUpdate)
    googleMap.uiSettings?.isRotateGesturesEnabled = true
    googleMap.uiSettings?.isTiltGesturesEnabled = false
    googleMap.uiSettings?.isMyLocationButtonEnabled = false
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED) {
      googleMap?.isMyLocationEnabled = true
    }

    googleMap.setOnMapClickListener { latLng: LatLng? ->
      this.googleMap.setOnMapClickListener { latLng: LatLng ->
        addMarker(latLng)
        presenter.getAddressFromLocation(latLng, getString(R.string.geocoder_key))
      }
    }
  }

  private fun addMarker(latLng: LatLng) {
    if (currentMarker == null) {
      currentMarker = this.googleMap.addMarker(MarkerOptions().position(latLng))
    } else {
      currentMarker?.position = latLng
    }
    googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentMarker?.position));
    googleMap.animateCamera(CameraUpdateFactory.zoomTo(defaultZoom * 1.7f))
  }

  override fun onResume() {
    super.onResume()
    client?.connect()
  }

  override fun onPause() {
    super.onPause()
    client?.disconnect()
  }

  fun requestLocationPermission() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
          permissionRequestCode)
    } else {
      googleMap.isMyLocationEnabled = true
      if (client?.isConnected!!) {
        getUserLocation()
      } else {
        client?.connect()
      }
    }
  }


  override fun onConnected(p0: Bundle?) {
  }

  override fun showLocation(address: ResultsItem) {
    addMarker(LatLng(address.geometry.location.lat,
        address.geometry.location.lng))
  }

  private fun getUserLocation() {
    val location = LocationServices.FusedLocationApi.getLastLocation(client)
    val center: CameraUpdate =
        CameraUpdateFactory.newLatLng(LatLng(location.latitude, location.longitude))
    val zoom: CameraUpdate = CameraUpdateFactory.zoomTo(defaultZoom * 1.7f)

    googleMap?.moveCamera(center)
    googleMap?.animateCamera(zoom)
  }

  override fun onConnectionSuspended(p0: Int) {
  }

  override fun onConnectionFailed(p0: ConnectionResult) {
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
      grantResults: IntArray) {
    when (requestCode) {
      permissionRequestCode -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        requestLocationPermission()
      } else {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            permissionRequestCode)
      }
    }
  }

  override fun showProgress() {
    mapProgressBar.visibility = View.VISIBLE
  }

  override fun hideProgress() {
    mapProgressBar.visibility = View.GONE
  }

  override fun showError(message: String) {
    Snackbar.make(mapAddressCoordinator, message, Snackbar.LENGTH_SHORT).show()
  }

  override fun showAddress(address: ResultsItem) {
    //Based on google maps api response models first address in list is always a street name
    //and second is street number
    street.text = "${address.addressComponents?.get(1)?.longName}, " +
        "${address.addressComponents?.get(0)?.shortName}"
    selectedAddressItem = address
  }

  fun hideSoftKeyboard(activity: Activity) {
    val inputMethodManager = activity
        .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager!!.hideSoftInputFromWindow(activity.currentFocus!!
        .windowToken, 0)
  }
}
