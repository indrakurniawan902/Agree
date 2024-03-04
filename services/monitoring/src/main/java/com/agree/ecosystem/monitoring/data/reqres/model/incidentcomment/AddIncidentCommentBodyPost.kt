package com.agree.ecosystem.monitoring.data.reqres.model.incidentcomment

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class AddIncidentCommentBodyPost(
    @SerializedName("activity_event_id")
    val activityEventId: String,
    @SerializedName("commenter_id")
    val commenterId: String,
    @SerializedName("commenter_name")
    val commenterName: String,
    @SerializedName("commenter_type")
    val commenterType: String,
    @SerializedName("message")
    val message: String
)
