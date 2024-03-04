package com.agree.ecosystem.smartfarming.presentation.menu.monitoring

import android.annotation.SuppressLint
import android.os.Parcel
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.DialogUtils
import com.agree.ecosystem.core.utils.utility.convertUTCTimezone
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.toDateString
import com.agree.ecosystem.core.utils.utility.toString
import com.agree.ecosystem.smartfarming.R
import com.agree.ecosystem.smartfarming.data.reqres.model.UpdateReadStatusRequestBody
import com.agree.ecosystem.smartfarming.data.reqres.model.devicehsitory.DeviceHistoryQuery
import com.agree.ecosystem.smartfarming.databinding.FragmentSmartfarmingMonitoringBinding
import com.agree.ecosystem.smartfarming.databinding.LayoutErrorDevicesBinding
import com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming.ParameterTestData
import com.agree.ecosystem.smartfarming.domain.reqres.model.detailsubvessel.DetailSubVesselParams
import com.agree.ecosystem.smartfarming.domain.reqres.model.devicehistory.DeviceHistory
import com.agree.ecosystem.smartfarming.domain.reqres.model.tools.Device
import com.agree.ecosystem.smartfarming.presentation.menu.monitoring.detailtestparameter.TestParameterDialogFragment
import com.agree.ecosystem.smartfarming.presentation.menu.monitoring.devices.DevicesSelectorDialogFragment
import com.agree.ui.snackbar.errorSnackBar
import com.devbase.utils.ext.gone
import com.devbase.utils.ext.isNotNull
import com.devbase.utils.ext.visible
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.telkom.legion.component.utility.extension.setLegionDivider
import com.telkom.legion.component.viewstate.ViewState
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showErrorLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar
import java.util.Date

