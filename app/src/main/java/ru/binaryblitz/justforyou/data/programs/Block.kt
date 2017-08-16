package ru.binaryblitz.justforyou.data.programs

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class Block(
    @Json(name = "programs_count")
    val programsCount: Int? = null,
    val color: String? = null,
    @Json(name = "image_url")
    val imageUrl: String? = null,
    val name: String? = null,
    val id: Int? = null
) : Parcelable {
  constructor(source: Parcel) : this(
      source.readValue(Int::class.java.classLoader) as Int?,
      source.readString(),
      source.readString(),
      source.readString(),
      source.readValue(Int::class.java.classLoader) as Int?
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeValue(programsCount)
    writeString(color)
    writeString(imageUrl)
    writeString(name)
    writeValue(id)
  }

  companion object {
    @JvmField val CREATOR: Parcelable.Creator<Block> = object : Parcelable.Creator<Block> {
      override fun createFromParcel(source: Parcel): Block = Block(source)
      override fun newArray(size: Int): Array<Block?> = arrayOfNulls(size)
    }
  }
}
