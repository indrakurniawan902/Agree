package com.agree.ecosystem.core.utils.utility.validation.rules

import com.devbase.presentation.validation.rules.RegexRule

fun mobileNumberOnlyRule(errorMessage: String) =
    RegexRule("^(\\+62|62)?[\\s-]?0?8[0-9]{1}\\d{1}[\\s-]?\\d{4}[\\s-]?\\d{2,6}\$", errorMessage)

fun usernameRule(errorMessage: String) =
    RegexRule("^[a-zA-Z0-9]{5,13}$", errorMessage)

fun minLengthRule(minLength: Int, errorMessage: String) =
    RegexRule("^.{$minLength,}\$", errorMessage)

fun emptyIgnore(errorMessage: String) =
    RegexRule("^(\\s|\\S)*(\\S)+(\\s|\\S)*\$", errorMessage)

fun minLengthLetterNumberRule(minLength: Int, errorMessage: String) =
    RegexRule("^(?=.*[A-Za-z])(?=.*?[0-9]).{$minLength,}\$", errorMessage)
