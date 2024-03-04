package com.agree.ecosystem.common.presentation.menu.home.offline

import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.common.databinding.FragmentOfflineBannerBinding
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.StatusBarColors
import com.agree.ecosystem.core.utils.utility.extension.setStatusBarColor
import com.agree.ecosystem.core.utils.utility.offline.ConnectivityObserver
import com.agree.ecosystem.core.utils.utility.offline.ConnectivityObserver.Status.Available
import com.telkom.legion.component.utility.extension.requiredColor
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class OfflineBannerFragment : AgrFragment<FragmentOfflineBannerBinding>() {

    private val connection: ConnectivityObserver by inject()

    override fun initUI() {
        super.initUI()
        with(binding) {
            /**
             * Initial State
             */
            root.isExpanded = !connection.isConnected
        }
    }

    override fun initObserver() {
        super.initObserver()
        with(binding) {
            /**
             * Observe connection status
             */
            connection.observe().onEach {
                when (it) {
                    Available -> {
                        root.isExpanded = false
                        requireActivity().setStatusBarColor(
                            requireContext().requiredColor(
                                StatusBarColors.PRIMARY10.value
                            )
                        )
                    }

                    else -> {
                        root.isExpanded = true
                        requireActivity().setStatusBarColor(
                            requireContext().requiredColor(
                                StatusBarColors.CUSTOMINFO700.value
                            )
                        )
                    }
                }
            }.launchIn(lifecycleScope)
        }
    }
}
