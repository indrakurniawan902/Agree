package com.agree.ecosystem.agreepedia.data.model.article

import com.agree.ecosystem.agreepedia.domain.model.article.Article
import com.agree.ecosystem.agreepedia.domain.model.article.Tag
import com.google.gson.annotations.SerializedName

data class ArticleItem(
    @field:SerializedName("object_identifier")
    val objectIdentifier: Long? = null,
    @field:SerializedName("begin_date")
    val beginDate: String? = null,
    @field:SerializedName("end_date")
    val endDate: String? = null,
    @field:SerializedName("business_code")
    val businessCode: String? = null,
    @field:SerializedName("blog_name")
    val blogName: String? = null,
    @field:SerializedName("blog_subname")
    val blogSubName: String? = null,
    @field:SerializedName("status")
    val status: String? = null,
    @field:SerializedName("action")
    val action: String? = null,
    @field:SerializedName("action_text")
    val actionText: String? = null,
    @field:SerializedName("blog_thumbnail")
    val blogThumbnail: String? = null,
    @field:SerializedName("blog_value")
    val blogValue: String? = null,
    @field:SerializedName("blog_id")
    val blogId: Int? = null,
    @field:SerializedName("change_date")
    val changeDate: String? = null,
    @field:SerializedName("change_user")
    val changeUser: String? = null,
    @field:SerializedName("created_by")
    val createdBy: String? = null,
    @field:SerializedName("message")
    val message: String? = null,
    @field:SerializedName("creator_mail")
    val creatorMail: String? = null,
    @field:SerializedName("avatar")
    val avatar: String? = null,
    @field:SerializedName("blog_image")
    val blogImage: String? = null,
    @field:SerializedName("tag")
    val tag: List<TagItem>? = null
) {
    fun toAgreepedia(): Article {
        return Article(
            objectIdentifier = objectIdentifier ?: 0L,
            beginDate = beginDate.orEmpty(),
            endDate = endDate.orEmpty(),
            businessCode = businessCode.orEmpty(),
            blogName = blogName.orEmpty(),
            blogSubName = blogSubName.orEmpty(),
            status = status.orEmpty(),
            action = action.orEmpty(),
            actionText = actionText.orEmpty(),
            blogThumbnail = blogThumbnail.orEmpty(),
            blogValue = blogValue.orEmpty(),
            blogId = blogId ?: 0,
            changeDate = changeDate.orEmpty(),
            changeUser = changeUser.orEmpty(),
            createdBy = createdBy.orEmpty(),
            message = message.orEmpty(),
            creatorMail = creatorMail.orEmpty(),
            avatar = avatar.orEmpty(),
            blogImage = blogImage.orEmpty(),
            tag = tag?.map { it.toTag() }.orEmpty()
        )
    }
}

data class TagItem(
    @field:SerializedName("object_identifier")
    val objectIdentifier: Long? = null,
    @field:SerializedName("tag_value")
    val tagValue: String? = null,
    @field:SerializedName("blog_id")
    val blogId: String? = null,
    @field:SerializedName("begin_date")
    val beginDate: String? = null,
    @field:SerializedName("change_date")
    val changeDate: String? = null
) {
    fun toTag(): Tag {
        return Tag(
            objectIdentifier = objectIdentifier ?: 0L,
            tagValue = tagValue.orEmpty(),
            blogId = blogId.orEmpty(),
            beginDate = beginDate.orEmpty(),
            changeDate = changeDate.orEmpty()
        )
    }
}
