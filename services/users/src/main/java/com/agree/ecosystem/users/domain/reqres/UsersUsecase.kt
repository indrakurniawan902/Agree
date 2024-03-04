package com.agree.ecosystem.users.domain.reqres

import com.agree.ecosystem.users.data.reqres.model.profile.ChangePasswordBodyPost
import com.agree.ecosystem.users.data.reqres.model.profile.UpdateProfileBodyPost
import com.agree.ecosystem.users.domain.reqres.model.profile.Profile
import com.google.gson.JsonElement
import io.reactivex.Flowable
import java.io.File

interface UsersUsecase {
    fun getProfile(userId: String): Flowable<Profile>

    fun updateProfile(userId: String, data: UpdateProfileBodyPost): Flowable<Profile>

    fun updateAvatar(userId: String, file: File): Flowable<Profile>

    fun changePassword(data: ChangePasswordBodyPost): Flowable<JsonElement>
}
