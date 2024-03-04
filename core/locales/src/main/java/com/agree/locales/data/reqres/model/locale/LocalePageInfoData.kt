package com.agree.locales.data.reqres.model.locale

import com.google.gson.annotations.SerializedName

data class LocalePageInfoData(
    @field:SerializedName("isLastPage") val isLastPage: Boolean? = null,
    @field:SerializedName("pageSize") val pageSize: Int? = null,
    @field:SerializedName("totalRows") val totalRows: Int? = null,
    @field:SerializedName("page") val page: Int? = null,
    @field:SerializedName("isFirstPage") val isFirstPage: Boolean? = null
)
