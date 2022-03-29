package com.revature.contentproviderexample.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [UserItem::class],
    exportSchema = false
)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao():UserDao

    companion object {

        @Volatile
        private var INSTANCE:UserDatabase? = null

        fun getDatabase(context:Context):UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(lock = this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "UserDatabase").build()

                INSTANCE = instance

                return instance
            }
        }
    }
}