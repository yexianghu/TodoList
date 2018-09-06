package com.yezi.todolist.dao

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(AndroidJUnit4::class)
class DataBaseTest {

    private val LOG_TAG = "DataBaseTest"

    private lateinit var db:TodoListDataBase
    private lateinit var dao:TodoListDao

    @Before
    fun setUp() {
        db = createTodoListDB(InstrumentationRegistry.getTargetContext())
        dao = db.todoListDao()
    }

    @After
    fun tearDown() {
        dao.clearTasks()
        dao.clearGroups()
        db.close()
    }


    @Test
    fun testGroup() {
        val origCount = dao.getGroupCount()

        val insertCount = 100
        for (i in 1..insertCount) {
            val group = GroupElement()
            group.title = "title$i"
            group.showDone = false
            dao.addGroup(group)
        }

        var needRemove = true
        for (group in dao.getGroup()) {
            if (needRemove) {
                dao.removeGroup(group)
            }
            needRemove = !needRemove
        }

        Assert.assertEquals(dao.getGroupCount(), (origCount + insertCount)/2)
    }

    @Test
    fun testTask() {

        val insertCount = 100
        for (i in 1..insertCount) {
            val group = GroupElement()
            group.title = "title${i}"
            group.showDone = false
            dao.addGroup(group)
        }

        var taskCount = 0;

        for (group in dao.getGroup()) {
            taskCount += dao.getTaskCountByGroup(group.id)
            for (i in 1..5) {
                val task = TaskElement()
                task.title = "title${i}"
                task.groupId = group.id
                dao.insertTask(task)
                taskCount++
            }

        }
        Assert.assertEquals(dao.getTaskCount(), taskCount)
    }
}