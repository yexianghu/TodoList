package com.yezi.todolist.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.yezi.todolist.dao.DaoHelper
import com.yezi.todolist.dao.GroupElement
import com.yezi.todolist.dao.TodoListDataBase
import com.yezi.todolist.data.Group
import com.yezi.todolist.data.Task
import com.yezi.todolist.data.getTodoListFactory


class TasksLiveData(val group:Group) : MutableLiveData<List<Task>>() {

}

class GroupsLiveData : MutableLiveData<List<Group>>() {
}


class TodoListRepository {

    fun loadGroups(ctx: Context):GroupsLiveData {
        val gld = GroupsLiveData()
        DaoHelper.execute(ctx) {
            db -> gld.postValue(getGroups(db))
        }
        return gld
    }

    fun getTaskByGroup(ctx: Context, group:Group):TasksLiveData {
        val tld = TasksLiveData(group)
        DaoHelper.execute(ctx) {
            db -> tld.postValue(getTasksByGroup(db, group))
        }
        return tld
    }

    fun addGroup(ctx: Context, title:String, showDone:Boolean, gld:GroupsLiveData?) {
        DaoHelper.execute(ctx) {
            db -> db.todoListDao().addGroup(GroupElement(title, showDone))
            gld?.postValue(getGroups(db))
        }
    }

    private fun getGroups(db:TodoListDataBase):List<Group> {
        return db.todoListDao().getGroup().map { getTodoListFactory().createGroup(it) }
    }

    private fun getTasksByGroup(db:TodoListDataBase, group:Group):List<Task> {
        return db.todoListDao().getTaskByGroup(group.id).map { getTodoListFactory().createTask(group, it) }
    }
}

val respsitory = TodoListRepository()

fun getTodoListRepository():TodoListRepository {
    return respsitory
}