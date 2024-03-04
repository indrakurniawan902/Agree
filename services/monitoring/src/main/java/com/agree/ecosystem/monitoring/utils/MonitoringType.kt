package com.agree.ecosystem.monitoring.utils

enum class MonitoringType(val value: String?) {
    ENTRY_POINT("entry-point"),
    SUMMARY("summary"),
    EMPTY(String()),
    OTHERS(null)
}
