package com.yezi.todolist.data

import com.yezi.todolist.dao.GroupElement
import com.yezi.todolist.dao.TaskElement

private class TaskImpl(override val id:Int, override val title: String, override var parentGroup: Group) : Task {

    constructor(id:Int, title:String, parentGroup: Group, comment:String?, tags:Set<String>?) : this(id, title, parentGroup) {
        this.comment = comment
        this.tags.clear()
        tags?.let { this@TaskImpl.tags.addAll(it) }

    }

    override var comment: String? = null
    override val tags: LinkedHashSet<String> = linkedSetOf()
}

private class GroupImpl(override val id:Int, override val title:String, override val persist:Boolean=false, showDone:Boolean) : Group {

    override var showDone: Boolean = true
    init {
        this.showDone = showDone
    }
}

class TodoListFactoryImpl: TodoListFactory {

    override fun createGroup(groupElement: GroupElement): Group {
        return GroupImpl(groupElement.id, groupElement.title, showDone=groupElement.showDone)
    }

    override fun createTask(group:Group, taskElement: TaskElement): Task {
        return TaskImpl(taskElement.id, taskElement.title, group, taskElement.comment, taskElement.tags)
    }

}