package com.agree.ecosystem.common.domain.reqres.model.notification

data class UpdateInbox(
    val data: Int,
    val metaData: String,
    val error: String,
    val count: Int
)
