package com.example.getitdone.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 2)
abstract class GetItDoneDatabase : RoomDatabase() {
    abstract fun getTaskDao() : TaskDao

    companion object {

        @Volatile
        private var DATABASE_INSTANCE: GetItDoneDatabase? = null

        fun getDatabase(context: Context): GetItDoneDatabase {
            return DATABASE_INSTANCE ?: synchronized(this) {
                var instance = Room.databaseBuilder(
                    context = context,
                    klass = GetItDoneDatabase::class.java,
                    name = "get-it-done-db"
                ).fallbackToDestructiveMigration().build()
                DATABASE_INSTANCE = instance
                return instance
            }
        }
    }
}