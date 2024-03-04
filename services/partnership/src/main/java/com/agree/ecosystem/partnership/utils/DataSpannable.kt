package com.agree.ecosystem.partnership.utils

data class DataSpannable(
    val `data`: List<ListSpinnable>
) {
    data class ListSpinnable(
        val firstChar: Int,
        val lastChar: Int,
        val listener: () -> Unit
    )
}
