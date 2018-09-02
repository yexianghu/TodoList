package com.yezi.todolist.dao

import androidx.annotation.NonNull
import androidx.room.*

@Entity(tableName = "tasks")
class TaskElement {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "title")
    lateinit var title: String

    @ColumnInfo(name = "comment")
    var comment: String? = null

    @ColumnInfo(name = "tags")
    var tags: Set<String>? = null

    @ColumnInfo(name = "groupId")
    var groupId:Int = 0
}

@Entity(tableName = "groups")
class GroupElement {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "title")
    lateinit var title: String

    @ColumnInfo(name = "showDown")
    var showDone: Boolean = false
}