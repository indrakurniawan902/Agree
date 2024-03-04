package com.agree.ecosystem.utilities.data.reqres.model.help

import com.agree.ecosystem.utilities.domain.reqres.model.help.Question
import com.google.gson.annotations.SerializedName

data class QuestionItem(
    @field:SerializedName("answer")
    val data: List<AnswerItem>? = null,

    @field:SerializedName("question")
    val question: String? = null
) {
    fun toQuestion(): Question {
        return Question(
            data = data?.map { it.toAnswer() } ?: emptyList(),
            question = question.orEmpty()
        )
    }
}
