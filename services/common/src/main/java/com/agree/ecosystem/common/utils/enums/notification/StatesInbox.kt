package com.agree.ecosystem.common.utils.enums.notification

enum class StatesInbox(val value: String) {
    REGISTRATION("pendaftaran"),
    MONITORING_TODAY("Aktivitas Monitoring Hari Ini"),
    PAST_MONITORING("Aktivitas Terlewat"),
    LAST_REVIEW_DONE("Penilaian Akhir Selesai"),
    REJECTED_REGISTRATION("Pendaftaran Ditolak")
}
