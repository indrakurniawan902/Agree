package com.agree.locales.data.reqres.db

import com.agree.locales.data.reqres.db.dao.LocaleDao
import com.devbase.data.source.db.DbService

interface LocaleDb : DbService {
    fun locale(): LocaleDao
}