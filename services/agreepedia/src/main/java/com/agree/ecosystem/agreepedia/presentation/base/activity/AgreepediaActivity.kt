package com.agree.ecosystem.agreepedia.presentation.base.activity

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.agreepedia.R
import com.agree.ecosystem.agreepedia.databinding.ActivityAgreepediaBinding
import com.agree.ecosystem.agreepedia.domain.model.article.Article
import com.agree.ecosystem.agreepedia.presentation.menu.detail.DetailFragmentArgs
import com.agree.ecosystem.core.utils.base.AgrActivity
import com.agree.ecosystem.core.utils.utility.eventbus.AppEvent
import com.agree.ecosystem.core.utils.utility.extension.initGraph
import com.agree.locales.presentation.delegation.LocaleActivityDelegate
import com.agree.locales.presentation.delegation.LocaleActivityDelegation
import kotlinx.coroutines.flow.collectLatest

class AgreepediaActivity :
    AgrActivity<ActivityAgreepediaBinding>(),
    LocaleActivityDelegate by LocaleActivityDelegation() {

    init {
        initLocale(this, super.getDelegate())
    }

    override fun getDelegate(): AppCompatDelegate {
        return getLocaleDelegate()
    }

    override fun initUI() {
        super.initUI()
        lifecycleScope.launchWhenResumed {
            eventBus.events.collectLatest {
                when (it) {
                    AppEvent.FORCE_LOGOUT -> {
                        finish()
                    }
                    else -> return@collectLatest
                }
            }
        }

        val oId: Long = intent.getLongExtra("oId", 0L)
        val payload: Article? = intent.getParcelableExtra("payload")

        if (payload != null || oId != 0L) {
            initGraph(
                R.id.nav_host_agreepedia,
                R.navigation.nav_agreepedia,
                DetailFragmentArgs(
                    agreepedia = payload,
                    oId = oId
                ).toBundle()
            ) {
                it.setStartDestination(R.id.detailFragment)
            }
        } else {
            initGraph(
                R.id.nav_host_agreepedia,
                R.navigation.nav_agreepedia
            )
        }
    }
}
