package com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop

import android.os.Bundle
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.monitoring.databinding.FragmentDetailActivitySopBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.SopActivityDetail
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.ValidationActivityDetail
import com.agree.ecosystem.monitoring.domain.reqres.model.insertactivitysop.InsertActivitySopBundleParams
import com.agree.ecosystem.monitoring.presentation.navigation.params.DetailActivityParams
import com.agree.ecosystem.monitoring.utils.Constant
import com.agree.ecosystem.monitoring.utils.MonitoringRecordType
import com.agree.ecosystem.monitoring.utils.MonitoringType
import com.agree.ui.UiKitDimens
import com.devbase.presentation.viewpager.setup
import com.devbase.utils.ext.isNotNull
import com.oratakashi.viewbinding.core.tools.gone
import com.telkom.legion.component.viewstate.showDefaultLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivitySopFragment :
    AgrFragment<FragmentDetailActivitySopBinding>(),
    DetailActivitySopDataContract {

    var date: String = ""
    var activityName = ""
    var idBundle: InsertActivitySopBundleParams? = null
    var guideContent: String = ""
    var recordType: String = String()
    var type: String = String()
    var isEdit: Boolean = false

    override fun initData() {
        super.initData()
        val payload = requireActivity().intent.getParcelableExtra<DetailActivityParams>("data")

        if (payload != null) {
            idBundle = payload.idBundleParams
            date = payload.date
            activityName = payload.activityName
            guideContent = payload.guideContent
            recordType = payload.recordType
            type = payload.type
        }
    }

    override fun initUI() {
        super.initUI()
        val output: String = if (activityName.length > 29) activityName.substring(
            0,
            29 - 1
        ) + ("...")
        else
            activityName

        with(binding) {
            toolbar.text = output
            tvIndividualId.apply {
                isVisible = args.individualSubVessel.isNotNull()
                text = args.individualSubVessel?.code
            }
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(DetailActivitySopObserver(this, viewModel))
        if (recordType == MonitoringRecordType.INDIVIDUAL.value &&
            type == MonitoringType.ENTRY_POINT.value
        ) {
            val subVesselId = idBundle?.subVesselId.toString()
            getValidationActivityDetail(subVesselId)
        } else getActivityDetailData()
    }

    override fun initAction() {
        super.initAction()
        binding.toolbar.setBackButtonOnClick {
            requireActivity().onBackPressed()
        }
    }

    override fun initViewPager(data: List<SopActivityDetail>) {
        super.initViewPager(data)
        val fragmentList = mutableListOf<Fragment>()
        val titleList = mutableListOf<String>()

        data.forEachIndexed { index, element ->
            val bundle = Bundle()
            bundle.putParcelable(Constant.SOP_DETAIL_KEY, element)
            bundle.putString(Constant.GUIDE_CONTENT_BUNDLE_KEY, guideContent)
            bundle.putString(Constant.DATE_BUNDLE_KEY, date)
            idBundle?.let {
                bundle.putParcelable(Constant.ID_BUNDLE_KEY, it)
            }
            bundle.putString(Constant.ACTIVITY_NAME_BUNDLE_KEY, activityName)
            bundle.putString(Constant.INDIVIDUAL_ID, args.individualSubVessel?.id)
            bundle.putString(Constant.RECORD_TYPE_BUNDLE_KEY, recordType)
            bundle.putString(Constant.TYPE_BUNDLE_KEY, type)
            bundle.putBoolean(Constant.IS_EDIT_BUNDLE_KEY, isEdit)
            val formFragment = DetailActivitySopFormFragment()
            formFragment.arguments = bundle

            fragmentList.add(formFragment)
            titleList.add("Frekuensi ${index + 1}")
        }

        binding.vpFrekuensi.setup {
            withFragment(this@DetailActivitySopFragment)
            withListFragment(fragmentList)

            if (data.size == 1) {
                binding.tabLayout.gone()
            } else {
                bindWithTabLayout(binding.tabLayout) {
                    withListTitles(titleList)
                }
            }
        }
        binding.tabLayout.apply {
            tabRippleColor = null
            for (i in 0 until tabCount) {
                val tab = (getChildAt(0) as ViewGroup).getChildAt(i)
                (tab.layoutParams as ViewGroup.MarginLayoutParams).setMargins(
                    resources.getDimension(
                        UiKitDimens.dimen_4dp
                    ).toInt(),
                    0,
                    resources.getDimension(UiKitDimens.dimen_4dp).toInt(),
                    0
                )
                tab.requestLayout()
            }
        }
    }

    override fun getActivityDetailData() {
        idBundle?.let {
            val phaseActivityId = it.phaseActivityId
            val subVesselId = it.subVesselId
            val individualId = args.individualSubVessel?.id
            viewModel.getActivityDetailData(phaseActivityId, date, subVesselId, individualId)
        }
    }

    override fun onGetSopActivityDetailLoading() {
        super.onGetSopActivityDetailLoading()
        // binding.msvDetailForm.showLoadingLayout()
    }

    override fun onGetSopActivityDetailSuccess(data: List<SopActivityDetail>) {
        super.onGetSopActivityDetailSuccess(data)
        binding.msvDetailForm.showDefaultLayout()
        initViewPager(data)
    }

    override fun onGetSopActivityDetailError(message: String?) {
        super.onGetSopActivityDetailError(message)
        // Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun getValidationActivityDetail(subVesselId: String) {
        viewModel.getValidationActivityDetail(subVesselId)
    }

    override fun getValidationActivityDetailSuccess(data: ValidationActivityDetail) {
        isEdit = data.is_edit
    }

    override fun getValidationActivityDetailError(message: String?) {
        super.getValidationActivityDetailError(message)
    }

    override fun getValidationActivityDetailFinish() {
        getActivityDetailData()
    }

    private val viewModel: DetailActivitySopViewModel by viewModel()
    private val args: DetailActivitySopFragmentArgs by navArgs()
}
