package com.example.fairmoneytestapp.data.sources.local

import android.content.Context
import androidx.room.*
import com.example.fairmoneytestapp.data.model.entities.Data
import com.example.fairmoneytestapp.data.model.entities.User
import com.example.fairmoneytestapp.data.sources.local.dao.UserDao

@Database(
    entities = [Data::class, User::class],
    version = 1,
    exportSchema = false
)

abstract class DummyDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var instance: DummyDatabase? = null

        fun getDatabase(context: Context): DummyDatabase {
            if (instance == null) {
                synchronized(DummyDatabase::class.java) {}
                instance =
                    Room.databaseBuilder(context, DummyDatabase::class.java, "AppDatabase")
                        .fallbackToDestructiveMigration()
                        .build()
            }

            return instance!!
        }
    }
}
