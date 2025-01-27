package com.example.getitdone.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class GetItDoneDatabase : RoomDatabase() {
    abstract fun getTaskDao() : TaskDao

    companion object {
        fun createDb(context: Context): GetItDoneDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = GetItDoneDatabase::class.java,
                name = "get-it-done-db"
            ).build()
        }
    }
}