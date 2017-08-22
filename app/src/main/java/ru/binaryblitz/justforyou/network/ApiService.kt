package ru.binaryblitz.justforyou.network

import io.reactivex.Single
import retrofit2.http.Body
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
  fun getMenu(@Path("programId") programId: Int, @Query("api_token") token: String): Single<List<Menu>>
}
