package binaryblitz.justforyou.network

import binaryblitz.justforyou.data.user.UserInfo
import binaryblitz.justforyou.network.models.UserData
import binaryblitz.justforyou.network.responses.CreateTokenResponse
import binaryblitz.justforyou.network.responses.VerifyTokenResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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
}
