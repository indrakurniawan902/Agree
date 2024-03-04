package com.agree.ecosystem.monitoring.presentation.menu.cultivationactivities

import android.animation.ObjectAnimator
import android.view.View.ROTATION
import android.widget.TextView
import androidx.core.view.children
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.defaultErrorHandling
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.genericDialogErrorHandling
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.core.utils.utility.toString
import com.agree.ecosystem.monitoring.databinding.FragmentCultivitionActivitiesBinding
import com.agree.ecosystem.monitoring.databinding.ItemCalendarDayBinding
import com.agree.ecosystem.monitoring.databinding.LayoutEmptyItemCultivationActivityBinding
import com.agree.ecosystem.monitoring.databinding.LayoutErrorCalendarBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitySop
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitySopItemToDetailActivityParams
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.MonthItem
import com.agree.ecosystem.monitoring.domain.reqres.model.eventdotcalendar.EventDotCalendar
import com.agree.ecosystem.monitoring.domain.reqres.model.insertactivitysop.NavToAdditionalActivitySopParams
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVessel
import com.agree.ecosystem.monitoring.presentation.menu.cultivationactivities.calendar.DayViewBinder
import com.agree.ecosystem.monitoring.presentation.menu.cultivationactivities.dateinput.SelectMonthAdapter
import com.agree.ecosystem.monitoring.presentation.menu.cultivationactivities.dateinput.SelectYearBottomSheet
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselObserver
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselViewModel
import com.agree.ecosystem.monitoring.presentation.menu.phaseactivity.PhaseActivityBottomSheet
import com.agree.ecosystem.monitoring.presentation.navigation.ActivitySopNavigation
import com.agree.ecosystem.monitoring.utils.MonitoringRecordType
import com.devbase.utils.ext.invisible
import com.devbase.utils.ext.toDate
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.InDateStyle
import com.kizitonwose.calendarview.utils.Size
import com.kizitonwose.calendarview.utils.yearMonth
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding
import com.oratakashi.viewbinding.core.tools.visible
import com.telkom.legion.component.viewstate.ViewState
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showEmptyLayout
import com.telkom.legion.component.viewstate.showErrorLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Calendar
import java.util.Locale