internal class MonitoringFragment : AgrFragment<FragmentSmartfarmingMonitoringBinding>(),
    MonitoringDataContract {

    private val detailSubVessel: DetailSubVesselParams? by lazy {
        requireActivity().intent.getParcelableExtra("data")
    }

    private val adapter: MonitoringAdapter by lazy {
        MonitoringAdapter(){ parameterTest ->
            parameterTest?.let {
                TestParameterDialogFragment(it).show(childFragmentManager,"bottomsheet_recom")
                parameterTestKey = parameterTest.key
                if (!parameterTest.isRead) {
                    fetchUpdateReadStatus()
                }
            }
        }
    }

    private val listDevice: MutableList<Device> by lazy {
        ArrayList()
    }

    private var smartfarmPartnerDeviceId: String = ""

    private var parameterTestKey: String = ""

    private var date: String = ""

    override fun initObserver() {
        super.initObserver()
        addObservers(MonitoringObserver(this, viewModel))
        onFetchListDevice()
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvTestParameters.adapter = adapter
            rvTestParameters.setLegionDivider()
            btnChangeTools.setOnClickListener {

                DevicesSelectorDialogFragment(listDevice, viewModel.selectedDevice.value) {
                    it?.let { device ->
                        viewModel.setSelectedDevice(device)
                        smartfarmPartnerDeviceId = device.deviceId
                        tvDeviceName.text = device.deviceName.uppercase()
                        tvServiceName.text = device.serviceName
                        imgTools.url = device.deviceImage
                        fetchTestParameters()
                    }
                }.show(childFragmentManager, "dialog")
            }
            cvDate.setOnClickListener {
                fetchDeviceHistory()
            }
        }
    }

    override fun fetchTestParameters(date: String?) {
        viewModel.getParameterTest(detailSubVessel?.id.orEmpty(), "user", smartfarmPartnerDeviceId, date)
    }

    override fun fetchLastUpdate() {
        fetchTestParameters()
    }

    override fun fetchDeviceHistory() {
        viewModel.getDeviceHistoryData(
            Date().toString(ConverterDate.YEAR_MONTH_ONLY),
            DeviceHistoryQuery(detailSubVessel?.id.orEmpty(), smartfarmPartnerDeviceId)
        )
    }

    override fun onGetLastUpdateLoading() {

    }

    override fun onGetLastUpdateSuccess() {

    }

    override fun onGetLastUpdateError(t: Throwable?) {

    }

    override fun onGetDeviceHistoryLoading() {

    }

    override fun onGetDeviceHistorySuccess(data: List<DeviceHistory>) {
        val today = MaterialDatePicker.todayInUtcMilliseconds()
        val calendar = Calendar.getInstance()

        calendar.timeInMillis = today
        calendar[Calendar.DAY_OF_MONTH] = data.first().day
        val startDate = calendar.timeInMillis

        val picker = MaterialDatePicker.Builder
            .datePicker()
            .setSelection(startDate)
            .setNegativeButtonText(R.string.action_cancel)
            .setPositiveButtonText(R.string.action_select)
            .setCalendarConstraints(
                CalendarConstraints.Builder()
                    .setValidator(object : CalendarConstraints.DateValidator {
                        override fun describeContents(): Int = 0

                        override fun writeToParcel(p0: Parcel, p1: Int) {}

                        override fun isValid(date: Long): Boolean {
                            calendar.timeInMillis = today
                            val dates = data.map {
                                calendar[Calendar.DAY_OF_MONTH] = it.day
                                calendar.timeInMillis
                            }
                            return dates.find { it == date }.isNotNull()
                        }

                    }).build()
            )
            .build()
        picker.addOnPositiveButtonClickListener { longDate ->
            data.find { it.day.toString() == longDate.toDateString(ConverterDate.DATE_ONLY) }?.data?.let {
                showTimePickerDialog(it.map { times -> times.createdAt })
            }
        }
        picker.show(childFragmentManager, "MaterialDatePicker")
    }

    override fun onGetDeviceHistoryError(t: Throwable?) {
        errorSnackBar(t?.message.toString())
    }

    override fun onGetTestParametersLoading() {
        with(binding) {
            msvParameterTest.showLoadingLayout()
            cvDate.gone()
        }
        showAlertNotification(false)
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onGetTestParametersSuccess(data: ParameterTestData) {
        with(binding) {
            msvParameterTest.showDefaultLayout()
            adapter.clear()
            adapter.addAll(data.formSchema)
            adapter.notifyDataSetChanged()
            adapter.calculateUnreadCount()
            cvDate.visible()
            tvDate.text = data.date.convertUTCTimezone(ConverterDate.FULL_DATE, false)
            tvTime.text = "(${data.date.convertUTCTimezone(ConverterDate.SIMPLE_TIME, false)})"
            date = data.date
        }
        showAlertNotification(true, adapter.getUnreadCount())
    }

    override fun onGetTestParametersError(t: Throwable?) {
        binding.msvParameterTest.showErrorLayout()
        showAlertNotification(false)
    }

    @SuppressLint("SetTextI18n")
    override fun showTimePickerDialog(data: List<String>) {
        DialogUtils.showCustomTimePickerDialog(
            requireContext(),
            data.firstOrNull()?.convertUTCTimezone(ConverterDate.SHORT_DAY_DATE, false).orEmpty(),
            data.map {
                it.convertUTCTimezone(ConverterDate.SIMPLE_TIME, false)
            },
            { time ->
                if (time.isEmpty()) return@showCustomTimePickerDialog
                val selectedDate = data.find { it.convertUTCTimezone(ConverterDate.SIMPLE_TIME, false) == time }
                fetchTestParameters(selectedDate)
                with(binding) {
                    tvDate.text = data.firstOrNull()?.convertUTCTimezone(ConverterDate.FULL_DATE, false).orEmpty()
                    tvTime.text = "($time)"
                }
            },
            {}
        )
    }

    override fun fetchUpdateReadStatus() {
        val body = UpdateReadStatusRequestBody(
            subVesselId = detailSubVessel?.id.orEmpty(),
            smartFarmPartnerDeviceId = smartfarmPartnerDeviceId,
            date = date,
            key = parameterTestKey
        )
        viewModel.updateReadStatus(body)
    }

    override fun onUpdateReadStatusLoading() {

    }

    override fun onUpdateReadStatusSuccess() {
        fetchTestParameters()
    }

    override fun onUpdateReadStatusError(t: Throwable?) {

    }


    override fun onFetchListDevice() {
        viewModel.getListDevice(detailSubVessel?.id.orEmpty())
    }

    override fun onGetListDeviceLoading() {
        binding.msvDevice.showLoadingLayout()
    }

    override fun onGetListDeviceSuccess(data: List<Device>) {
        listDevice.addAll(data)
        with(binding) {
            msvDevice.showDefaultLayout()
            if (tvDeviceName.text.toString().isEmpty()) {
                tvDeviceName.text = listDevice[0].deviceName.uppercase()
                tvServiceName.text = listDevice[0].serviceName
            }

            smartfarmPartnerDeviceId = listDevice[0].deviceId
            imgTools.url = listDevice[0].deviceImage
            fetchLastUpdate()
        }
    }


    override fun onGetListDeviceError(t: Throwable?) {
        with(binding) {
            msvDevice.showErrorLayout()
            msvParameterTest.showErrorLayout()
            cvDate.gone()
            with(LayoutErrorDevicesBinding.bind(msvDevice.getView(ViewState.ERROR))) {
                alertDevices.setOnActionClickListener {
                    onFetchListDevice()
                }
            }
        }
    }

    private fun showAlertNotification(state: Boolean, unreadCount: Int = 0) {
        with(binding){
            alertNotification.apply {
                if (state && unreadCount > 0) {
                    description = "Ada $unreadCount Pesan belum dibuka pada parameter di bawah, segera dicek yang bertanda"
                    visible()
                } else {
                    gone()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    private val viewModel: MonitoringViewModel by viewModel()

}