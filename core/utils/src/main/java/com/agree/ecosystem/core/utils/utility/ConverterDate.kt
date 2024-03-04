package com.agree.ecosystem.core.utils.utility

enum class ConverterDate(val formatter: String) {
    TIME_ONLY("HH:mm aa"),
    FULL_DATE("dd MMMM yyyy"),
    FULL_DATE_TIME("dd MMMM yyyy HH:mm"),
    SHORT_DATE("dd MMM yyyy"),
    SIMPLE_DATE("dd/MM/yyyy"),
    SIMPLE_DATE_TIME("dd/MM/yyyy HH:mm"),
    SQL_DATE("yyyy-MM-dd"),
    SIMPLE_DAY("EEE"),
    SIMPLE_MONTH("MMM"),
    SIMPLE_DAY_MONTH("dd MMMM"),
    UTC2("yyyy-MM-dd'T'HH:mm:ss'Z'"),
    UTC1("yyyy-MM-dd'T'HH:mm:ss"),
    UTC3("yyyy-MM-dd'T'HH:mm:SSS'Z'"),
    UTC4("yyyy-MM-dd'T'HH:mm:ss.SSSSS'Z'"),
    UTC("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"),
    UTCDETAIL("yyyy-MM-dd'T'HH:mm:ss.SSS+HH:mm'Z'"),
    SIMPLE_DAY_DATE("EEEE, dd MMM yyyy"),
    SIMPLE_DATE_TIME_DOT("dd MMM yyyy • HH:mm"),
    YEAR_MONTH_ONLY("yyyy-MM"),
    SHORT_DAY_DATE("EEE, MMM dd"),
    DATE_ONLY("d"),
    SIMPLE_TIME("HH:mm")
}
