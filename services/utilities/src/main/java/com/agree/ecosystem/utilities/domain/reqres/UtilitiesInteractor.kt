package com.agree.ecosystem.utilities.domain.reqres

import com.agree.ecosystem.core.utils.data.reqres.RemoteConfigRepository
import com.agree.ecosystem.utilities.data.reqres.UtilitiesRepository
import com.agree.ecosystem.utilities.domain.reqres.model.appinfo.AppInfo
import com.agree.ecosystem.utilities.domain.reqres.model.help.Help
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

class UtilitiesInteractor(
    private val repo: UtilitiesRepository,
    private val remoteConfig: RemoteConfigRepository
) : UtilitiesUsecase {

    override fun getAppInfo(isOnlinePriority: Boolean): Flowable<ArrayList<AppInfo>> {
        return repo.getAppInfo(isOnlinePriority).map {
            val data = arrayListOf<AppInfo>()
            data.add(it.toAppInfo())
            return@map data
        }
    }

    override fun getHelpCategory(): Flowable<ArrayList<Help>> {
        return repo.getHelpCategory().map {
            val data = arrayListOf<Help>()
            it.map { i ->
                data.add(i.toHelp())
            }
            return@map data
        }
    }

    override fun getSubSectors(): Flowable<List<SubSector>> {
        return repo.getSubSectors().map { it.map { data -> data.toSubSector() } }
    }

    override fun initRemoteConfig(): Flowable<Boolean> {
        return Flowable.create({ emitter ->
            Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                when {
                    task.isSuccessful -> emitter.onNext(task.result)
                    else -> emitter.tryOnError(Error(task.exception))
                }
            }
        }, BackpressureStrategy.BUFFER)
    }
}
