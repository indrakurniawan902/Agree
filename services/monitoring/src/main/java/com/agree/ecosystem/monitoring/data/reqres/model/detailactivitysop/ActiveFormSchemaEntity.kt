package com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.agree.ecosystem.core.utils.utility.offline.EntityModel

@Entity
data class ActiveFormSchemaEntity(
    @PrimaryKey
    val id: String,
    var value: String,
    val is_reference: String,
    val phaseActivityId: String,
) : EntityModel
