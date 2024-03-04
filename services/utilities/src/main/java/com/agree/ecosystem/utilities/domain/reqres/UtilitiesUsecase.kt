package com.agree.ecosystem.utilities.domain.reqres

import com.agree.ecosystem.utilities.domain.reqres.model.appinfo.AppInfo
import com.agree.ecosystem.utilities.domain.reqres.model.help.Help
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import io.reactivex.Flowable

interface UtilitiesUsecase {
    fun getAppInfo(isOnlinePriority: Boolean = false): Flowable<ArrayList<AppInfo>>
    fun getHelpCategory(): Flowable<ArrayList<Help>>

    fun getSubSectors(): Flowable<List<SubSector>>

    fun initRemoteConfig(): Flowable<Boolean>
}
