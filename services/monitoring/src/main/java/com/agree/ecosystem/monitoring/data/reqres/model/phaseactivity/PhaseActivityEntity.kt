package com.agree.ecosystem.monitoring.data.reqres.model.phaseactivity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhaseActivityEntity(
    @PrimaryKey(autoGenerate = false)
    val phaseId: String,
    val phaseName: String,
    val description: String,
    val subVesselId: String
)
