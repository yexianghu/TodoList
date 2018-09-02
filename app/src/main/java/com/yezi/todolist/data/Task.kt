package com.yezi.todolist.data

import com.yezi.todolist.dao.GroupElement
import com.yezi.todolist.dao.TaskElement

interface Task {
    val id:Int
    val title:String
    val comment:String?
    val tags:Set<String>
    var parentGroup:Group
}

interface Group {

    enum class PERSIST {
        TODAY,
        IMPORTANT,
        TODO;

        val value:Int
            get() = ordinal + Int.MAX_VALUE/2
    }

    val id:Int
    val title:String
    var showDone:Boolean
    val persist:Boolean
}

interface TodoListFactory {

    fun createGroup(groupElement: GroupElement): Group

    fun createTask(group:Group, taskElement: TaskElement): Task
}

class TagInspect {

    companion object {
        val INVALID_CHARSET:Set<Char> = setOf(',', ':', ';')
        private val CHECK_VALID_REX = INVALID_CHARSET.joinToString("|")

        fun valid(tag:String):Boolean {
            return !tag.isEmpty() && !tag.contains(Regex(CHECK_VALID_REX))
        }
    }
}

val todoListFactory = TodoListFactoryImpl()

fun getTodoListFactory():TodoListFactory {
    return todoListFactory
}