package com.agree.ecosystem.core.utils.data.reqres.web.common

import com.devbase.data.source.web.WebService

class CommonApi(
    val api: CommonApiClient
) : CommonApiClient, WebService
