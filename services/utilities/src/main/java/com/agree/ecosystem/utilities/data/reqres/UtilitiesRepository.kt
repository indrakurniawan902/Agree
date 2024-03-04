package com.agree.ecosystem.utilities.data.reqres

import com.agree.ecosystem.utilities.data.reqres.model.appinfo.AppInfoEntity
import com.agree.ecosystem.utilities.data.reqres.model.help.HelpItem
import com.agree.ecosystem.utilities.data.reqres.model.subsector.SubSectorEntity
import com.devbase.data.source.DevRepository
import io.reactivex.Flowable

interface UtilitiesRepository : DevRepository {
    fun getAppInfo(isOnlinePriority: Boolean = false): Flowable<AppInfoEntity>
    fun getHelpCategory(): Flowable<ArrayList<HelpItem>>

    fun getSubSectors(): Flowable<List<SubSectorEntity>>
}
