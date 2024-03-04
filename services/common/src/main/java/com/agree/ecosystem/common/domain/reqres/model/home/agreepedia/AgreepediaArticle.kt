package com.agree.ecosystem.common.domain.reqres.model.home.agreepedia

import androidx.annotation.DrawableRes
import java.util.*

data class AgreepediaArticle(
    @DrawableRes val image: Int,
    val title: String,
    val author: String,
    val date: String
)
