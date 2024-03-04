package com.agree.ecosystem.finances.utils

enum class LoanEligibilityError(val value: String) {
    INCOMPLETE_DATA("borrowerHasIncompleteDataGroup"),
    NO_ACTIVE_LAND("borrowerDoesNotHaveActiveLands"),
    HAS_ACTIVE_LOAN("borrowerHasActiveLoan")
}
