package com.agree.ecosystem.core.utils.base

import com.agree.ecosystem.core.utils.domain.reqres.model.ValidationModel
import com.agree.ecosystem.core.utils.utility.validation.ValidationController
import com.agree.ecosystem.core.utils.utility.validation.viewmodels.ValidationView
import com.agree.ecosystem.core.utils.utility.validation.viewmodels.ValidationViewModel
import com.oratakashi.viewbinding.core.binding.list.arrayList
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

abstract class AgrFormBottomSheet :
    AgrBottomSheet(),
    ValidationController,
    ValidationView {

    override val forms: MutableList<MutableStateFlow<ValidationModel>> by arrayList()

    override val validation: ValidationViewModel by viewModel { parametersOf(this) }

    override fun initUI() {
        super.initUI()
        initForm()
    }

    abstract fun initForm()
}
