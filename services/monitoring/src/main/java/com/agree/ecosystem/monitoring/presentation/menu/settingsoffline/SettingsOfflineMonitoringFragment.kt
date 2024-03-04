package com.agree.ecosystem.monitoring.presentation.menu.settingsoffline

import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.FragmentSettingsOfflineMonitoringBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import com.agree.ecosystem.utilities.presentation.menu.dialog.DialogMessageFragment
import com.oratakashi.viewbinding.core.tools.gone
import com.oratakashi.viewbinding.core.tools.toast
import com.oratakashi.viewbinding.core.tools.visible

class SettingsOfflineMonitoringFragment :
    AgrFragment<FragmentSettingsOfflineMonitoringBinding>(),
    SettingsOfflineMonitoringDataContract {

    override fun initUI() {
        with(binding) {
            imgToolbarBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
            imgToolbarHelp.setOnClickListener {
                val title = getString(R.string.title_offline_help)
                val message = getString(R.string.message_offline_help)
                val positiveLabel = getString(R.string.label_yes_understand)
                DialogMessageFragment(title, message).apply {
                    setPositiveButton(Pair(positiveLabel, null))
                }.showNow(childFragmentManager, "dialogHelp")
            }
            rvSubAreaAvailable.adapter = adapterAvailable
            rvSubAreaDownloaded.adapter = adapterDownloaded

            // --------- data sample --------
            val data = mutableListOf<Pair<SubVessel, Boolean>>()
            for (n in 1..10) {
                val subVessel = tempSubVessel("Sub Area $n")
                val bool = n % 10 == 0
                val pair = Pair(subVessel, bool)
                data.add(pair)
            }
            // ------------------------------
            adapterAvailable.apply {
                clear()
                addOrUpdateAll(data)
                if (data.size >= 10) withFooter(true)
            }

            // --------- data sample --------
            val dataDownload = mutableListOf<SubVessel>()
            for (n in 1..7) {
                dataDownload.add(tempSubVessel("Sub Area $n Downloaded"))
            }
            // ------------------------------
            checkContainerDownloaded(dataDownload.size)
            adapterDownloaded.apply {
                clear()
                addOrUpdateAll(dataDownload)
                if (data.size >= 10) withFooter(true)
            }
        }
    }

    private fun checkContainerDownloaded(size: Int) {
        with(binding) {
            if (size <= 0) containerSubAreaDownloaded.gone()
            else containerSubAreaDownloaded.visible()
        }
    }

    // --------- data sample --------
    private fun tempSubVessel(text: String): SubVessel {
        return SubVessel(
            String(),
            String(),
            String(),
            String(),
            String(),
            String(),
            String(),
            String(),
            String(),
            String(),
            String(),
            text,
            String(),
            SubVessel.Status.ACTIVE,
            String(),
            String(),
            String(),
            String(),
            String(),
            String(),
            String(),
            String(),
            String(),
            String(),
            String(),
            String(),
            0.0,
            String(),
            String(),
            String(),
            true
        )
    }

    private var tempFinish = 0
    // ------------------------------

    private val adapterAvailable: SettingsOfflineMonitoringAvailableAdapter by lazy {
        SettingsOfflineMonitoringAvailableAdapter(
            onClickDownload = {
                adapterAvailable.setItemDownload(it)
                toast(it.subVesselName)
            },
            onClickLoadMore = {
                // --------- data sample --------
                adapterAvailable.setFooterWithLoading(true)
                val data = mutableListOf<Pair<SubVessel, Boolean>>()
                for (n in adapterAvailable.itemCount..(adapterAvailable.itemCount + 10)) {
                    val subVessel = tempSubVessel("Sub Area $n added")
                    val bool = n % 10 == 0
                    val pair = Pair(subVessel, bool)
                    data.add(pair)
                }
                adapterAvailable.addOrUpdateAll(data)
                adapterAvailable.setFooterWithLoading(false)

                if (tempFinish >= 2)
                    adapterAvailable.withFooter(false)
                tempFinish++
                // ------------------------------
            }
        )
    }
    private val adapterDownloaded: SettingsOfflineMonitoringDownloadedAdapter by lazy {
        SettingsOfflineMonitoringDownloadedAdapter(
            onClickRemove = { subVessel ->
                val title = getString(R.string.message_remove_sub_area, subVessel.subVesselName)
                val positiveLabel = getString(R.string.label_yes_remove)
                val negativeLabel = getString(R.string.label_back)
                DialogMessageFragment(title).apply {
                    setPositiveButton(
                        Pair(positiveLabel) {
                            adapterDownloaded.remove(subVessel)
                            adapterAvailable.add(Pair(subVessel, false))
                            checkContainerDownloaded(adapterDownloaded.itemCount - 1)
                        }
                    )
                    setNegativeButton(Pair(negativeLabel, null))
                }.showNow(childFragmentManager, "dialogRemoveSubArea")
            },
            onClickLoadMore = {}
        )
    }

    override fun fetchSubAreaAvailable() {
    }

    override fun fetchLoadMoreSubAreaAvailable() {
    }

    override fun fetchSubAreaDownloaded() {
    }
}
