package com.agree.ecosystem.users.data.reqres.web

import com.agree.ecosystem.users.data.reqres.model.profile.ChangePasswordBodyPost
import com.agree.ecosystem.users.data.reqres.model.profile.ProfileItem
import com.agree.ecosystem.users.data.reqres.model.profile.UpdateProfileBodyPost
import com.devbase.data.source.web.WebService
import com.devbase.data.source.web.model.DevApiResponse
import com.google.gson.JsonElement
import io.reactivex.Flowable
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Response

class AgreeUsersApi(
    private val api: AgreeUsersApiClient
) : AgreeUsersApiClient, WebService {

    override fun getUserProfile(userId: String): Single<Response<DevApiResponse<ProfileItem>>> {
        return api.getUserProfile(userId)
    }

    override fun updateUserProfile(userId: String, data: UpdateProfileBodyPost): Flowable<Response<DevApiResponse<ProfileItem>>> {
        return api.updateUserProfile(userId, data)
    }

    override fun updateUserAvatar(
        userId: String,
        file: MultipartBody.Part
    ): Flowable<Response<DevApiResponse<ProfileItem>>> {
        return api.updateUserAvatar(userId, file)
    }

    override fun changePassword(data: ChangePasswordBodyPost): Flowable<Response<DevApiResponse<JsonElement>>> {
        return api.changePassword(data)
    }
}
