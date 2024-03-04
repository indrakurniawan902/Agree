package com.agree.ecosystem.users.data.reqres

import androidx.annotation.WorkerThread
import com.agree.ecosystem.users.data.reqres.db.UsersDb
import com.agree.ecosystem.users.data.reqres.model.profile.ChangePasswordBodyPost
import com.agree.ecosystem.users.data.reqres.model.profile.ProfileEntity
import com.agree.ecosystem.users.data.reqres.model.profile.ProfileItem
import com.agree.ecosystem.users.data.reqres.model.profile.UpdateProfileBodyPost
import com.agree.ecosystem.users.data.reqres.web.AgreeUsersApi
import com.devbase.data.utilities.rx.operators.flowableApiError
import com.devbase.data.utilities.rx.operators.singleApiError
import com.google.gson.JsonElement
import com.oratakashi.viewbinding.core.network.rxjava2.flowable.networkSyncReverse
import io.reactivex.Flowable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@WorkerThread
class UsersDataStore(
    override val dbService: UsersDb,
    override val webService: AgreeUsersApi
) : UsersRepository {

    override fun getProfile(userId: String): Flowable<ProfileEntity> {
        return networkSyncReverse<List<ProfileItem>, List<ProfileEntity>>(
            saveToDb = { dbService.profileDao().addAll(it) },
            fetchDb = { dbService.profileDao().getUsersById(userId) },
            fetchApi = {
                webService.getUserProfile(userId).lift(singleApiError()).map {
                    it.data?.let { data -> listOf(data) } ?: emptyList()
                }
            },
            mapData = { it.map { data -> data.toProfileEntity() } }
        ).map { it.firstOrNull() ?: throw Exception("Data Not Found!") }
    }

    override fun updateProfile(userId: String, data: UpdateProfileBodyPost): Flowable<ProfileItem> {
        return webService.updateUserProfile(userId, data)
            .lift(flowableApiError())
            .map { it.data }
    }

    override fun updateAvatar(userId: String, file: File): Flowable<ProfileItem> {
        val requestBody = MultipartBody.Part.createFormData(
            "avatar", file.name, file.asRequestBody("image/*".toMediaTypeOrNull())
        )
        return webService.updateUserAvatar(userId, requestBody)
            .lift(flowableApiError())
            .map { it.data }
    }

    override fun changePassword(data: ChangePasswordBodyPost): Flowable<JsonElement> {
        return webService.changePassword(data)
            .lift(flowableApiError())
            .map { it.data }
    }
}
