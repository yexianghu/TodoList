package com.yezi.todolist

import android.app.Application
import com.yezi.todolist.utils.LogUtils

class TodoListApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LogUtils.getConfig().setBorderSwitch(false).setLogHeadSwitch(false)
    }
}