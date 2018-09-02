package com.yezi.todolist.dao

import android.content.Context
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock


private fun crerateExecutor():ThreadPoolExecutor {
    val executor = ThreadPoolExecutor(1, Integer.MAX_VALUE, 0, TimeUnit.MILLISECONDS, LinkedBlockingQueue<Runnable>())
    executor.setThreadFactory {
        Thread(it, "db-access-thread")
    }
    return executor
}

private val executor = crerateExecutor()

private val dblock:Lock = ReentrantLock(true)

private var db:TodoListDataBase? = null


class DaoHelper {

    companion object {
        fun execute(ctx:Context, invoker:(TodoListDataBase) -> Unit) {

            executor.submit {
                dblock.lock()
                try {
                    invoker(getDB(ctx))
                    tryCloseDB()
                } finally {
                    dblock.unlock()
                }

            }
        }

        fun getDB(ctx:Context):TodoListDataBase {
            if (db == null) {
                db = createTodoListDB(ctx)
            }
            return db!!
        }

        fun tryCloseDB() {
            if (executor.activeCount == 0 && executor.queue.isEmpty()) {
                db!!.close()
                db = null
            }
        }
    }
}