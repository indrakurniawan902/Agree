package com.agree.ecosystem.partnership.domain.reqres.model.registrationstatus

import android.os.Parcelable
import androidx.annotation.Keep
import com.agree.ecosystem.utilities.domain.reqres.model.commodity.Commodity
import com.agree.ui.UiKitAttrs
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RegistrationStatus(
    val id: String,
    val companyId: String,
    val size: Double,
    val type: String,
    val status: Status,
    val address: String,
    val provinceId: String,
    val districtId: String,
    val subDistrictId: String,
    val villageId: String,
    val createdAt: String,
    val createdBy: String,
    val modifiedAt: String,
    val companyLogo: String,
    val companyName: String,
    val companyStatus: String,
    val commodity: List<Commodity>
) : Parcelable {
    enum class Status(val alias: String, val status: String, val backgroundColor: Int, val textColor: Int) {
        SUBMITTED(
            "Belum Diproses",
            "Sedang Diajukan",
            UiKitAttrs.black_100,
            UiKitAttrs.black_600
        ),
        ON_PROGRESS(
            "Berlangsung",
            "Sedang Diproses",
            UiKitAttrs.warning_100,
            UiKitAttrs.warning_normal
        ),
        ON_SURVEY(
            "Berlangsung",
            "Sedang Disurvei",
            UiKitAttrs.warning_100,
            UiKitAttrs.warning_normal
        ),
        ACCEPTED(
            "Selesai",
            "Diterima",
            UiKitAttrs.success_100,
            UiKitAttrs.success_pressed
        ),
        CANCELED(
            "Dibatalkan",
            "Dibatalkan",
            UiKitAttrs.error_100,
            UiKitAttrs.error_pressed
        ),
        REJECTED(
            "Selesai",
            "Ditolak",
            UiKitAttrs.success_100,
            UiKitAttrs.success_pressed
        )
    }
}
