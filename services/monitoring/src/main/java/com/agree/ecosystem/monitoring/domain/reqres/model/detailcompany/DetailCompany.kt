package com.agree.ecosystem.monitoring.domain.reqres.model.detailcompany

import android.os.Parcelable
import androidx.annotation.Keep
import com.agree.ecosystem.monitoring.data.reqres.model.detailcompany.CommodityItem
import kotlinx.parcelize.Parcelize

data class DetailCompany(
    val commodity: List<CommodityItem>,
)

@Keep
@Parcelize
data class Commodity(
    val commodityId: String,
    val productType: List<String>,
) : Parcelable
