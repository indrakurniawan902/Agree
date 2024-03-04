package com.agree.ecosystem.finances.presentation.menu.finance.loanpackage.applyloan

import android.widget.LinearLayout
import android.widget.TextView
import com.agree.ecosystem.core.utils.utility.extension.immutable
import com.agree.ecosystem.core.utils.utility.extension.setHandler
import com.agree.ecosystem.finances.data.reqres.model.ApplyLoanBodyPost
import com.agree.ecosystem.finances.data.reqres.model.LoanItemList
import com.agree.ecosystem.finances.data.reqres.model.LoanRequestedData
import com.agree.ecosystem.finances.domain.reqres.FinanceUsecase
import com.agree.ecosystem.finances.domain.reqres.model.DetailLoanPackage
import com.agree.ecosystem.finances.domain.reqres.model.HasLoanData
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.ApplyLoanCultivator
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.CheckableCollateral
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.CheckableLand
import com.agree.ecosystem.finances.utils.StepperType
import com.devbase.data.source.DevData
import com.devbase.data.utilities.DevViewModel
import com.devbase.utils.ext.isNull
import com.google.gson.JsonElement
import kotlinx.coroutines.flow.MutableStateFlow

class LoanSubmissionViewModel(private val usecase: FinanceUsecase) :
    DevViewModel() {

    private val _selectedCultivator: MutableStateFlow<ArrayList<ApplyLoanCultivator>?> by lazy {
        MutableStateFlow(arrayListOf())
    }
    val selectedCultivator = _selectedCultivator.immutable()

    private val _bnTncChecked: MutableStateFlow<Boolean> by lazy {
        MutableStateFlow(false)
    }

    private val bnTChecked = _bnTncChecked

    private val _companyId: MutableStateFlow<String> by lazy {
        MutableStateFlow("")
    }

    private val _mitraId: MutableStateFlow<String> by lazy {
        MutableStateFlow("")
    }

    val companyId = _companyId
    val mitraId = _mitraId

    val _selectedCollateral: MutableStateFlow<ArrayList<CheckableCollateral>?> by lazy {
        MutableStateFlow(arrayListOf())
    }
    var selectedCollateral = _selectedCollateral.immutable()

    val bnTncChecked = _bnTncChecked.immutable()

    fun setTnc(value: Boolean) {
        _bnTncChecked.value = value
    }

    private val _selectedLand: MutableStateFlow<ArrayList<CheckableLand>?> by lazy {
        MutableStateFlow(arrayListOf())
    }
    val selectedLand = _selectedLand.immutable()

    private val _selectedLandState: MutableStateFlow<Boolean> by lazy {
        MutableStateFlow(false)
    }
    val selectedLandState get() = _selectedLandState

    private val _landCount: MutableStateFlow<MutableMap<String, Int>?> by lazy {
        MutableStateFlow(mutableMapOf())
    }
    val landCount get() = _landCount.immutable()

    private val _datas = DevData<List<ApplyLoanCultivator>>().apply { default() }
    val datas get() = _datas.immutable()

    private val _hasLoan = DevData<HasLoanData>().apply { default() }
    val hasLoan get() = _hasLoan

    private val _stepperList: MutableStateFlow<List<StepperType>> by lazy {
        MutableStateFlow(arrayListOf())
    }
    val stepperList = _stepperList.immutable()

    private val _nominal: MutableStateFlow<MutableMap<Int, String>> by lazy {
        MutableStateFlow(mutableMapOf())
    }
    val nominal = _nominal.immutable()

    private val _totalNominal: MutableStateFlow<MutableMap<Int, Long>?> by lazy {
        MutableStateFlow(mutableMapOf())
    }
    val totalNominal = _totalNominal.immutable()

    private val _loanItemList: MutableStateFlow<MutableMap<Int, LoanItemList>?> by lazy {
        MutableStateFlow(mutableMapOf())
    }

    val loanItemList = _loanItemList

    private val _dropdownLandElements: MutableStateFlow<MutableMap<String, Triple<LinearLayout, TextView, Boolean>>?> by lazy {
        MutableStateFlow(mutableMapOf())
    }
    val dropdownLandElements get() = _dropdownLandElements.immutable()

    lateinit var detailLoanPackage: DetailLoanPackage

    private val _applyLoanResult = DevData<JsonElement>().apply { default() }
    val applyLoanResult = _applyLoanResult

    fun registerDropdownElement(key: String, data: Triple<LinearLayout, TextView, Boolean>) {
        _dropdownLandElements.value?.set(key, data)
        _landCount.value?.set(key, _landCount.value?.get(key) ?: 0)
    }

    fun incrementCount(item: CheckableLand, count: Int) {
        _landCount.value?.set(item.farmerId, count)
    }

    fun decrementCount(item: CheckableLand, count: Int) {
        _landCount.value?.set(item.farmerId, count)
    }

    //    lateinit var detailLoanPackage: DetailLoanPackage
    fun updateSelectedCultivator(
        data: ApplyLoanCultivator,
        isChecked: Boolean,
        isSingleCultivator: Boolean
    ) {
        val cultivatorList = _selectedCultivator.value ?: ArrayList()

        val cultivator = cultivatorList.find {
            it.farmerId.equals(data.farmerId, ignoreCase = true)
        }

        if (isSingleCultivator) {
            cultivatorList.clear()
            if (isChecked) cultivatorList.add(data)
        } else {
            if (cultivator.isNull()) {
                cultivatorList.add(data)
            } else {
                cultivatorList.remove(cultivator)
            }
        }
        _selectedCultivator.value = cultivatorList
    }

    fun updateSelectedLand(item: CheckableLand) {
        val selectedLandList = _selectedLand.value ?: ArrayList()
        val pool = _selectedLand.value?.find {
            it.data.plantingSeasonId.equals(item.data.plantingSeasonId, ignoreCase = true)
        }

        if (pool.isNull()) selectedLandList.add(item) else selectedLandList.remove(pool)
        _selectedLand.value = selectedLandList
    }

    fun setStepperList(list: List<StepperType>) {
        _stepperList.value = list
    }

    fun checkStepperList(t: List<ApplyLoanCultivator>) {
        _stepperList.value = when (t.get(0).collateralData) {
            null -> {
                if (_stepperList.value.contains(StepperType.GOODS) == true) {
                    listOf(
                        StepperType.CULTIVATOR,
                        StepperType.LAND,
                        StepperType.GOODS
                    )
                } else {
                    listOf(
                        StepperType.CULTIVATOR,
                        StepperType.LAND,
                        StepperType.NOMINAL
                    )
                }
            }

            else -> {
                if (_stepperList.value.contains(StepperType.GOODS) == true) {
                    listOf(
                        StepperType.CULTIVATOR,
                        StepperType.COLLATERAL,
                        StepperType.LAND,
                        StepperType.GOODS
                    )
                } else {
                    listOf(
                        StepperType.CULTIVATOR,
                        StepperType.COLLATERAL,
                        StepperType.LAND,
                        StepperType.NOMINAL
                    )
                }
            }
        }
    }

    fun isSelectedLandValid(): Boolean {
        var isValid = true
        val selectedLandFarmerIds = selectedLandByFarmerId()
        _selectedCultivator.value?.forEach {
            if (!selectedLandFarmerIds.contains(it.farmerId)) {
                isValid = false
            }
        }
        _selectedLandState.value = isValid
        return isValid
    }

    private fun selectedLandByFarmerId(): List<String> {
        return _selectedLand.value!!.map { it.farmerId }
    }

    fun fetchCheckMemberEligibility(
        mitraId: String,
        loanPackageId: String
    ) {
        usecase.fetchCheckMemberEliginility(mitraId, loanPackageId)
            .setHandler(_datas).let { return@let disposable::add }
    }

    fun checkHasLoanData(borrowerId: String) {
        usecase.checkIfHasLoan(borrowerId)
            .setHandler(_hasLoan).let { return@let disposable::add }
    }

    fun setNominal(position: Int, data: String) {
        _nominal.value.set(position, data)
    }

    fun setTotalNominal(position: Int, data: Long) {
        _totalNominal.value?.set(position, data)
    }

    fun setLoanItemList(position: Int, data: LoanItemList) {
        _loanItemList.value = mutableMapOf(position to data)
    }

    fun resetNominalandTotalNominal() {
        _nominal.value = mutableMapOf()
        _totalNominal.value = null
    }

    fun initLoanPackage(data: DetailLoanPackage?) {
        if (data != null) {
            detailLoanPackage = data
        }
    }

    fun submitLoan(userId: String) {
        val loanRequestedData =
            _selectedCultivator.value?.mapIndexed { index, applyLoanCultivator ->
                LoanRequestedData(
                    loanRequestedAmount = _totalNominal.value?.get(index)?.toDouble()
                        ?: 0.toDouble(),
                    loanItemList = _loanItemList.value?.values?.toList() ?: listOf(),
                    loanMitraId = _mitraId.value,
                    loanPlantingSeasonIds = _selectedLand.value?.filter { it.farmerId == applyLoanCultivator.farmerId }
                        ?.map { it.data.plantingSeasonId } ?: listOf(),
                    loanCollateralId = _selectedCollateral.value?.find { collateral -> collateral.farmerId == applyLoanCultivator.farmerId }?.data?.collateralId,
                    loanBorrowerId = applyLoanCultivator.borrowerId,
                    loanFarmerId = applyLoanCultivator.farmerId,
                    loanCompanyId = _companyId.value
                )
            }
        applyLoan(userId, loanRequestedData!!)
    }

    fun setMitraAndCompanyId(companyId: String?, mitraId: String?) {
        _companyId.value = companyId ?: ""
        _mitraId.value = mitraId ?: ""
    }

    fun getSumTotalNominal() = _totalNominal.value?.values?.sum()?.toDouble() ?: 0.0
    fun applyLoan(userId: String, loanRequestedData: List<LoanRequestedData>) {
        val body = ApplyLoanBodyPost(
            loanUserId = userId,
            loanPackageId = detailLoanPackage.loanPackageId,
            loanRequestedData = loanRequestedData,
            loanSubmissionType = detailLoanPackage.loanPackagePaymentType
        )
        usecase.submitLoan(body)
            .setHandler(_applyLoanResult)
            .let { return@let disposable::add }

    }
}
