package com.agree.ecosystem.utilities.data.reqres.db

import com.agree.ecosystem.utilities.data.ZoneDatabase
import com.agree.ecosystem.utilities.data.reqres.db.dao.zone.DistrictDao
import com.agree.ecosystem.utilities.data.reqres.db.dao.zone.ProvinceDao
import com.agree.ecosystem.utilities.data.reqres.db.dao.zone.SubDistrictDao
import com.agree.ecosystem.utilities.data.reqres.db.dao.zone.VillageDao

class ZoneDbImpl(
    private val db: ZoneDatabase
) : ZoneDb {
    override fun province(): ProvinceDao {
        return db.province()
    }

    override fun district(): DistrictDao {
        return db.district()
    }

    override fun subDistrict(): SubDistrictDao {
        return db.subDistrict()
    }

    override fun village(): VillageDao {
        return db.village()
    }
}
