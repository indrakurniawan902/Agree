package com.agree.ecosystem.users.data.reqres.web

import com.agree.ecosystem.users.data.reqres.model.profile.ChangePasswordBodyPost
import com.agree.ecosystem.users.data.reqres.model.profile.ProfileItem
import com.agree.ecosystem.users.data.reqres.model.profile.UpdateProfileBodyPost
import com.devbase.data.source.web.model.DevApiResponse
import com.google.gson.JsonElement
import io.reactivex.Flowable
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface AgreeUsersApiClient {
    @GET("users/v1/users/{id}")
    fun getUserProfile(
        @Path("id") userId: String
    ): Single<Response<DevApiResponse<ProfileItem>>>

    @PUT("users/v1/users/{id}")
    fun updateUserProfile(
        @Path("id") userId: String,
        @Body data: UpdateProfileBodyPost
    ): Flowable<Response<DevApiResponse<ProfileItem>>>

    @Multipart
    @PUT("users/v1/users/avatar/{id}")
    fun updateUserAvatar(
        @Path("id") userId: String,
        @Part file: MultipartBody.Part
    ): Flowable<Response<DevApiResponse<ProfileItem>>>

    @PUT("users/v1/users/change-password")
    fun changePassword(
        @Body data: ChangePasswordBodyPost
    ): Flowable<Response<DevApiResponse<JsonElement>>>
}
