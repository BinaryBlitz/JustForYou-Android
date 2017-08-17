package ru.binaryblitz.justforyou.data.cart

import io.realm.Realm
import ru.binaryblitz.justforyou.data.programs.Program


/**
 * Created by ilyasavin on 8/17/17.
 */
class ProgramsStorage : CartLocalStorage {
  var realmLocalStorage: Realm = Realm.getDefaultInstance()

  override fun getCartPrograms(): List<CartModel> {
    val query = realmLocalStorage.where(CartModel::class.java)

    return realmLocalStorage.copyFromRealm(query.findAll())
  }

  override fun addProgramToCart(blockName: String, days: Int, price: Int, program: Program) {
    realmLocalStorage
    realmLocalStorage.executeTransaction({ realm ->
      val cartProgram = realm.createObject(CartModel::class.java)
      cartProgram.blockName = blockName
      cartProgram.days = days
      cartProgram.programId = program.id
      cartProgram.programName = program.name
      cartProgram.price = price
    })
  }

  override fun removeProgramFromCart(programId: Int) {
    realmLocalStorage.executeTransaction({ realm ->
      val result = realm.where(CartModel::class.java).equalTo("programId", programId).findAll()
      result.deleteAllFromRealm()
    })
  }
}
