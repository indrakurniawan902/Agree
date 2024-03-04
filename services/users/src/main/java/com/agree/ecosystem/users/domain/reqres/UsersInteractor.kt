package com.agree.ecosystem.users.domain.reqres

import com.agree.ecosystem.users.data.reqres.UsersRepository
import com.agree.ecosystem.users.data.reqres.model.profile.ChangePasswordBodyPost
import com.agree.ecosystem.users.data.reqres.model.profile.UpdateProfileBodyPost
import com.agree.ecosystem.users.domain.reqres.model.profile.Profile
import com.google.gson.JsonElement
import io.reactivex.Flowable
import java.io.File

class UsersInteractor(
    private val repo: UsersRepository
) : UsersUsecase {

    override fun getProfile(userId: String): Flowable<Profile> {
        return repo.getProfile(userId).map { it.toProfile() }
    }

    override fun updateProfile(userId: String, data: UpdateProfileBodyPost): Flowable<Profile> {
        return repo.updateProfile(userId, data).map { it.toProfile() }
    }

    override fun updateAvatar(userId: String, file: File): Flowable<Profile> {
        return repo.updateAvatar(userId, file).map { it.toProfile() }
    }

    override fun changePassword(data: ChangePasswordBodyPost): Flowable<JsonElement> {
        return repo.changePassword(data)
    }
}
