package ru.binaryblitz.justforyou.data.programs

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class Program(
    val preview: String? = null,
    val unit: String? = null,
    @Json(name = "individual_price")
    val individualPrice: Boolean? = null,
    @Json(name = "secondary_price")
    val secondaryPrice: Int? = null,
    val prescription: List<String>? = null,
    @Json(name = "image_url")
    val imageUrl: String? = null,
    val name: String,
    val description: String? = null,
    val threshold: Int? = null,
    val id: Int? = null,
    @Json(name = "block_id")
    val blockId: Int? = null,
    @Json(name = "primary_price")
    val primaryPrice: Int? = null
) : Parcelable {
  constructor(source: Parcel) : this(
      source.readString(),
      source.readString(),
      source.readValue(Boolean::class.java.classLoader) as Boolean?,
      source.readValue(Int::class.java.classLoader) as Int?,
      ArrayList<String>().apply { source.readList(this, String::class.java.classLoader) },
      source.readString(),
      source.readString(),
      source.readString(),
      source.readValue(Int::class.java.classLoader) as Int?,
      source.readValue(Int::class.java.classLoader) as Int?,
      source.readValue(Int::class.java.classLoader) as Int?,
      source.readValue(Int::class.java.classLoader) as Int?
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeString(preview)
    writeString(unit)
    writeValue(individualPrice)
    writeValue(secondaryPrice)
    writeList(prescription)
    writeString(imageUrl)
    writeString(name)
    writeString(description)
    writeValue(threshold)
    writeValue(id)
    writeValue(blockId)
    writeValue(primaryPrice)
  }

  companion object {
    @JvmField val CREATOR: Parcelable.Creator<Program> = object : Parcelable.Creator<Program> {
      override fun createFromParcel(source: Parcel): Program = Program(source)
      override fun newArray(size: Int): Array<Program?> = arrayOfNulls(size)
    }
  }
}
