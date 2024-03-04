package com.agree.ecosystem.smartfarming.data.reqres.model

import com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming.ParameterTestData
import com.google.gson.annotations.SerializedName

data class ParameterTestDataItem(
    @field:SerializedName("created_by")
    val createdBy: String? = null,

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("form_schema")
    val formSchema: List<ParameterTestFormSchemaItem>? = mutableListOf(),

    @field:SerializedName("smartfarm_partner_device_name")
    val smartfarmPartnerDeviceName: String? = null,

    @field:SerializedName("smartfarm_partner_installment_date")
    val smartfarmPartnerInstallmentDate: String? = null,

    @field:SerializedName("smartfarm_partner_service_name")
    val smartfarmPartnerServiceName: String? = null,

    @field:SerializedName("value")
    val value: String? = null
) {
    fun toParameterTestData() : ParameterTestData {
        return ParameterTestData(
            createdBy = createdBy.orEmpty(),
            date = date.orEmpty(),
            formSchema = formSchema?.map { it.toParameterTestFormSchema() }.orEmpty(),
            smartfarmPartnerDeviceName = smartfarmPartnerDeviceName.orEmpty(),
            smartfarmPartnerInstallmentDate = smartfarmPartnerInstallmentDate.orEmpty(),
            smartfarmPartnerServiceName = smartfarmPartnerServiceName.orEmpty(),
            value = value.orEmpty()
        )
    }
}