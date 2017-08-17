package ru.binaryblitz.justforyou.data.menu

import android.os.Parcel
import android.os.Parcelable

data class Menu(
    val id: Int? = null,
    val position: Int? = null,
    val calories: Double? = null,
    val items: List<MenuItem>? = null
) : Parcelable {
  constructor(source: Parcel) : this(
      source.readValue(Int::class.java.classLoader) as Int?,
      source.readValue(Int::class.java.classLoader) as Int?,
      source.readValue(Double::class.java.classLoader) as Double?,
      ArrayList<MenuItem>().apply { source.readList(this, MenuItem::class.java.classLoader) }
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeValue(id)
    writeValue(position)
    writeValue(calories)
    writeList(items)
  }

  companion object {
    @JvmField val CREATOR: Parcelable.Creator<Menu> = object : Parcelable.Creator<Menu> {
      override fun createFromParcel(source: Parcel): Menu = Menu(source)
      override fun newArray(size: Int): Array<Menu?> = arrayOfNulls(size)
    }
  }
}
