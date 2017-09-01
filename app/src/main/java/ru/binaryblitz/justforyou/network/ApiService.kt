package ru.binaryblitz.justforyou.network

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.binaryblitz.justforyou.data.menu.Menu
import ru.binaryblitz.justforyou.data.programs.Block
import ru.binaryblitz.justforyou.data.programs.Program
import ru.binaryblitz.justforyou.data.user.UserInfo
import ru.binaryblitz.justforyou.network.models.UserData
import ru.binaryblitz.justforyou.network.responses.CreateTokenResponse
import ru.binaryblitz.justforyou.network.responses.VerifyTokenResponse
import ru.binaryblitz.justforyou.network.responses.deliveries.Delivery
import ru.binaryblitz.justforyou.network.responses.delivery_addresses.create.Address
import ru.binaryblitz.justforyou.network.responses.delivery_addresses.create.AddressBodyData
import ru.binaryblitz.justforyou.network.responses.orders.CardBody
import ru.binaryblitz.justforyou.network.responses.orders.DeliveryBody
import ru.binaryblitz.justforyou.network.responses.orders.OrderBody
import ru.binaryblitz.justforyou.network.responses.orders.OrderResponse
import ru.binaryblitz.justforyou.network.responses.orders.PurchaseItem
import ru.binaryblitz.justforyou.network.responses.payment.Payment
import ru.binaryblitz.justforyou.network.responses.payment_cards.PaymentCard
import ru.binaryblitz.justforyou.network.responses.purchases.PurchasesResponse

/**
 * JustForYou API methods
 */
interface ApiService {
  @POST("api/verification_tokens")
  fun createToken(@Query("phone_number") phoneNumber: String): Single<CreateTokenResponse>

  @PATCH("api/verification_tokens/{token}")
  fun verifyToken(@Path("token") token: String, @Query("phone_number") phoneNumber: String,
      @Query("code") code: String): Single<VerifyTokenResponse>

  @POST("/api/user.json")
  fun createUser(@Body user: UserData): Single<UserInfo>

  @PATCH("/api/user.json")
  fun updateUser(@Body user: UserData, @Query("api_token") token: String): Single<Any>

  @GET("/api/user.json")
  fun getUser(@Query("api_token") token: String): Single<UserInfo>

  @GET("/api/blocks.json")
  fun getBlocks(@Query("api_token") token: String): Single<List<Block>>

  @GET("/api/blocks/{blockId}/programs.json")
  fun getPrograms(@Path("blockId") blockId: Int,
      @Query("api_token") token: String): Single<List<Program>>

  @GET("/api/programs/{programId}/days.json")
  fun getMenu(@Path("programId") programId: Int,
      @Query("api_token") token: String): Single<List<Menu>>

  @GET("/api/addresses.json")
  fun getDeliveryAddresses(@Query("api_token") token: String): Single<List<Address>>

  @DELETE("/api/addresses/{id}.json")
  fun removeDeliveryAddress(@Path("id") id: Int,
      @Query("api_token") token: String): Single<Address>

  @POST("/api/addresses.json")
  fun createDeliveryAddress(@Body address: AddressBodyData,
      @Query("api_token") token: String): Single<Address>

  @GET("/api/purchases.json")
  fun getPurchases(@Query("api_token") token: String): Single<List<PurchasesResponse>>

  @GET("/api/orders.json")
  fun getOrders(@Query("api_token") token: String): Single<List<PurchaseItem>>

  @GET("/api/payment_cards.json")
  fun getPaymentCards(@Query("api_token") token: String): Single<List<PaymentCard>>

  @POST("/api/orders/{id}/payment.json")
  fun makeOrderPayment(@Path("id") id: Int,
      @Query("api_token") token: String): Single<Payment>

  @POST("/api/orders/{id}/payment.json")
  fun makeOrderPaymentWithCard(@Path("id") id: Int, @Body cardBody: CardBody,
      @Query("api_token") token: String): Single<Payment>

  @POST("/api/orders/{id}/payment.json")
  fun payWithCreditCard(@Path("id") id: Int,
      @Query("api_token") token: String): Single<Payment>

  @POST("/api/orders.json")
  fun createOrder(@Body orderBody: OrderBody,
      @Query("api_token") token: String): Single<OrderResponse>

  @POST("/api/purchases/{orderId}/deliveries.json")
  fun addDeliveryDays(@Path("orderId") id: Int, @Body deliveryBody: DeliveryBody,
      @Query("api_token") token: String): Single<Payment>

  @GET("/api/deliveries.json")
  fun getDeliveries(@Query("api_token") token: String): Single<List<Delivery>>

}
