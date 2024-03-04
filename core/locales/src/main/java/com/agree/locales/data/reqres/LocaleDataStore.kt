package com.agree.locales.data.reqres

import android.content.Context
import androidx.annotation.WorkerThread
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.AppConfig
import com.agree.ecosystem.core.utils.utility.CredentialManagement
import com.agree.ecosystem.core.utils.utility.nowUnix
import com.agree.locales.LanguageHelper
import com.agree.locales.SupportedLocale
import com.agree.locales.data.reqres.db.LocaleDb
import com.agree.locales.data.reqres.model.locale.LocaleData
import com.agree.locales.data.reqres.web.LocaleApiClient
import com.devbase.data.utilities.rx.operators.singleApiError
import com.devbase.utils.AppContext
import com.oratakashi.viewbinding.core.network.rxjava2.flowable.networkSyncReverse
import dev.b3nedikt.app_locale.AppLocale
import dev.b3nedikt.restring.Restring
import io.reactivex.Flowable
import java.util.Locale

/**
 * Locale Data Store are Locale Repository Implementation
 * on Data Layer
 * @author: @telkomdev-abdul
 * @since: 21 Dec 2022
 */
@WorkerThread
class LocaleDataStore(
    private val androidContext: Context,
    override val dbService: LocaleDb,
    override val webService: LocaleApiClient,
    private val prefs: AgrPrefUsecase
) : LocaleRepository {
    override fun getResources(): Flowable<List<LocaleData>> {
        return networkSyncReverse(
            saveToDb = { dbService.locale().addAll(it) },
            fetchDb = { dbService.locale().getAll() },
            fetchApi = {
                (if (AppConfig.env == "prod") {
                    webService.getResourcesProd(CredentialManagement.getLocaleToken())
                } else {
                    webService.getResourcesDev(CredentialManagement.getLocaleToken())
                })
                    .lift(singleApiError())
                    .map { it.list ?: emptyList() }
            },
            mapData = { it }
        ).map { data ->
            prefs.lastLanguageFetch = nowUnix()
            dbService.locale().addAll(data)
            mapResources(data)
        }
//        return networkSync<List<LocaleData>, List<LocaleData>>(
//            saveToDb = { dbService.locale().addAll(it)},
//            fetchDb = { dbService.locale().getAll() },
//            fetchApi = {
//                (if(AppConfig.env == "prod") {
//                    webService.getResourcesProd(CredentialManagement.getLocaleToken())
//                } else {
//                    webService.getResourcesDev(CredentialManagement.getLocaleToken())
//                })
//                    .lift(singleApiError())
//                    .map { it.list ?: emptyList() }
//            },
//            onConflict = { api, _ ->
//                api
//            },
//            alwaysUpToDate = {true}
//        ).map { data ->
//            prefs.lastLanguageFetch = nowUnix()
//            dbService.locale().addAll(data)
//            mapResources(data)
//        }


//        return Flowable.create({ emitter ->
//            emitter.onNext(dbService.locale().getAll())
//        }, BackpressureStrategy.BUFFER).flatMap { local ->
//            val lastFetch = prefs.lastLanguageFetch.toLocalDateTime()
//            val duration =
//                ChronoUnit.HOURS.between(lastFetch, LocalDateTime.now(ZoneId.systemDefault()))
//
//            if (duration >= 0 || local.isEmpty()) {
//                (
//                    if(AppConfig.env == "prod") {
//                        webService.getResourcesProd(CredentialManagement.getLocaleToken())
//                    } else {
//                        webService.getResourcesDev(CredentialManagement.getLocaleToken())
//                    }
//                )
//                    .lift(flowableApiError())
//                    .map { res ->
//                        val data = res.list?.mapNotNull { data -> data }.orEmpty()
//                        prefs.lastLanguageFetch = nowUnix()
//                        dbService.locale().addAll(data)
//                        mapResources(data)
//                    }
//            } else {
//                Flowable.create({ emitter ->
//                    emitter.onNext(mapResources(local))
//                }, BackpressureStrategy.BUFFER)
//            }
//        }
    }

    /**
     * Mapping data into resources
     */
    private fun mapResources(data: List<LocaleData>): List<LocaleData> {
        val indonesia = data.map { locale ->
            val string = if (locale.idn.isNullOrBlank()) locale.default else locale.idn
            locale.key to (string).orEmpty()
                .replace("\\n", System.getProperty("line.separator").orEmpty())
        }.toTypedArray()

        val english = data.map { locale ->
            val string = if (locale.en.isNullOrBlank()) locale.default else locale.en
            locale.key to (string).orEmpty()
                .replace("\\n", System.getProperty("line.separator").orEmpty())
        }.toTypedArray()

        Restring.putStrings(
            SupportedLocale.INDONESIAN.locale,
            mapOf(*indonesia)
        )

        Restring.putStrings(
            SupportedLocale.ENGLISH.locale,
            mapOf(*english)
        )

        Restring.wrapContext(androidContext).also { ctx ->
            AppContext.setBaseContext(ctx)
        }

        LanguageHelper.injectLanguage(prefs.currentLocale)
        AppLocale.desiredLocale = Locale.forLanguageTag(prefs.currentLocale)
        return data
    }
}