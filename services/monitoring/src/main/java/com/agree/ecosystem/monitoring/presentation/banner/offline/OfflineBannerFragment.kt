package com.agree.ecosystem.monitoring.presentation.banner.offline

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import androidx.annotation.ColorInt
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.StatusBarColors
import com.agree.ecosystem.core.utils.utility.extension.setStatusBarColor
import com.agree.ecosystem.core.utils.utility.offline.ConnectivityObserver
import com.agree.ecosystem.core.utils.utility.offline.ConnectivityObserver.Status.Available
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.FragmentOfflineBannerMonitoringBinding
import com.agree.ecosystem.monitoring.presentation.base.activity.settingsoffline.SettingsOfflineMonitoringActivity
import com.agree.ui.UiAttrs
import com.agree.ui.UiKitAttrs
import com.oratakashi.viewbinding.core.tools.gone
import com.oratakashi.viewbinding.core.tools.startActivity
import com.oratakashi.viewbinding.core.tools.visible
import com.telkom.legion.component.utility.extension.requiredColor
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class OfflineBannerFragment : AgrFragment<FragmentOfflineBannerMonitoringBinding>() {

    private val connection: ConnectivityObserver by inject()

    override fun initUI() {
        super.initUI()
        with(binding) {
            switchState(connection.isConnected)
            btnSettings.setOnClickListener {
                runCatching {
                    activity?.startActivity(SettingsOfflineMonitoringActivity::class.java)
                }.onFailure {
                    it.printStackTrace()
                }
            }
        }
    }

    override fun initObserver() {
        super.initObserver()
        connection.observe().onEach {
            when (it) {
                Available -> switchState(true)
                else -> switchState(false)
            }
        }.launchIn(lifecycleScope)
    }

    private fun switchState(isConnect: Boolean) {
        with(binding) {
            val draftState = draftState()
            if (isConnect) {
                root.isExpanded = draftState
                val color =
                    if (draftState) UiKitAttrs.colorPrimary
                    else UiKitAttrs.white

                requireActivity().setStatusBarColor(
                    requireContext().requiredColor(color)
                )
                animateColors(
                    colorFrom = requireContext().requiredColor(UiAttrs.customInfo500),
                    colorTo = requireContext().requiredColor(UiKitAttrs.colorPrimary)
                )
                tvTitle.text = getString(R.string.label_title_offline_banner_monitoring_connected)
            } else {
                root.isExpanded = true
                requireActivity().setStatusBarColor(
                    requireContext().requiredColor(
                        StatusBarColors.CUSTOMINFO700.value
                    )
                )
                animateColors(
                    colorFrom = requireContext().requiredColor(UiKitAttrs.colorPrimary),
                    colorTo = requireContext().requiredColor(UiAttrs.customInfo500)
                )
                tvTitle.text =
                    getString(R.string.label_title_offline_banner_monitoring_not_connected)
            }
        }
    }

    private fun draftState(): Boolean {
        with(binding) {
            // change {draftSize} to get the calculated value of the database draft data size
            val draftSize = 1
            if (draftSize > 0) {
                tvDesc.text = getString(
                    R.string.label_desc_offline_banner_monitoring,
                    draftSize.toString()
                )
                btnUpload.visible()
                return true
            } else {
                tvDesc.text = getString(R.string.label_desc_offline_banner_monitoring_empty)
                btnUpload.gone()
                return false
            }
        }
    }

    private fun animateColors(
        @ColorInt colorFrom: Int,
        @ColorInt colorTo: Int,
    ) {
        with(binding) {
            val animator = ObjectAnimator.ofObject(
                containerContent,
                "backgroundColor",
                ArgbEvaluator(),
                colorFrom,
                colorTo
            )
            animator.duration = 500
            animator.start()
        }
    }
}
