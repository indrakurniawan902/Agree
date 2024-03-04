package com.agree.ecosystem.users.data.reqres

import com.agree.ecosystem.users.data.reqres.model.profile.ChangePasswordBodyPost
import com.agree.ecosystem.users.data.reqres.model.profile.ProfileEntity
import com.agree.ecosystem.users.data.reqres.model.profile.ProfileItem
import com.agree.ecosystem.users.data.reqres.model.profile.UpdateProfileBodyPost
import com.devbase.data.source.DevRepository
import com.google.gson.JsonElement
import io.reactivex.Flowable
import java.io.File

interface UsersRepository : DevRepository {
    fun getProfile(userId: String): Flowable<ProfileEntity>

    fun updateProfile(userId: String, data: UpdateProfileBodyPost): Flowable<ProfileItem>

    fun updateAvatar(userId: String, file: File): Flowable<ProfileItem>

    fun changePassword(data: ChangePasswordBodyPost): Flowable<JsonElement>
}
