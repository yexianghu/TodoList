package com.yezi.todolist.dao

import android.content.Context
import androidx.room.*
import androidx.room.OnConflictStrategy.ROLLBACK
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yezi.todolist.data.TagInspect
import com.yezi.todolist.utils.LogUtils

private const val LOG_TAG = "Dao"

private const val DB_NAME = "todo_list_db"
private const val DB_VERSION = 1

@Dao
interface TodoListDao {

    @Query("SELECT * FROM groups")
    fun getGroup():List<GroupElement>

    @Query("select * from tasks where groupId = :groupId")
    fun getTaskByGroup(groupId:Int):List<TaskElement>

    @Query("SELECT COUNT(id) FROM groups")
    fun getGroupCount():Int

    @Query("SELECT COUNT(id) FROM tasks WHERE groupId = :groupId")
    fun getTaskCountByGroup(groupId:Int):Int

    @Query("SELECT COUNT(id) FROM tasks")
    fun getTaskCount():Int

    @Insert(onConflict = ROLLBACK)
    fun insertTask(vararg task:TaskElement)

    @Insert(onConflict = ROLLBACK)
    fun addGroup(vararg group:GroupElement)

    @Update
    fun updateTask(vararg task:TaskElement)

    @Update
    fun updateGroup(vararg group:GroupElement)

    @Delete
    fun removeGroup(vararg  group:GroupElement)

    @Delete
    fun removeTask(vararg  task:TaskElement)

    @Query("DELETE FROM groups")
    fun clearGroups()

    @Query("DELETE FROM tasks")
    fun clearTasks()
}

@Database(entities = [GroupElement::class, TaskElement::class], version = DB_VERSION, exportSchema = true)
@TypeConverters(Converts::class)
abstract class TodoListDataBase : RoomDatabase() {
    abstract fun todoListDao():TodoListDao
}


class Converts {

    val SPLIT_CHAR = TagInspect.INVALID_CHARSET.iterator().next()

    @TypeConverter
    fun strToSet(s:String?):Set<String>? {
        val strs = s?.split(SPLIT_CHAR)?.filter { !it.isEmpty() }?.map { it.trim() }

        return if (strs == null || strs.isEmpty()) {
            null
        } else {
            LinkedHashSet<String>(strs)
        }
    }

    @TypeConverter
    fun setToStr(set:Set<String>?):String? {
        return if (set == null || set.isEmpty()) {
            null
        } else {
            set.joinToString(java.lang.String.valueOf(SPLIT_CHAR))
        }
    }
}

fun createTodoListDB(ctx: Context): TodoListDataBase {
    return Room.databaseBuilder(ctx.applicationContext, TodoListDataBase::class.java, DB_NAME)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    LogUtils.dTag(LOG_TAG, "onCreate")
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    LogUtils.dTag(LOG_TAG, "onOpen")
                }
            }).build()
}