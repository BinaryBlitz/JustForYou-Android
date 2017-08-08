package binaryblitz.justforyou.network

import binaryblitz.justforyou.network.responses.VerificationTokenResponse
import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {
  @POST("api/verification_tokens")
  fun createToken(@Query("phone_number") phoneNumber: String): Single<VerificationTokenResponse>
}
