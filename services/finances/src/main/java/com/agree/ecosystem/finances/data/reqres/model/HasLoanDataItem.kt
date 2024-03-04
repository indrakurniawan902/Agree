package com.agree.ecosystem.finances.data.reqres.model

import com.agree.ecosystem.finances.domain.reqres.model.HasLoanData
import com.google.gson.annotations.SerializedName

data class HasLoanDataItem(
    @field:SerializedName("borrowerHasLoan")
    val borrowerHasLoan: Boolean = false
) {
    fun toHasLoanData(): HasLoanData = HasLoanData(borrowerHasLoan)
}