package com.agree.ecosystem.core.utils.data.reqres

import com.agree.ecosystem.core.utils.data.reqres.web.common.CommonApi
import com.devbase.data.source.db.DbService

class CommonDataStore(
    override val dbService: DbService?,
    override val webService: CommonApi
) : CommonRepository
