package com.agree.ecosystem.finances.utils

import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.CheckableCultivator
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.CheckableLand
import com.agree.ecosystem.finances.domain.reqres.model.loansubmission.LoanSubmissionBaseItem

fun LoanSubmissionBaseItem.toCheckableCultivator() = this as CheckableCultivator
fun LoanSubmissionBaseItem.toCheckableLand() = this as CheckableLand

fun String?.parseCurrencyToLong(): Long {
    return this?.let { if (it.isNotBlank()) it.split('.').joinToString("").toLong() else 0 } ?: 0
}
