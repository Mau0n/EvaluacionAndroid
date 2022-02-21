package com.evaluacionandroid.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Search::class,
        Nationality::class
    ],
    version = 1
)
abstract class RecordDataBase: RoomDatabase() {

    abstract val recordDao: RecordDao

    companion object {
        @Volatile
        private var INSTANCE: RecordDataBase? = null

        fun getInstance(context: Context): RecordDataBase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RecordDataBase::class.java,
                    "record_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}