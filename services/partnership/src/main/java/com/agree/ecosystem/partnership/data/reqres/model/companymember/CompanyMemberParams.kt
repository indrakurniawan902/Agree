package com.agree.ecosystem.partnership.data.reqres.model.companymember

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class CompanyMemberParams(
    val page: Int,
    val quantity: Int,
    val subSectorIds: List<String>,
    val userId: String
) : Parcelable {
    fun getSubSectorIds(): String? {
        return if (subSectorIds.isNotEmpty()) Gson().toJson(subSectorIds) else null
    }
}
