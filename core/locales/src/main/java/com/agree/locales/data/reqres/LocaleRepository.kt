package com.agree.locales.data.reqres

import com.agree.locales.data.reqres.model.locale.LocaleData
import com.devbase.data.source.DevRepository
import io.reactivex.Flowable

/**
 * Locale Repository are Locale Data Contract Abstraction
 * on Data Layer
 * @author: @telkomdev-abdul
 * @since: 21 Dec 2022
 */
interface LocaleRepository : DevRepository {
    fun getResources(): Flowable<List<LocaleData>>
}