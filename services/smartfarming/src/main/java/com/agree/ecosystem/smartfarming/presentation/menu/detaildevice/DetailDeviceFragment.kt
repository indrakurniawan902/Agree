package com.agree.ecosystem.smartfarming.presentation.menu.detaildevice

import android.graphics.drawable.ColorDrawable
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.smartfarming.R
import com.agree.ecosystem.smartfarming.databinding.FragmentDetailDeviceBinding
import com.agree.ecosystem.smartfarming.domain.reqres.model.detailsubvessel.DetailSubVesselParams
import com.agree.ecosystem.smartfarming.presentation.menu.actuator.ActuatorFragment
import com.agree.ecosystem.smartfarming.presentation.menu.monitoring.MonitoringFragment
import com.devbase.presentation.viewpager.setup
import com.devbase.utils.util.getDrawableResource

internal class DetailDeviceFragment : AgrFragment<FragmentDetailDeviceBinding>() {

    private val detailSubVessel: DetailSubVesselParams? by lazy {
        requireActivity().intent.getParcelableExtra("data")
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            toolbar.text = detailSubVessel?.subVesselName.orEmpty()
            tvSubVesselName.text = detailSubVessel?.subVesselName.orEmpty()
            tvCultivatorName.text = detailSubVessel?.workerName.orEmpty()
            when (detailSubVessel?.subSectorId?.first()) {
                '1'  -> {
                    tvTitleInformation.text = getString(R.string.title_info_agriculture)
                    tvTitleSubVesselName.text =
                        getString(R.string.title_sub_vessel_name_agriculture)
                    tvTitleCultivatorName.text =
                        getString(R.string.title_cultivator_name_agriculture)
                }

                '2'  -> {
                    tvTitleInformation.text = getString(R.string.title_info_fishery)
                    tvTitleSubVesselName.text = getString(R.string.title_sub_vessel_name_fishery)
                    tvTitleCultivatorName.text = getString(R.string.title_cultivator_name_fishery)
                }

                else -> {
                    tvTitleInformation.text = getString(R.string.title_info_livestock)
                    tvTitleSubVesselName.text = getString(R.string.title_sub_vessel_name_livestock)
                    tvTitleCultivatorName.text = getString(R.string.title_cultivator_name_livestock)
                }
            }

            vpSmartFarming.adapter = object: FragmentStateAdapter(this@DetailDeviceFragment) {
                override fun getItemCount(): Int = 1

                override fun createFragment(position: Int) = MonitoringFragment()
            }
            tabSmartFarming.addTab(tabSmartFarming.newTab().apply {
                text = getString(R.string.label_smartfarming_monitoring)
                icon = getDrawableResource(R.drawable.ic_monitor) ?: ColorDrawable()
            })
            tabSmartFarming.addTab(tabSmartFarming.newTab().apply {
                text = getString(R.string.label_smartfarming_actuator)
                icon = getDrawableResource(R.drawable.ic_tool) ?: ColorDrawable()
                view.isClickable = false
            })

            /**
             * TODO: Uncomment if tab actuator need activate
             */
//            vpSmartFarming.setup {
//                withFragment(this@DetailDeviceFragment)
//                withListFragment(
//                    listOf(
//                        MonitoringFragment(),
//                        ActuatorFragment()
//                    )
//                )
//                bindWithTabLayout(tabSmartFarming) {
//                    withListTitles(
//                        listOf(
//                            getString(R.string.label_smartfarming_monitoring),
//                            getString(R.string.label_smartfarming_actuator)
//                        )
//                    )
//                    withListIcon(
//                        listOf(
//                            getDrawableResource(R.drawable.ic_monitor) ?: ColorDrawable(),
//                            getDrawableResource(R.drawable.ic_tool) ?: ColorDrawable()
//                        )
//                    )
//                }
//            }
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.setBackButtonOnClick {
                requireActivity().onBackPressed()
            }
        }
    }

}