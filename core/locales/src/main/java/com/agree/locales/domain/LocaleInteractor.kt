package com.agree.locales.domain

import com.agree.locales.data.reqres.LocaleRepository
import com.agree.locales.data.reqres.model.locale.LocaleData
import com.devbase.utils.ext.isNotNull
import io.reactivex.Flowable

class LocaleInteractor(
    private val repo: LocaleRepository
) : LocaleUsecase {
    override fun getResources(): Flowable<List<LocaleData>> {
        return  repo.getResources().map { it.filterNotNull() }
    }
}