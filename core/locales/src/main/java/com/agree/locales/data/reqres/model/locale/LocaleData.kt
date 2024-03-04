package com.agree.locales.data.reqres.model.locale

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devbase.data.source.db.DbModel
import com.google.gson.annotations.SerializedName

@Entity(tableName = "resourceString")
data class LocaleData(
    @ColumnInfo(name = "key")
    @PrimaryKey
    @field:SerializedName("key")
    val key: String = "",
    @ColumnInfo(name = "Idn") @field:SerializedName("Idn")
    val idn: String? = null,
    @ColumnInfo(name = "Platform") @field:SerializedName("Platform")
    val platform: String? = null,
    @ColumnInfo(name = "En") @field:SerializedName("Eng")
    val en: String? = null,
    @ColumnInfo(name = "default") @field:SerializedName("Default")
    val default: String? = null
)
