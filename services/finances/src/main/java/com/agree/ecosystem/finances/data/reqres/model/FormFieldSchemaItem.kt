package com.agree.ecosystem.finances.data.reqres.model

import com.agree.ecosystem.finances.domain.reqres.model.profilefarmer.FormFieldSchema
import com.agree.ecosystem.finances.domain.reqres.model.profilefarmer.OptionsSchema
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class FormFieldSchemaItem(

    @SerializedName("componentType")
    val componentType: String? = null,

    @SerializedName("disabled")
    val disabled: Boolean? = null,

    @SerializedName("label")
    val label: String? = null,

    @SerializedName("fieldType")
    val fieldType: String? = null,

    @SerializedName("value")
    val value: JsonElement? = null,

    @SerializedName("required")
    val required: Boolean? = null,

    @SerializedName("maxLength")
    val maxLength: Int? = null,

    @SerializedName("information")
    val information: Boolean? = null,

    @SerializedName("keyboardType")
    val keyboardType: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("suffix")
    val suffix: String? = null,

    @SerializedName("delimiter")
    val delimiter: String? = null,

    @SerializedName("prefix")
    val prefix: String? = null,

    @SerializedName("translatable")
    val translatable: Boolean? = true,

    @SerializedName("options")
    val options: List<OptionsSchemaItem>? = null
) {
    fun toFieldInfo(): FormFieldSchema {
        return FormFieldSchema(
            componentType = componentType.orEmpty(),
            disabled = disabled ?: false,
            label = label.orEmpty(),
            fieldType = fieldType.orEmpty(),
            value = value?.getValueFromElement().orEmpty(),
            required = required ?: false,
            maxLength = maxLength ?: 0,
            information = information ?: false,
            keyboardType = keyboardType.orEmpty(),
            options = options?.map { it.toOptionsSchema() } ?: emptyList(),
            title = title.orEmpty(),
            description = description.orEmpty(),
            suffix = suffix.orEmpty(),
            delimiter = delimiter.orEmpty(),
            prefix = prefix.orEmpty(),
            translatable = translatable ?: true,
            listImages = value?.getListFromElement() ?: emptyList()
        )
    }
}

data class OptionsSchemaItem(

    @SerializedName("label")
    val label: String? = null,

    @SerializedName("value")
    val value: String? = null,

    @SerializedName("provinceId")
    val provinceId: Long? = null,

    @SerializedName("districtId")
    val districtId: Long? = null,

    @SerializedName("subDistrictId")
    val subDistrictId: Long? = null,

    @SerializedName("villageId")
    val villageId: Long? = null,

    @SerializedName("village")
    val village: String? = null,

    @SerializedName("subDistrict")
    val subDistrict: String? = null,

    @SerializedName("district")
    val district: String? = null,

    @SerializedName("economicSubSector")
    val economicSubSector: String? = null,

    @SerializedName("economicSectorId")
    val economicSectorId: Long? = null,

    @SerializedName("economicSubSectorId")
    val economicSubSectorId: Long? = null,

    @SerializedName("economicNewSubSectorId")
    val economicNewSubSectorId: Long? = null,

    @SerializedName("economicNewSubSector")
    val economicNewSubSector: String? = null,

    @SerializedName("businessFieldId")
    val businessFieldId: Long? = null,

    @SerializedName("businessField")
    val businessField: String? = null,

    @SerializedName("subBusinessFieldId")
    val subBusinessFieldId: Long? = null,

    @SerializedName("subBusinessField")
    val subBusinessField: String? = null,

    @SerializedName("utilityType")
    val utilityType: String? = null
) {
    fun toOptionsSchema(): OptionsSchema {
        return OptionsSchema(
            id = value.orEmpty(),
            displayText = label.orEmpty(),
            provinceId = provinceId.toString().orEmpty(),
            districtId = districtId.toString().orEmpty(),
            subDistrictId = subDistrictId.toString().orEmpty(),
            villageId = villageId.toString().orEmpty(),
            district = district.orEmpty(),
            subDistrict = subDistrict.orEmpty(),
            village = village.orEmpty(),
            economicSectorId = economicSectorId.toString(),
            economicSubSector = economicSubSector.toString(),
            economicSubSectorId = economicSubSectorId.toString(),
            businessFieldId = businessFieldId.toString(),
            businessField = businessField.toString(),
            subBusinessFieldId = subBusinessFieldId.toString(),
            subBusinessField = subBusinessField.toString(),
            economicNewSubSector = economicNewSubSector.toString().orEmpty(),
            economicNewSubSectorId = economicNewSubSectorId.toString().orEmpty()
        )
    }
}

fun JsonElement.getValueFromElement(): String {
    return if (this.isJsonPrimitive) {
        this.asString
    } else {
        ""
    }
}

fun JsonElement.getListFromElement(): List<String> {
    if (this.isJsonArray) {
        val gson = GsonBuilder().create()
        val packagesArray = gson.fromJson(this.asJsonArray, Array<String>::class.java).toList()
        return packagesArray
    } else {
        return listOf()
    }
}
