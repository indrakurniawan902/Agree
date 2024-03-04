package com.agree.ecosystem.finances.presentation.menu.finance.cultivator.data.profilefarmer.dynamic

import com.agree.ecosystem.finances.domain.reqres.model.profilefarmer.FormFieldSchema

interface DynamicformInfoCultivatorDataContract {

    fun fetchDynamicDataCultivator(
        borrowerId: String,
        schemeName: String
    )

    fun dynamicDataCultivatorOnLoading()

    fun dynamicDataCultivatorOnSuccess(data: Map<String, FormFieldSchema>)

    fun dynamicDataCultivatorOnEmpty(data: Map<String, FormFieldSchema>?)

    fun dynamicDataCultivatorOnError(e: Throwable?)
}