class CultivationActivitiesFragment :
    AgrFragment<FragmentCultivitionActivitiesBinding>(),
    CultivationActivitiesDataContract {

    private lateinit var selectedDate: LocalDate
    private lateinit var selectedDay: LocalDate
    var eventCalendars: List<EventDotCalendar> = listOf()

    private val extend: MutableStateFlow<Boolean> by lazy {
        MutableStateFlow(false)
    }

    private lateinit var subVesselId: String
    var recordType: String = String()

    override fun initData() {
        super.initData()
        val argDate = requireActivity().intent.extras?.get("date").toString()
        selectedDate = if (argDate == "null") LocalDate.now() else LocalDate.parse(argDate)
        selectedDay = LocalDate.now()
        subVesselId = requireActivity().intent.getStringExtra("subVesselId").orEmpty()
        sharedVm.setSubVesselId(subVesselId)

        val payload = requireActivity().intent.getParcelableExtra<DetailSubVessel>("data")
        if (payload != null) {
            recordType = payload.recordType
            binding.exInfo.isExpanded = false
            sharedVm.setSubVessel(payload)
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(
            CultivationActivitiesObserver(this, viewModel), SubVesselObserver(this, sharedVm)
        )
        viewModel.getListMonth(selectedDate.monthValue)
    }

    override fun initUI() {
        super.initUI()
        settingCalendar()
        with(binding) {
            rvItemActivitySop.adapter = adapter
            rvItemMonth.adapter = monthAdapter

            tvDate.text = selectedDate.toString().toDate(ConverterDate.SQL_DATE.formatter)
                ?.toString(ConverterDate.SIMPLE_DAY_DATE).orEmpty()
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            tvYear.setOnClickListener {
                SelectYearBottomSheet(callback = {
                    if (extend.value) {
                        tvYear.text = selectedDate.withYear(it).year.toString()
                        calendarView.scrollToMonth(YearMonth.of(it, selectedDate.monthValue))
                    } else {
                        val firstDate = LocalDate.of(it, selectedDate.monthValue, 1)
                        calendarView.scrollToDate(firstDate)
                    }
                }).show(childFragmentManager, "dialog")
            }

            ivCalendarExpand.setOnClickListener {
                val firstDate =
                    calendarView.findFirstVisibleDay()?.date ?: return@setOnClickListener
                val lastDate = calendarView.findLastVisibleDay()?.date ?: return@setOnClickListener
                if (extend.value) {
                    calendarView.updateMonthConfiguration(
                        inDateStyle = InDateStyle.FIRST_MONTH,
                        maxRowCount = 1,
                        hasBoundaries = false
                    )

                    // +7 to make sure the date doesn't take a month from the previous month's date
                    if (firstDate.plusDays(7).yearMonth == selectedDate.yearMonth) {
                        calendarView.scrollToDate(selectedDate)
                    } else {
                        calendarView.scrollToDate(firstDate)
                    }
                    ObjectAnimator.ofFloat(ivCalendarExpand, ROTATION, 0f).setDuration(500).start()
                } else {
                    calendarView.updateMonthConfiguration(
                        inDateStyle = InDateStyle.ALL_MONTHS, maxRowCount = 6, hasBoundaries = true
                    )

                    when (selectedDate.yearMonth) {
                        firstDate.yearMonth -> {
                            calendarView.scrollToMonth(firstDate.yearMonth)
                        }
                        lastDate.yearMonth -> {
                            calendarView.scrollToMonth(lastDate.yearMonth)
                        }
                        else -> {
                            if (lastDate.yearMonth == firstDate.yearMonth) {
                                calendarView.scrollToMonth(firstDate.yearMonth)
                            } else {
                                calendarView.scrollToMonth(lastDate.yearMonth)
                            }
                        }
                    }

                    ObjectAnimator.ofFloat(ivCalendarExpand, ROTATION, 180f).setDuration(500)
                        .start()
                }
                extend.value = !extend.value
            }

            calendarView.monthScrollListener = {
                if (calendarView.maxRowCount == 6) {
                    viewModel.getListMonth(it.month)
                    tvYear.text = it.year.toString()
                    rvItemMonth.scrollToPosition(it.month - 1)
                } else {
                    val firstDate = it.weekDays.first().first().date
                    val lastDate = it.weekDays.last().last().date
                    if (firstDate.yearMonth >= lastDate.yearMonth) {
                        tvYear.text = firstDate.yearMonth.year.toString()
                        viewModel.getListMonth(firstDate.monthValue)
                        rvItemMonth.scrollToPosition(firstDate.monthValue - 1)
                    } else {
                        tvYear.text = lastDate.yearMonth.year.toString()
                        viewModel.getListMonth(lastDate.monthValue)
                        rvItemMonth.scrollToPosition(lastDate.monthValue - 1)
                    }
                }
            }

            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }

            tvHistory.setOnClickListener {
                extend.value = false
                activitySopNav.fromActivityInfoToHistory()
            }

            tvAddActivity.setOnClickListener {
                if (validateDate())
                    CultivationActivitiesBottomSheet().showNow(
                        childFragmentManager, "dialog"
                    )
                else
                    sharedVm.getSubVessel()?.let { dataDetailSubVesselToActivityParams ->
                        PhaseActivityBottomSheet(
                            dataDetailSubVesselToActivityParams, selectedDate.toString()
                        ) {}.showNow(
                            childFragmentManager, "dialog"
                        )
                    }
            }
        }
    }

    override fun onSubVesselIdChanged(id: String) {
        super.onSubVesselIdChanged(id)
        getActivities()
        getEventCalendar()
        getDetailSubVessel()
    }

    override fun getActivities() {
        if (sharedVm.getSubVesselId().isEmpty()) {
            return
        }

        viewModel.getListActivitySop(sharedVm.getSubVesselId(), selectedDate.toString(), sharedVm.getSubVessel()?.recordType) {
            defaultErrorHandling(this, it.orEmpty()) {
                getActivities()
            }
        }
    }

    override fun getEventCalendar() {
        if (sharedVm.getSubVesselId().isEmpty()) {
            return
        }
        viewModel.getEventDotCalendar(sharedVm.getSubVesselId()) {
            onEventCalendarEmpty()
        }
    }

    /**
     * Get Detail SubVessel Data when previous page not pass args
     */
    override fun getDetailSubVessel() {
        if (sharedVm.getSubVesselId().isEmpty() || sharedVm.getSubVessel() != null) {
            return
        }

        viewModel.getDetailSubVessel(sharedVm.getSubVesselId()) {
            genericDialogErrorHandling(this, it.orEmpty()) {
                getDetailSubVessel()
            }
        }
    }

    override fun onGetListActivitySuccess(data: List<ActivitySop>) {
        with(binding) {
            msvActivitySop.visible()
            if (recordType == MonitoringRecordType.INDIVIDUAL.value) {
                tvAddActivity.invisible()
            } else tvAddActivity.visible()
            msvActivitySop.showDefaultLayout()
            adapter.apply {
                adapter.clear()
                adapter.addOrUpdateAll(data)
            }
        }
    }

    fun validateDate(): Boolean {
        val dateToValidate = Calendar.getInstance()
        val currentDate = Calendar.getInstance()
        dateToValidate.set(selectedDate.year, selectedDate.monthValue, selectedDate.dayOfMonth)
        currentDate.set(
            LocalDate.now().year,
            LocalDate.now().monthValue,
            LocalDate.now().dayOfMonth
        )
        val dateInMilliseconds = dateToValidate.timeInMillis
        val currentTimeInMilliseconds = currentDate.timeInMillis
        return dateInMilliseconds > currentTimeInMilliseconds
    }

    override fun onGetListActivityEmpty() {
        with(binding) {
            tvAddActivity.invisible()
            msvActivitySop.showEmptyLayout()
            msvActivitySop.getView(ViewState.EMPTY)?.let {
                with(LayoutEmptyItemCultivationActivityBinding.bind(it)) {
                    sharedVm.getSubVessel()?.let { dataDetailSubVesselToActivityParams ->
                        btnAddActivity.setOnClickListener {
                            if (validateDate())
                                CultivationActivitiesBottomSheet().showNow(
                                    childFragmentManager,
                                    "dialog"
                                )
                            else
                                PhaseActivityBottomSheet(
                                    dataDetailSubVesselToActivityParams, selectedDate.toString()
                                ) {}.showNow(
                                    childFragmentManager, "dialog"
                                )
                        }
                    }
                }
            }
        }
    }

    override fun onGetListActivityLoading() {
        binding.msvActivitySop.showLoadingLayout()
    }

    override fun getListMonth(data: List<MonthItem>) {
        monthAdapter.apply {
            clear()
            addOrUpdateAll(data)
        }
    }

    override fun onEventCalendarLoading() {
        binding.msvCalendar.showLoadingLayout()
    }

    override fun onEventCalendarSuccess(data: List<EventDotCalendar>) {
        with(binding) {
            msvCalendar.showDefaultLayout()
            eventCalendars = data
            val dayBinder = DayViewBinder(requireContext(), data, selectedDate) { calendarDay ->
                selectViewDate(calendarDay)
            }
            calendarView.dayBinder = dayBinder
        }
    }

    override fun onEventCalendarEmpty() {
        with(binding) {
            msvCalendar.showErrorLayout()
            msvCalendar.getView(ViewState.ERROR)?.let {
                with(LayoutErrorCalendarBinding.bind(it)) {
                    btnRetry.setOnClickListener { getEventCalendar() }
                }
            }
        }
    }

    override fun onDetailSubVesselLoading() {
        binding.exInfo.isExpanded = true
    }

    override fun onDetailSubVesselSuccess(data: DetailSubVessel) {
        with(binding) {
            exInfo.isExpanded = false
            sharedVm.setSubVessel(data)
        }
    }

    fun settingCalendar() {
        with(binding) {
            try {
                val dayBinder =
                    DayViewBinder(requireContext(), eventCalendars, selectedDate) { calendarDay ->
                        selectViewDate(calendarDay)
                    }
                calendarView.dayBinder = dayBinder
                val currentMonth = YearMonth.now()
                val startMonth = currentMonth.minusYears(10).withMonth(1)
                val endMonth = currentMonth.plusYears(10).withMonth(12)
                val daysOfWeek = arrayOf(
                    DayOfWeek.SUNDAY,
                    DayOfWeek.MONDAY,
                    DayOfWeek.TUESDAY,
                    DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY,
                    DayOfWeek.FRIDAY,
                    DayOfWeek.SATURDAY
                )
                legendLayout.root.children.forEachIndexed { index, view ->
                    (view as TextView).apply {
                        text = daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale("id"))
                            .uppercase(Locale("id"))
                    }
                }
                calendarView.daySize = Size(
                    calendarView.daySize.width,
                    2 * calendarBinding.tvCalendarDay.layoutParams.height + calendarBinding.imgEventDotCalendar.layoutParams.height
                )

                calendarView.setup(startMonth, endMonth, WeekFields.of(Locale("id")).firstDayOfWeek)
                calendarView.scrollToMonth(YearMonth.from(selectedDate))
                calendarView.updateMonthConfiguration(
                    inDateStyle = InDateStyle.NONE, maxRowCount = 1, hasBoundaries = false
                )
                calendarView.scrollToDate(selectedDate)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun selectViewDate(day: CalendarDay) {
        with(binding) {
            if (day.date.yearMonth != selectedDate.yearMonth) {
                calendarView.scrollToMonth(day.date.yearMonth)
            }
            val currentSelection = selectedDate
            if (currentSelection == day.date) {
                calendarView.notifyDateChanged(selectedDate)
            } else {
                calendarView.notifyDateChanged(selectedDate)
                calendarView.notifyDateChanged(day.date)
            }
            selectedDate = day.date
            tvDate.text = day.date.toString(ConverterDate.SIMPLE_DAY_DATE)
            selectedDay = day.date
            getActivities()
        }
    }

    private val adapter: CultivationActivitiesAdapter by lazy {
        CultivationActivitiesAdapter {
            sharedVm.getSubVessel()?.let { detailSubVessel ->
                extend.value = false
                it?.let {
                    if (validateDate())
                        CultivationActivitiesBottomSheet().showNow(
                            childFragmentManager,
                            "dialog"
                        )
                    else {
                        if (it.isAdditional) {
                            activitySopNav.fromActivityInfoToInsertAdditionalActivity(
                                navParams = NavToAdditionalActivitySopParams(
                                    ActivitySopItemToDetailActivityParams(
                                        it, detailSubVessel
                                    ).getInsertActivitySopBundleParams(),
                                    phaseName = it.name,
                                    formSchema = null,
                                    isInsert = false,
                                    date = selectedDate.toString()
                                )
                            )
                        } else {
                            activitySopNav.fromActivityInfoToDetailActivity(
                                subVesselId,
                                ActivitySopItemToDetailActivityParams(
                                    it, detailSubVessel
                                ).getDetailActivityParams()
                            )
                        }
                    }
                }
            }
        }
    }

    private val monthAdapter: SelectMonthAdapter by lazy {
        SelectMonthAdapter(onItemClicked = {
            with(binding) {
                if (extend.value) {
                    calendarView.scrollToMonth(YearMonth.of(tvYear.text.toString().toInt(), it))
                } else {
                    val firstDate = LocalDate.of(tvYear.text.toString().toInt(), it, 1)
                    calendarView.scrollToDate(firstDate)
                }
            }
            viewModel.getListMonth(it)
        })
    }

    override fun onResume() {
        super.onResume()
        getActivities()
    }

    //    private var args: DetailSubVesselToActivityParams? = null
    private val emptyItemActivityBinding: LayoutEmptyItemCultivationActivityBinding by viewBinding()
    private val calendarBinding: ItemCalendarDayBinding by viewBinding()
    private val viewModel: CultivationActivitiesViewModel by viewModel()
    private val activitySopNav: ActivitySopNavigation by navigation { activity }
    private val sharedVm: SubVesselViewModel by sharedViewModel()
}
