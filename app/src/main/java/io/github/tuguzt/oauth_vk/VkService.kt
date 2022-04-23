package io.github.tuguzt.oauth_vk

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface VkService {
    @POST("method/users.get")
    fun getUsersProfiles(
        @Query("user_ids") id: String,
        @Query("fields") fields: String,
        @Query("access_token") token: String,
        @Query("v") version: String = "5.131",
    ): Call<VkUsersResponse>
}
