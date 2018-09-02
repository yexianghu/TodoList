package com.yezi.todolist.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.yezi.todolist.dao.DaoHelper
import com.yezi.todolist.data.Group
import com.yezi.todolist.data.Task
import com.yezi.todolist.data.getTodoListFactory


class TasksLiveData : MutableLiveData<List<Task>>() {

}

class GroupsLiveData : MutableLiveData<List<Group>>() {
}


class TodoListRepository {

    fun loadGroups(ctx: Context):GroupsLiveData {
        val gld = GroupsLiveData()
        DaoHelper.execute(ctx) {
            db -> gld.postValue(db.todoListDao().getGroup().map { getTodoListFactory().createGroup(it) })
        }
        return gld
    }

    fun getTaskByGroup(ctx: Context, group:Group):TasksLiveData {
        val tld = TasksLiveData()
        DaoHelper.execute(ctx) {
            db -> tld.postValue(db.todoListDao().getTaskByGroup(group.id).map { getTodoListFactory().createTask(group, it) })
        }
        return tld
    }
}

val respsitory = TodoListRepository()

fun getTodoListRepository():TodoListRepository {
    return respsitory
}