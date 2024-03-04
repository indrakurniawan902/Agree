package com.agree.ecosystem.users.presentation.menu.updateprofile

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class UpdateProfileObserver(
    private val view: UpdateProfileDataContract,
    private val viewModel: UpdateProfileViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.profile.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onProfileLoading()
                is VmData.Success -> view.onGetProfileSuccess(it.data)
                is VmData.Failure -> view.onGetProfileFailed(it.throwable)
                is VmData.Empty -> view.onGetProfileFailed(EmptyException())
            }
        }

        viewModel.updateProfile.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onLoading()
                is VmData.Success -> view.onUpdateProfileSuccess(it.data)
                is VmData.Failure -> view.onUpdateProfileFailed(it.throwable)
                is VmData.Empty -> view.onUpdateProfileFailed(EmptyException())
            }
        }

        viewModel.updateAvatar.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onUpdateAvatarLoading()
                is VmData.Success -> view.onUpdateAvatarSuccess(it.data)
                is VmData.Failure -> view.onUpdateAvatarFailed(it.throwable)
                is VmData.Empty -> view.onUpdateAvatarFailed(EmptyException())
            }
        }
    }
}
