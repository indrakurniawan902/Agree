package com.agree.ecosystem.core.utils.domain.reqres.model

data class DataSpannable(
    val `data`: List<ListSpinnable>
) {
    data class ListSpinnable(
        val firstChar: Int,
        val lastChar: Int,
        val listener: () -> Unit
    )
}
