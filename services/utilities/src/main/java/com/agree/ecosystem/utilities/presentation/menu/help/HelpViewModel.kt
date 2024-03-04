package com.agree.ecosystem.utilities.presentation.menu.help

import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.utilities.domain.reqres.UtilitiesUsecase
import com.agree.ecosystem.utilities.domain.reqres.model.help.Help
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel

class HelpViewModel(
    private val usecase: UtilitiesUsecase
) : DevViewModel() {

    private val _help = DevData<ArrayList<Help>>().apply { default() }
    val help = _help.immutable()

    fun getHelp(block: Int.(String?) -> Unit) {
        usecase.getHelpCategory()
            .setHandler(_help, block)
            .let { return@let disposable::add }
    }
}
