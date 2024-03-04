package com.agree.ecosystem.utilities.data.reqres.db

import com.agree.ecosystem.utilities.data.reqres.db.dao.zone.DistrictDao
import com.agree.ecosystem.utilities.data.reqres.db.dao.zone.ProvinceDao
import com.agree.ecosystem.utilities.data.reqres.db.dao.zone.SubDistrictDao
import com.agree.ecosystem.utilities.data.reqres.db.dao.zone.VillageDao
import com.devbase.data.source.db.DbService

interface ZoneDb : DbService {
    fun province(): ProvinceDao
    fun district(): DistrictDao
    fun subDistrict(): SubDistrictDao
    fun village(): VillageDao
}
