package com.agree.ecosystem.finances.domain.reqres.model.profilefarmer

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class FormFieldSchema(
    val componentType: String = "",
    val disabled: Boolean = false,
    val label: String = "",
    val fieldType: String = "",
    val value: String = "",
    val required: Boolean = false,
    val maxLength: Int = 0,
    val information: Boolean = false,
    val keyboardType: String = "",
    val title: String = "",
    val description: String = "",
    val suffix: String = "",
    val prefix: String = "",
    val delimiter: String = "",
    val translatable: Boolean = true,
    val options: List<OptionsSchema> = emptyList(),
    val listImages: List<String> = emptyList()
) : Parcelable

@Keep
@Parcelize
data class OptionsSchema(
    override val id: String,
    override val displayText: String,
    var provinceId: String = "",
    var districtId: String = "",
    var district: String = "",
    var subDistrictId: String = "",
    var subDistrict: String = "",
    var villageId: String = "",
    var village: String = "",
    var economicSectorId: String = "",
    var economicSubSectorId: String = "",
    var economicSubSector: String = "",
    var economicNewSubSectorId: String = "",
    var economicNewSubSector: String = "",
    var businessFieldId: String = "",
    var businessField: String = "",
    var subBusinessFieldId: String = "",
    var subBusinessField: String = ""
) : Picker<String>, Parcelable

interface Picker<T> {
    val id: T?

    val displayText: String?
}