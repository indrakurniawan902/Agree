package com.agree.locales.data.reqres.model.locale

import com.google.gson.annotations.SerializedName

data class LocaleResponseData(
    @field:SerializedName("pageInfo") val pageInfo: LocalePageInfoData? = null,
    @field:SerializedName("list") val list: List<LocaleData>? = null
)
