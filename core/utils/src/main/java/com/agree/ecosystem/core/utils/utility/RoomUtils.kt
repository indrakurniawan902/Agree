package com.agree.ecosystem.core.utils.utility

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import net.sqlcipher.database.SupportFactory

inline fun <reified DB : RoomDatabase> createRoomDb(
    context: Context,
    name: String,
    config: RoomDatabase.Builder<DB>.() -> Unit = {}
): DB {
    return Room.databaseBuilder(context, DB::class.java, name)
        .fallbackToDestructiveMigration()
        .encryptDatabase()
        .apply(config)
        .build()
}

fun <T : RoomDatabase> RoomDatabase.Builder<T>.encryptDatabase(): RoomDatabase.Builder<T> {
    if (AppConfig.isDebug) {
        return this
    }
    openHelperFactory(SupportFactory(CredentialManagement.getBasicToken().toByteArray()))
    return this
}
