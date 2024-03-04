package com.agree.ecosystem.core.utils.domain.reqres

import com.agree.ecosystem.core.utils.data.reqres.CommonRepository

class CommonInteractor(
    private val repo: CommonRepository
) : CommonUsecase
