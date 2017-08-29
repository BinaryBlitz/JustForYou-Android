package ru.binaryblitz.justforyou.data.menu

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class MenuItem(
    @Json(name = "starts_at")
    val startsAt: String? = null,
    val weight: Int? = null,
    val id: Int? = null,
    val calories: Double? = null,
    @Json(name = "ends_at")
    val endsAt: String? = null,
    val content: String? = null
) : Parcelable {
  constructor(source: Parcel) : this(
      source.readString(),
      source.readValue(Int::class.java.classLoader) as Int?,
      source.readValue(Int::class.java.classLoader) as Int?,
      source.readValue(Double::class.java.classLoader) as Double?,
      source.readString(),
      source.readString()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeString(startsAt)
    writeValue(weight)
    writeValue(id)
    writeValue(calories)
    writeString(endsAt)
    writeString(content)
  }

  companion object {
    @JvmField val CREATOR: Parcelable.Creator<MenuItem> = object : Parcelable.Creator<MenuItem> {
      override fun createFromParcel(source: Parcel): MenuItem = MenuItem(source)
      override fun newArray(size: Int): Array<MenuItem?> = arrayOfNulls(size)
    }
  }
}
