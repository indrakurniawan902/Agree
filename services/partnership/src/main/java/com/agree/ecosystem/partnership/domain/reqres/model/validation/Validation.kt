package com.agree.ecosystem.partnership.domain.reqres.model.validation

data class Validation(
    val registered: String,
    val subsectors: List<SubsectorValidate>
)
