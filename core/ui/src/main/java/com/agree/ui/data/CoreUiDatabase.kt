package com.agree.ui.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.agree.ui.data.reqres.model.DisplayedSchemaEntity
import com.agree.ui.data.reqres.model.FormEntity
import com.agree.ui.data.reqres.model.SubFormEntity

@Database(
    entities = [
        FormEntity::class,
        SubFormEntity::class,
        DisplayedSchemaEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CoreUiDatabase : RoomDatabase() {
    companion object {
        fun getDbName(): String = "coreUIDb"
    }
}
