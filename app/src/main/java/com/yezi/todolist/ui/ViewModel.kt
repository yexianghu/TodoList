package com.yezi.todolist.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.yezi.todolist.data.Group
import com.yezi.todolist.repository.GroupsLiveData
import com.yezi.todolist.repository.getTodoListRepository

class GroupsViewModel : ViewModel() {

    private lateinit var groupsLiveData:GroupsLiveData

    fun load(ctx:Context):GroupsLiveData {
        if (!::groupsLiveData.isInitialized) {
            groupsLiveData = getTodoListRepository().loadGroups(ctx)
        }
        return groupsLiveData
    }

    fun get():GroupsLiveData {
        return groupsLiveData;
    }

    fun getGroup(id:Int):Group? {
        return if (!::groupsLiveData.isInitialized) {
            null
        } else {
            groupsLiveData.value?.find { it.id == id }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}

class TasksViewModel(group: Group) {

    var group:Group = group
        set(value) {
            if (field != value) {
                field = value
                onGroupChanged()
            }
        }


    private fun onGroupChanged() {
    }
}