package com.agree.ecosystem.finances.utils

import androidx.annotation.LayoutRes
import com.agree.ecosystem.finances.R

enum class LoanSubmissionItemType(@LayoutRes val layoutRes: Int) {
    CHECKABLE_CULTIVATOR(R.layout.item_loan_submission_checkable_cultivator),
    DROPDOWN_LAND(R.layout.item_loan_submission_dropdown_land),
    CHECKABLE_LAND(R.layout.item_loan_submission_checkable_land),
    CHECKABLE_COLLATERAL(R.layout.item_loan_submission_checkable_collateral),
    ADD_COLLATERAL(R.layout.item_loan_submission_checkable_collateral), //(R.layout.item_loan_submission_add_collateral),
    NOMINAL(R.layout.item_loan_submission_nominal),
    GOODS(R.layout.item_loan_submission_goods)
}

enum class FinanceNameOfBundles(val param: String) {
    PARAM_TO_PROFILE_ACTIVITY("params")
}