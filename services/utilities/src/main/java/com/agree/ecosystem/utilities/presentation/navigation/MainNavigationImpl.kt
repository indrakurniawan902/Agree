package com.agree.ecosystem.utilities.presentation.navigation

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.agree.ecosystem.core.utils.utility.AppConfig
import com.agree.ecosystem.utilities.domain.reqres.model.help.Help
import com.agree.ecosystem.utilities.presentation.menu.help.HelpFragmentDirections
import com.google.gson.Gson
import com.telkom.legion.extension.snackbar.errorSnackBar

class MainNavigationImpl(
    private val nav: NavController?,
    private val activity: FragmentActivity?
) : MainNavigation {
    override fun goToPrevious() {
        nav?.navigateUp()
    }

    override fun fromHelpToDetailHelp(data: Help) {
        runCatching {
            nav?.navigate(
                HelpFragmentDirections.actionHelpFragmentToDetailHelpFragment(
                    Gson().toJson(data)
                )
            )
        }.onFailure {
            it.printStackTrace()
            if (AppConfig.isDebug) {
                activity?.errorSnackBar(it.message.orEmpty())
            }
        }
    }
}
