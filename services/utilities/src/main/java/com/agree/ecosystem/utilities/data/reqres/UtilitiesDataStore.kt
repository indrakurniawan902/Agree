package com.agree.ecosystem.utilities.data.reqres

import android.annotation.SuppressLint
import androidx.annotation.WorkerThread
import com.agree.ecosystem.utilities.data.reqres.db.UtilitiesDb
import com.agree.ecosystem.utilities.data.reqres.model.appinfo.AppInfoEntity
import com.agree.ecosystem.utilities.data.reqres.model.appinfo.AppInfoItem
import com.agree.ecosystem.utilities.data.reqres.model.help.HelpItem
import com.agree.ecosystem.utilities.data.reqres.model.subsector.SubSectorEntity
import com.agree.ecosystem.utilities.data.reqres.model.subsector.SubSectorItem
import com.agree.ecosystem.utilities.data.reqres.web.utilities.AgreeUtilitiesApi
import com.devbase.data.utilities.rx.operators.flowableApiError
import com.devbase.data.utilities.rx.operators.singleApiError
import com.oratakashi.viewbinding.core.network.rxjava2.flowable.networkSync
import com.oratakashi.viewbinding.core.network.rxjava2.flowable.networkSyncReverse
import io.reactivex.Flowable

@WorkerThread
@SuppressLint("CheckResult")
class UtilitiesDataStore(
    override val dbService: UtilitiesDb,
    override val webService: AgreeUtilitiesApi
) : UtilitiesRepository {

    override fun getAppInfo(isOnlinePriority: Boolean): Flowable<AppInfoEntity> {
        return if (!isOnlinePriority) {
            networkSync<List<AppInfoItem>, List<AppInfoEntity>>(
                saveToDb = { dbService.appInfo().addAll(it) },
                fetchDb = { dbService.appInfo().getAppInfo() },
                fetchApi = {
                    webService.getAppInfo().lift(singleApiError()).map { it.data ?: emptyList() }
                },
                onConflict = { dataFromAPI, _ ->
                    dataFromAPI.map { data -> data.toAppInfoEntity() }
                },
                alwaysUpToDate = { it.isEmpty() }
            ).map { it.first() }
        } else {
            networkSyncReverse<List<AppInfoItem>, List<AppInfoEntity>>(
                saveToDb = { dbService.appInfo().addAll(it) },
                fetchDb = { dbService.appInfo().getAppInfo() },
                fetchApi = {
                    webService.getAppInfo().lift(singleApiError()).map { it.data ?: emptyList() }
                },
                mapData = { it.map { data -> data.toAppInfoEntity() } }
            ).map { it.first() }
        }
    }

    override fun getHelpCategory(): Flowable<ArrayList<HelpItem>> {
        return webService.getHelpCategory()
            .lift(flowableApiError())
            .map { it.data }
    }

    override fun getSubSectors(): Flowable<List<SubSectorEntity>> {
        return networkSync<List<SubSectorItem>, List<SubSectorEntity>>(
            saveToDb = { dbService.subSector().addAll(it) },
            fetchDb = { dbService.subSector().getAllSubSectors() },
            fetchApi = {
                webService.getSubSectors().lift(singleApiError()).map { it.data ?: emptyList() }
            },
            onConflict = { dataFromAPI, _ ->
                dataFromAPI.map { data -> data.toSubSectorEntity() }
            },
            alwaysUpToDate = { true }
        )
    }
}
