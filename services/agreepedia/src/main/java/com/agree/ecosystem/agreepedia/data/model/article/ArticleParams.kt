package com.agree.ecosystem.agreepedia.data.model.article

import androidx.annotation.Keep

@Keep
data class ArticleParams(
    val page: Int,
    val perPage: Int,
    val blogName: String,
    val orderDesc: String? = null ?: "change_date",
    val tag: String? = null,
    val oId: Long? = null
)
