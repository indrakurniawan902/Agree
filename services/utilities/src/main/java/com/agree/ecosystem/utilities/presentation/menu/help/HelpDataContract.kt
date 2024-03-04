package com.agree.ecosystem.utilities.presentation.menu.help

import com.agree.ecosystem.utilities.domain.reqres.model.help.Help

interface HelpDataContract {

    fun onGetHelpLoading()

    fun onGetHelpSuccess(data: ArrayList<Help>)

    fun onGetHelpFailed(e: Throwable?)

    fun getData()
}
