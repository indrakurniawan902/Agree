package com.agree.ecosystem.users.presentation.navigation

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.agree.ecosystem.users.presentation.menu.profile.ProfileFragmentDirections

class MainNavigationImpl(
    private val nav: NavController?,
    private val activity: FragmentActivity?
) : MainNavigation {
    override fun goToPrevious() {
        activity?.onBackPressed()
    }

    override fun fromProfileToEditProfile() {
        runCatching {
            nav?.navigate(ProfileFragmentDirections.actionProfileFragmentToUpdateProfileFragment())
        }.onFailure {
            it.printStackTrace()
        }
    }
}
