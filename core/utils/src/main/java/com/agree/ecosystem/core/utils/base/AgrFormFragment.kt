package com.agree.ecosystem.core.utils.base

import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.agree.ecosystem.core.analytics.AgrAnalytics
import com.agree.ecosystem.core.analytics.constant.FirebaseUserProperty
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.domain.reqres.model.ValidationModel
import com.agree.ecosystem.core.utils.utility.eventbus.AppEvent
import com.agree.ecosystem.core.utils.utility.eventbus.EventBus
import com.agree.ecosystem.core.utils.utility.validation.ValidationController
import com.agree.ecosystem.core.utils.utility.validation.viewmodels.ValidationView
import com.agree.ecosystem.core.utils.utility.validation.viewmodels.ValidationViewModel
import com.devbase.presentation.viewbinding.DevFragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.oratakashi.viewbinding.core.binding.list.arrayList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

abstract class AgrFormFragment<T : ViewBinding> :
    DevFragment<T>(),
    ValidationController,
    ValidationView {

    private val analytics by inject<AgrAnalytics>()
    private val eventBus: EventBus by inject()

    override val forms: MutableList<MutableStateFlow<ValidationModel>> by arrayList()

    override val validation: ValidationViewModel by viewModel { parametersOf(this) }

    override fun initUI() {
        super.initUI()
        initAnalytics()
        initForm()
    }

    private fun initAnalytics() {
        val pref: AgrPrefUsecase by inject()
        analytics.logEvent(
            FirebaseAnalytics.Event.SCREEN_VIEW,
            bundleOf(
                FirebaseAnalytics.Param.SCREEN_NAME to javaClass.simpleName,
                FirebaseAnalytics.Param.SCREEN_CLASS to javaClass.canonicalName,
                FirebaseUserProperty.USERNAME to pref.userName
            )
        )
    }

    open fun broadcastEvent(event: () -> AppEvent?) {
        event.invoke()?.let { lifecycleScope.launch { eventBus.invokeEvent(it) } }
    }

    abstract fun initForm()
}
