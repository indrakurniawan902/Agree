package com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ParameterTestData(
    val createdBy: String,
    val date: String,
    val formSchema: List<ParameterTestFormSchema>,
    val smartfarmPartnerDeviceName: String,
    val smartfarmPartnerInstallmentDate: String,
    val smartfarmPartnerServiceName: String,
    val value: String
) : Parcelable
