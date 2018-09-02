package com.yezi.todolist.database

import com.yezi.todolist.dao.Converts
import com.yezi.todolist.data.TagInspect
import org.junit.Assert
import org.junit.Test

class DataBaseTest {

    private val LOG_TAG = "DataBaseTest"

    @Test
    fun testConvert() {
        val converts = Converts()

        val setString = "abc${converts.SPLIT_CHAR}def${converts.SPLIT_CHAR}1${converts.SPLIT_CHAR}${converts.SPLIT_CHAR}22323${converts.SPLIT_CHAR}"
        val set = converts.strToSet(setString)
        set?.forEach {
            Assert.assertTrue(TagInspect.valid(it))
        }

        val newSetString = converts.setToStr(set)
        val newSet = converts.strToSet(newSetString)

        Assert.assertTrue(newSet == set)
    }
}