package ru.binaryblitz.justforyou.data.cart

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.RealmClass
import ru.binaryblitz.justforyou.network.responses.orders.DeliveriesItem

/**
 * Cart model used for local storage
 */
@RealmClass
open class CartModel : RealmObject() {
  var programId: Int? = null
  var programName: String? = null
  var blockName: String? = null
  var days: Int? = null
  var price: Int? = null
  var deliveries: RealmList<DeliveriesItem> = RealmList()
}
