package com.agree.locales.domain

import com.agree.locales.data.reqres.model.locale.LocaleData
import io.reactivex.Flowable

interface LocaleUsecase {
    fun getResources(): Flowable<List<LocaleData>>
}