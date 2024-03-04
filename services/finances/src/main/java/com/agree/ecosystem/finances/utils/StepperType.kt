package com.agree.ecosystem.finances.utils

import com.agree.ecosystem.finances.R
import com.devbase.utils.util.getStringResource

enum class StepperType(val value: String) {
    CULTIVATOR(getStringResource(R.string.label_borrower)),
    LAND(getStringResource(R.string.label_land)),
    COLLATERAL(getStringResource(R.string.label_collateral)),
    NOMINAL(getStringResource(R.string.label_nominal)),
    GOODS(getStringResource(R.string.label_item))
}