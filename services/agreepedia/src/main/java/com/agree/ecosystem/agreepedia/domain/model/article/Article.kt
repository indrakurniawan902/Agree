package com.agree.ecosystem.agreepedia.domain.model.article

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Article(
    val objectIdentifier: Long,
    val beginDate: String,
    val endDate: String,
    val businessCode: String,
    val blogName: String,
    val blogSubName: String,
    val status: String,
    val action: String,
    val actionText: String,
    val blogThumbnail: String,
    val blogValue: String,
    val blogId: Int,
    val changeDate: String,
    val changeUser: String,
    val createdBy: String,
    val message: String,
    val creatorMail: String,
    val avatar: String,
    val blogImage: String,
    val tag: List<Tag>
) : Parcelable

@Keep
@Parcelize
data class Tag(
    val objectIdentifier: Long,
    val tagValue: String,
    val blogId: String,
    val beginDate: String,
    val changeDate: String
) : Parcelable
