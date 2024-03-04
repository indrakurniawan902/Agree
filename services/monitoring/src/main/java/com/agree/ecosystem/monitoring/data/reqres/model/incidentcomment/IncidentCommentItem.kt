package com.agree.ecosystem.monitoring.data.reqres.model.incidentcomment

import androidx.annotation.Keep
import com.agree.ecosystem.monitoring.domain.reqres.model.incidentcomment.IncidentComment
import com.google.gson.annotations.SerializedName

@Keep
data class IncidentCommentItem(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("activity_event_id")
    val activityId: String,
    @field:SerializedName("commenter_id")
    val commenterId: String,
    @field:SerializedName("commenter_name")
    val commenterName: String,
    @field:SerializedName("created_at")
    val createdAt: String,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("commenter_type")
    val commenterType: String
) {
    fun toIncidentComment(): IncidentComment {
        return IncidentComment(
            id = id.orEmpty(),
            activityId = activityId.orEmpty(),
            commenterId = commenterId.orEmpty(),
            commenterName = commenterName.orEmpty(),
            createdAt = createdAt.orEmpty(),
            message = message.orEmpty(),
            commenterType = commenterType.orEmpty(),
            images = listOf()
        )
    }
}
