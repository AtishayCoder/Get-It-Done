package com.example.getitdone

import android.app.Application
import com.example.getitdone.data.GetItDoneDatabase
import com.example.getitdone.data.TaskDao

class GetItDoneApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        db = GetItDoneDatabase.getDatabase(this)
        dao = db.getTaskDao()
    }

    companion object {
        lateinit var db: GetItDoneDatabase
        lateinit var dao: TaskDao

    }
}