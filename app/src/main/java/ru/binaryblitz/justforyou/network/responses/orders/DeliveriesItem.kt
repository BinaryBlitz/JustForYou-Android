package ru.binaryblitz.justforyou.network.responses.orders

import com.squareup.moshi.Json
import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class DeliveriesItem : RealmObject() {
  @Json(name = "address_id")
  var addressId: Int? = null
  @Json(name = "scheduled_for")
  var scheduledFor: String? = null
  var comment: String? = null
}
