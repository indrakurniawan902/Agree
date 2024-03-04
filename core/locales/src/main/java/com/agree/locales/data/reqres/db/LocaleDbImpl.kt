package com.agree.locales.data.reqres.db

import com.agree.locales.data.LocaleDatabase
import com.agree.locales.data.reqres.db.dao.LocaleDao

class LocaleDbImpl(
    private val db: LocaleDatabase
) : LocaleDb {
    override fun locale(): LocaleDao {
        return db.localeDao()
    }
}