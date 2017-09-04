package ru.binaryblitz.justforyou.data.cart

import io.realm.Realm
import io.realm.RealmConfiguration
import ru.binaryblitz.justforyou.data.programs.Program
import ru.binaryblitz.justforyou.network.responses.orders.DeliveriesItem


/**
 * Cart local storage implemented via realm database
 */
class ProgramsStorage : CartLocalStorage {
  var realmLocalStorage: Realm

  init {
    val realmConfiguration = RealmConfiguration.Builder()
        .name(Realm.DEFAULT_REALM_NAME)
        .deleteRealmIfMigrationNeeded()
        .build()
    realmLocalStorage = Realm.getInstance(realmConfiguration)
  }

  override fun getCartPrograms(): List<CartModel> {
    val query = realmLocalStorage.where(CartModel::class.java)

    return realmLocalStorage.copyFromRealm(query.findAll())
  }

  override fun addProgramToCart(blockName: String, days: Int, price: Int, program: Program,
      deliveries: List<DeliveriesItem>?) {
    realmLocalStorage
    realmLocalStorage.executeTransaction({ realm ->
      val cartProgram = realm.createObject(CartModel::class.java)
      cartProgram.blockName = blockName
      cartProgram.days = days
      cartProgram.programId = program.id
      cartProgram.programName = program.name
      cartProgram.price = price
      cartProgram.deliveries.addAll(deliveries?.toList()!!)
    })
  }

  override fun removeProgramFromCart(programId: Int) {
    realmLocalStorage.executeTransaction({ realm ->
      val result = realm.where(CartModel::class.java).equalTo("programId", programId).findAll()
      result.deleteAllFromRealm()
    })
  }

  override fun clear() {
    realmLocalStorage.executeTransaction({ realm ->
      val result = realm.where(CartModel::class.java).findAll()
      result.deleteAllFromRealm()
    })
  }
}
