package com.agree.ecosystem.monitoring.utils

enum class MonitoringRecordType(val value: String?) {
    INDIVIDUAL("individual"),
    SUB_VESSEL("subvessel"),
    EMPTY(String()),
    OTHERS(null)
}
